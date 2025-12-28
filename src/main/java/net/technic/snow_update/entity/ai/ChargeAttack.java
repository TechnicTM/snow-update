package net.technic.snow_update.entity.ai;

import com.google.common.collect.ImmutableMap;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.technic.snow_update.entity.TitanYetiEntity;
import net.technic.snow_update.init.SnowMemoryModulesRegistry;

public class ChargeAttack extends Behavior<TitanYetiEntity>{
    private static final int DURATION = Mth.ceil(60.0F);
    private static final double MIN_RANGE_SQ = 16.0D;
    private static final int FREQ = 10;
    private LivingEntity target;
    private Vec3 chargePos;
    private int windup;
    private boolean hasAttacked;
    private boolean isStuned;
    private boolean isFirstTime = true;

    public ChargeAttack() {
        super(ImmutableMap.of(SnowMemoryModulesRegistry.CHARGE_TARGET.get(), MemoryStatus.VALUE_PRESENT, SnowMemoryModulesRegistry.CHARGE_CD.get(), MemoryStatus.VALUE_ABSENT), DURATION);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel pLevel, TitanYetiEntity pOwner) {
        
        if (pOwner.isOcupied()){
            return false;
        }
    
        this.target = pOwner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
        double distance = this.target.distanceToSqr(pOwner);

        if (distance < MIN_RANGE_SQ) {
            return false;
        }

        if (!pOwner.onGround()){
            return false;
        }

        Vec3 chargePos = findChargePoint(pOwner, this.target);
        this.chargePos = chargePos;
        return true;
    }

    @Override
    protected boolean canStillUse(ServerLevel pLevel, TitanYetiEntity pEntity, long pGameTime) {
        return (this.windup > 0 || chargePos != pEntity.position()) && !this.isStuned;
    }

    @Override
    protected void start(ServerLevel pLevel, TitanYetiEntity pEntity, long pGameTime) {
        pEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, (long)DURATION);
        this.windup = 20;
        this.isFirstTime = true;
        pEntity.setChargeStart(true);
        pEntity.setSprinting(true);
        
    }

    @SuppressWarnings("deprecation")//this.charger.level().hasChunksAt(min, max)
    @Override
    protected void tick(ServerLevel pLevel, TitanYetiEntity pOwner, long pGameTime) {
       
        //pOwner.getLookControl().setLookAt(this.chargePos.x(), this.chargePos.y() - 1, this.chargePos.z(), 10.0F, pOwner.getMaxHeadXRot());
        
        if (this.windup > 0){
            if (this.isFirstTime){
                this.isFirstTime = false;
                
            }   

            if (--this.windup == 0){
                
                pOwner.getNavigation().moveTo(this.chargePos.x(), this.chargePos.y(), this.chargePos.z(), 2.2F);
                pOwner.setChargeStart(false);
				pOwner.setCharging(true);
        }
            
        } else if (!pOwner.level().isClientSide() && ForgeEventFactory.getMobGriefingEvent(pLevel, pOwner)){
            AABB bb = pOwner.getBoundingBox();

            int minx = Mth.floor(bb.minX - 0.75D);
			int miny = Mth.floor(bb.minY + 1.0D);
			int minz = Mth.floor(bb.minZ - 0.75D);
			int maxx = Mth.floor(bb.maxX + 0.75D);
			int maxy = Mth.floor(bb.maxY + 0.0D);
			int maxz = Mth.floor(bb.maxZ + 0.75D);

			BlockPos min = new BlockPos(minx, miny, minz);
			BlockPos max = new BlockPos(maxx, maxy, maxz);

            if (pLevel.hasChunkAt(maxy, maxz)) {
                for (BlockPos pos : BlockPos.betweenClosed(min, max)){
                    if(pLevel.getBlockState(pos).getBlock() != Blocks.AIR){
                        pOwner.setCrashing(true);
                        this.isStuned = true;
                    }
                }
            }
        }

        double rangeSq = pOwner.getBbWidth() * 1.4F * pOwner.getBbWidth() * 1.4F + this.target.getBbWidth();

        if (pOwner.distanceToSqr(this.target.getX(), this.target.getBoundingBox().minY, this.target.getZ()) <= rangeSq) {
            if (!this.hasAttacked){
                pOwner.doHurtTarget(this.target);
                this.hasAttacked = true;
            }
        }
    }
    

    @Override
    protected void stop(ServerLevel pLevel, TitanYetiEntity pEntity, long pGameTime) {
        pEntity.getNavigation().stop();
        this.windup = 0;
        this.target = null;
        this.hasAttacked = false;
		this.isStuned = false;
        pEntity.setChargeStart(false);
        pEntity.setCharging(false);
        pEntity.setSprinting(false);
        setCooldown(pEntity, 240);
    }

    public static void setCooldown(LivingEntity pEntity, int pCooldown) {
      pEntity.getBrain().setMemoryWithExpiry(SnowMemoryModulesRegistry.CHARGE_CD.get(), Unit.INSTANCE, (long)pCooldown);
    }

    protected Vec3 findChargePoint(Entity pAttacker, Entity pTarget) {

		double X = pTarget.getX() - pAttacker.getX();
		double Z = pTarget.getZ() - pAttacker.getZ();
		float angle = (float) (Math.atan2(Z, X));

		double distance = Mth.sqrt((float) (X * X + Z * Z));
		double overshoot = 8.1D;

		double dX = Mth.cos(angle) * (distance + overshoot);
		double dZ = Mth.sin(angle) * (distance + overshoot);

		return new Vec3(pAttacker.getX() + dX, pTarget.getY(), pAttacker.getZ() + dZ);
	}

    

}
