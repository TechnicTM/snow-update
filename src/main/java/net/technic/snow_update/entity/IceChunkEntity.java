package net.technic.snow_update.entity;

import java.util.UUID;


import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.technic.snow_update.init.SnowEntityRegistry;
import net.technic.snow_update.init.SnowSoundsRegistry;

public class IceChunkEntity extends Projectile {

    private LivingEntity target;
    private UUID targetId;
    private int ticksChasing;
    private boolean entityHit;
    public AnimationState shakeAnimation = new AnimationState();


    public IceChunkEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        
    }

    public IceChunkEntity(Level pLevel, LivingEntity pShooter, LivingEntity pTarget) {
        super(SnowEntityRegistry.ICE_CHUNK.get(), pLevel);
        this.target = pTarget;
        this.ticksChasing = 0;
        this.entityHit = false;
        setOwner(pShooter);
        
        this.moveTo(pShooter.getX(), pShooter.getY() + 1.75F, pShooter.getZ(), this.getYRot(), this.getXRot());
    }
    
    public IceChunkEntity(Level pLevel) {
        super(SnowEntityRegistry.ICE_CHUNK.get(), pLevel);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            
            if (this.target != null && this.ticksChasing < 120) {
                homeTo(this.target.blockPosition());
            }

            if (this.target == null && this.targetId != null) {
                this.target = ((LivingEntity)((ServerLevel)this.level()).getEntity(this.targetId));
                if (this.target == null) {
                this.targetId = null;
                }
            }

            if (this.ticksChasing >= 180) {
                this.setDeltaMovement(0, -0.5, 0);
                Vec3 downVec = new Vec3(0, -0.5D, 0);
                this.setPos(this.getX(), this.getY()+downVec.y(), this.getZ());
            }
            
            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            if(this.entityHit & this.ticksChasing > 182) 
                this.destroy();

            ++this.ticksChasing;
           
        } else {
            this.setupAnimationStates();
        }
        this.checkInsideBlocks();
        
        
    }
    
    

    private void setupAnimationStates() {
        this.shakeAnimation.animateWhen(this.ticksChasing > 120, tickCount);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        this.entityHit = true;
        Entity entity = pResult.getEntity();
        LivingEntity livingEntity = entity instanceof LivingEntity ? ((LivingEntity)entity) : null;
        if (livingEntity != null) {
            boolean flag = livingEntity.hurt(this.damageSources().mobProjectile(this, ((LivingEntity)this.getOwner())), 7.0F);
            if (flag) {
                this.doEnchantDamageEffects(((LivingEntity)this.getOwner()), livingEntity);
                livingEntity.setTicksFrozen(livingEntity.getTicksRequiredToFreeze());
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        this.destroy();
        
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
    }
    

    private void destroy() {
        for (int i = 0; i <= 10; i++) {
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.SNOWFLAKE, this.getX()+Mth.sin(i*(2*Mth.PI)/10)*3, this.getY(), this.getZ()+Mth.cos(i*(2*Mth.PI)/10)*3, 4, 0.2D, 0.2D, 0.2D, 0.0D);
        }
        this.playSound(SnowSoundsRegistry.ICE_CHUNK_DROP.get(), 1.0F, 1.0F);
        this.discard();
        this.level().gameEvent(GameEvent.ENTITY_DAMAGE, this.position(), GameEvent.Context.of(this));
    }

    protected boolean canHitEntity(Entity p_37341_) {
        return super.canHitEntity(p_37341_) && !p_37341_.noPhysics;
    }

    @Override
    protected void defineSynchedData() {
        
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.target != null) {
            pCompound.putUUID("Target", this.target.getUUID());
        }
        pCompound.putInt("Ticks", this.ticksChasing);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.hasUUID("Target")) {
            this.targetId = pCompound.getUUID("Target");
        }
        this.ticksChasing = pCompound.getInt("Ticks");
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    private void homeTo(BlockPos dest) {
        
        double posX = getX();
        double posY = getY();
        double posZ = getZ();
        double motionX = this.getDeltaMovement().x;
        double motionY = this.getDeltaMovement().y;
        double motionZ = this.getDeltaMovement().z;

        if (dest.getX() != 0 || dest.getY() != 0 || dest.getZ() != 0){
            double targetX = dest.getX()+0.5;
            double targetY = dest.getY()+this.target.getBbHeight()*1.5D;
            double targetZ = dest.getZ()+0.5;
            Vec3 targetVector = new Vec3(targetX-posX,targetY-posY,targetZ-posZ);
            double length = targetVector.length();
            targetVector = targetVector.scale(0.3/length);
            double weight  = 0;
            if (length < 0.08)
                weight = -0.16;
            

            motionX = (0.9)*motionX+(0.16+weight)*targetVector.x;
            motionY = (0.9)*motionY+(0.16+weight)*targetVector.y;
            motionZ = (0.9)*motionZ+(0.16+weight)*targetVector.z;
        }
        
        
        posX += motionX;
        
        posY += motionY;
        
        posZ += motionZ;
        
        this.setPos((posX), (posY), (posZ));

        this.setDeltaMovement(motionX, motionY, motionZ);
    }

    private void homeTo2(BlockPos dest) {
        Vec3 currentPosition = new Vec3(getX(), getY(), getZ());
        Vec3 targetPosition = new Vec3(dest.getX() + 0.5, dest.getY() + this.target.getBbHeight() * 1.5D, dest.getZ() + 0.5);
        Vec3 targetVector = targetPosition.subtract(currentPosition);
        double distanceToTarget = targetVector.length();

        // Smoothly adjust velocity towards the target
        if (distanceToTarget > 0) {
            Vec3 normalizedDirection = targetVector.normalize();
            
            // Interpolate the movement towards the target. Adjust the '0.05' factor to control the smoothing.
            Vec3 newVelocity = this.getDeltaMovement().add(normalizedDirection.scale(0.05 * (1.0 - Math.exp(-distanceToTarget))));
            
            // Ensure the velocity is capped to a maximum value to prevent excessive speed.
            double maxVelocity = 0.25; // Maximum velocity
            if (newVelocity.lengthSqr() > maxVelocity * maxVelocity) {
                newVelocity = newVelocity.normalize().scale(maxVelocity);
            }
            
            this.setDeltaMovement(newVelocity);
        }

        // Apply the new position based on the updated velocity.
        Vec3 newPosition = currentPosition.add(this.getDeltaMovement());
        this.setPos(newPosition.x, newPosition.y, newPosition.z);
    }


}
    

    