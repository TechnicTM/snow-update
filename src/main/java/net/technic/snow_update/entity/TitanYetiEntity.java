package net.technic.snow_update.entity;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Dynamic;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import net.technic.snow_update.entity.ai.CustomLookControl;
import net.technic.snow_update.entity.ai.TitanYetiAI;
import net.technic.snow_update.init.SnowEntityRegistry;
import net.technic.snow_update.init.SnowSoundsRegistry;


public class TitanYetiEntity extends Monster{
    
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState chargeStartAnimationState = new AnimationState();
    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState stunAnimationState = new AnimationState();
    public final AnimationState stunRecoveryAnimationState = new AnimationState();
    public final AnimationState chargeCrashAnimationState = new AnimationState();
    public final AnimationState meleeAttackAnimationState = new AnimationState();
    public final AnimationState frozenAnimationState = new AnimationState();
    private final TitanYetiPart weakspot;
    private final TitanYetiPart[] parts;
    private final CustomLookControl lookControl;
    public int idleAnimationTimeout = 0;
    public int attackAnimationTimeout = 0;
    public int chargeStartAnimationTimeout = 0;
    public int crashAniamtionTimeout = 0;
    public int chargeCD = 0;
    private int ticksFrozen = 0;
    private final ServerBossEvent bossEvent = new ServerBossEvent(Component.literal("Titan Yeti"), BossBarColor.BLUE, BossBarOverlay.NOTCHED_12);
    


    EntityDimensions STUNNED_DIMENSIONS = EntityDimensions.scalable(SnowEntityRegistry.TITAN_YETI.get().getWidth(), SnowEntityRegistry.TITAN_YETI.get().getHeight() - 1.0F);

    private static final EntityDataAccessor<Boolean> SLAMING = SynchedEntityData.defineId(TitanYetiEntity.class, EntityDataSerializers.BOOLEAN);    
    private static final EntityDataAccessor<Boolean> START_CHARGING = SynchedEntityData.defineId(TitanYetiEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(TitanYetiEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CRASHED = SynchedEntityData.defineId(TitanYetiEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> STUNNED = SynchedEntityData.defineId(TitanYetiEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> START_RECOVERY = SynchedEntityData.defineId(TitanYetiEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROZEN = SynchedEntityData.defineId(TitanYetiEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SHOULD_UNFREEZE = SynchedEntityData.defineId(TitanYetiEntity.class, EntityDataSerializers.BOOLEAN);

    public TitanYetiEntity(EntityType<? extends TitanYetiEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.lookControl = new CustomLookControl(this);
        this.weakspot = new TitanYetiPart(this, 0.5F, 0.5F);
        this.parts = new TitanYetiPart[]{this.weakspot};
        this.getNavigation().setCanFloat(true);
        this.setId(ENTITY_COUNTER.getAndAdd(this.parts.length + 1) + 1);
        this.noPhysics = true;
        setNoGravity(true);
        setPersistenceRequired();
    }
    
    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(SLAMING, false);
        this.entityData.define(START_CHARGING, false);
        this.entityData.define(CHARGING, false);
        this.entityData.define(CRASHED, false);
        this.entityData.define(STUNNED, false);
        this.entityData.define(START_RECOVERY, false);
        this.entityData.define(FROZEN, true);
        this.entityData.define(SHOULD_UNFREEZE, false);
    }

    @Override
    public LookControl getLookControl() {
        return this.lookControl;
    }

    public void setId(int pId) {
        super.setId(pId);
        for (int i = 0; i < this.parts.length; i++) // Forge: Fix MC-158205: Set part ids to successors of parent mob id
           this.parts[i].setId(pId + i + 1);
    }

    public void setAttacking(boolean pAttacking){
        this.entityData.set(SLAMING, pAttacking);
    }

    public boolean isAttacking(){
        return this.entityData.get(SLAMING);
    }

    @Override
    protected float nextStep() {
        return this.moveDist + 0.55F;
    }
    
    private void setupAnimationStates(){
        
        if (this.idleAnimationTimeout <=0 ){
            this.idleAnimationTimeout = this.random.nextInt(40)+80;
            this.idleAnimationState.start(tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if(isAttacking() && attackAnimationTimeout <= 0){
            attackAnimationTimeout = 70;
            attackAnimationState.start(tickCount);
        } else {
            --attackAnimationTimeout;
        }

        if(!isAttacking()){
            attackAnimationState.stop();
        }

        if(isCrashing()){
            ++this.crashAniamtionTimeout;
        }

        if(this.crashAniamtionTimeout >=40){
            setCrashing(false);
            setStuned(true);
            this.crashAniamtionTimeout = 0;
            
        }

        this.chargeStartAnimationState.animateWhen(isStartCharging(), tickCount);
        this.chargeAnimationState.animateWhen(isCharging(), tickCount);
        this.chargeCrashAnimationState.animateWhen(isCrashing(), tickCount);
        this.stunAnimationState.animateWhen(isStunned(), tickCount);
        this.stunRecoveryAnimationState.animateWhen(isRecovering(), tickCount);
        this.frozenAnimationState.animateWhen(isFrozen(), tickCount);
    }

    public boolean isRecovering() {
        return this.entityData.get(START_RECOVERY);
    }

    public boolean isStunned() {
        return this.entityData.get(STUNNED);
    }

    public boolean isCharging() {
        return this.entityData.get(CHARGING);
    }

    public boolean isStartCharging() {
        return this.entityData.get(START_CHARGING);
    }

    public boolean isCrashing() {
        return this.entityData.get(CRASHED);
    }

    public boolean isUnfreezing() {
        return this.entityData.get(SHOULD_UNFREEZE);
    }

    public void tick(){
        super.tick();
        
        float f = this.yBodyRot * ((float) Math.PI / 180F);
        float offset = ((float)Math.PI/2);

        float f1 = Mth.sin(f-offset);
        float f2 = Mth.cos(f-offset);

        if (isUnfreezing()) {
            this.ticksFrozen++;
        }

        if (this.ticksFrozen > 240 && isUnfreezing()) {
            this.setFrozen(false);
            this.setShouldUnfreeze(false);
            this.noPhysics = false;
            setNoGravity(false);
            
        }
         
        this.tickPart(weakspot, f2, 3.2D, f1);

        if (this.chargeCD > 0){
            --this.chargeCD;
        }
        
        if (isCrashing() || isStunned() || isRecovering()){
            this.setPose(Pose.SITTING);
        } else {
            this.setPose(Pose.STANDING);
        }
        
        if(this.level().isClientSide()){
            setupAnimationStates();
        }
    }

    public void setCharging(boolean b) {
        this.entityData.set(CHARGING, b);
    }

    public void setChargeStart(boolean b){
        this.entityData.set(START_CHARGING, b);
    }

    public void setCrashing(boolean b){
        this.entityData.set(CRASHED, b);
    }

    public boolean isOcupied(){
        return this.isStartCharging() || this.isCharging() || this.isCrashing() || this.isStunned() || this.isRecovering() || this.isAttacking();
    }

    public void setFrozen(boolean b) {
        this.entityData.set(FROZEN, b);
    }

    public boolean isFrozen() {
        return this.entityData.get(FROZEN);
    }
    
    public void setShouldUnfreeze(boolean b) {
        this.entityData.set(SHOULD_UNFREEZE, b);
    }

    @Override
    protected void customServerAiStep() {
        ServerLevel serverLevel = ((ServerLevel)this.level());
        serverLevel.getProfiler().push("titanYetiBrain");
        this.getBrain().tick(serverLevel, this);
        this.level().getProfiler().pop();
        super.customServerAiStep();
        TitanYetiAI.updateActivity(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
            MobSpawnType pReason, SpawnGroupData pSpawnData, CompoundTag pDataTag) {
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }
    

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return pPose == Pose.SITTING ? STUNNED_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pPose);
    }
    
    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (!this.isOcupied())
            this.level().broadcastEntityEvent(this, (byte)4);
        return super.doHurtTarget(pEntity);
    }

    @Override
    public void handleEntityEvent(byte pId) {
        switch (pId){
            case (byte) 4:
            this.meleeAttackAnimationState.start(this.tickCount);
        }
        super.handleEntityEvent(pId);
    }

    @Override
    protected void registerGoals() {
        
    }
        
    @Override
    protected Brain<?> makeBrain(Dynamic<?> pDynamic) {
        return TitanYetiAI.makeBrain(this, pDynamic);
    }


    @SuppressWarnings("unchecked")
    @Override
    public Brain<TitanYetiEntity> getBrain() {
        return ((Brain<TitanYetiEntity>)super.getBrain());
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }

    @Nullable
    public LivingEntity getTarget() {
        return this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse((LivingEntity)null);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
        .add(Attributes.MOVEMENT_SPEED, 0.15D)
        .add(Attributes.MAX_HEALTH, 250).add(Attributes.ATTACK_DAMAGE, 18.0)
        .add(Attributes.KNOCKBACK_RESISTANCE, 1.2)
        .add(Attributes.ATTACK_KNOCKBACK, 2.2)
        .add(Attributes.FOLLOW_RANGE, 50);
    }
    
    @Override
    public void move(MoverType pType, Vec3 pPos) {
        super.move(pType, pPos);
    }

    public void die(DamageSource p_37847_) {
        super.die(p_37847_);
    }

    protected void tickDeath() {
        ++this.deathTime;
        
        if (this.deathTime == 20 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte)60);
            this.remove(RemovalReason.KILLED);
        }
        
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getDirectEntity() instanceof AbstractArrow) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }
    
    protected SoundEvent getAmbientSound() {
        return SnowSoundsRegistry.JUVENILE_YETI_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SnowSoundsRegistry.JUVENILE_YETI_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return SnowSoundsRegistry.JUVENILE_YETI_DEATH.get();
    }

    public float getVoicePitch() {
        return this.isDeadOrDying() ? 1.0F : super.getVoicePitch();
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }
    

    @Override
    public @Nullable PartEntity<?>[] getParts() {
        return parts;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer pServerPlayer) {
        super.startSeenByPlayer(pServerPlayer);
        this.bossEvent.addPlayer(pServerPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer pServerPlayer) {
        super.stopSeenByPlayer(pServerPlayer);
        this.bossEvent.removePlayer(pServerPlayer);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isCharging()) {
            this.walkAnimation.setSpeed(this.walkAnimation.speed() + 0.6F);
        }
        
        this.bossEvent.setProgress(this.getHealth()/this.getMaxHealth());
    }

    public void setStuned(boolean b) {
        this.entityData.set(STUNNED, b);
    }

    private void tickPart(TitanYetiPart pPart, double pOffsetX, double pOffsetY, double pOffsetZ) {
        if (this.getPose() == Pose.SITTING){
            pOffsetY-=1.4D; 
        }
        pPart.setPos(this.getX() + pOffsetX, this.getY() + pOffsetY, this.getZ() + pOffsetZ);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        setNoGravity(pCompound.getBoolean("gravity"));
        this.noPhysics = pCompound.getBoolean("physics");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putBoolean("physics", this.noPhysics);
        pCompound.putBoolean("gravity", isNoGravity());
    }

    public class TitanYetiPart extends PartEntity<TitanYetiEntity>{
        public final TitanYetiEntity parentMob;
        public final EntityDimensions size;
        public TitanYetiPart(TitanYetiEntity parent, float pWidht, float pHeight) {
            super(parent);
            this.parentMob = parent;
            this.size = EntityDimensions.scalable(pWidht, pHeight);
            this.refreshDimensions();
        }

        @Override
        public boolean hurt(DamageSource pSource, float pAmount) {
            return this.parentMob.hurt(pSource, 2*pAmount);
        }

        public EntityDimensions getDimensions(Pose pPose) {
            return this.size;
        }

        public boolean is(Entity pEntity) {
            return this == pEntity || this.parentMob == pEntity;
        }

        public boolean isPickable() {
            return true;
        }

        @Override
        protected void defineSynchedData() {

        }

        


        public boolean shouldBeSaved() {
            return false;
        }

        @Override
        protected void readAdditionalSaveData(CompoundTag pCompound) {
            
        }

        @Override
        protected void addAdditionalSaveData(CompoundTag pCompound) {
            
        }
        
        
    }

    public boolean canTargetEntity(Entity pEntity) {
        boolean toReturn = false;
        if (pEntity instanceof LivingEntity livingentity) {
            if (this.level() == pEntity.level() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(pEntity) && !this.isAlliedTo(pEntity) && livingentity.getType() != EntityType.ARMOR_STAND && livingentity.getType() != EntityType.WARDEN && !livingentity.isInvulnerable() && !livingentity.isDeadOrDying() && this.level().getWorldBorder().isWithinBounds(livingentity.getBoundingBox())) {
               toReturn = true;
            }
        }
        return toReturn;
    }
}

