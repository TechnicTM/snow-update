package net.technic.snow_update.entity;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.technic.snow_update.entity.ai.SnowBallThrowGoal;
import net.technic.snow_update.init.SnowSoundsRegistry;

public class JuvenileYetiEntity extends AbstractIllager implements RangedAttackMob {

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;
    public int idleAnimationTimeout = 0;
    public final AnimationState idleAnimationState = new AnimationState();
    private static final EntityDataAccessor<Integer> attackTiming = SynchedEntityData.defineId(JuvenileYetiEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData
    .defineId(JuvenileYetiEntity.class, EntityDataSerializers.BOOLEAN);

    public JuvenileYetiEntity(EntityType<? extends AbstractIllager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SnowBallThrowGoal(this, 1D, 20, 10.0F));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 15.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[]{Raider.class})).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 2.0F, 1.2F, 1.4F));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(1, new AvoidEntityGoal<>(this, AbstractVillager.class, 2.0F, 1.2F, 1.4F));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(1, new AvoidEntityGoal<>(this, IronGolem.class, 2.0F, 1.2F, 1.4F));
        
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
        .add(Attributes.MOVEMENT_SPEED, 0.15000000596046448)
        .add(Attributes.MAX_HEALTH, 30).add(Attributes.ATTACK_DAMAGE, 8.0)
        .add(Attributes.FOLLOW_RANGE, 20.0).add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
        .add(Attributes.ATTACK_KNOCKBACK, 2.0);
    }
    
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
        this.entityData.define(attackTiming, 0);
    }

    public void setAttacking(boolean attacking){
        this.entityData.set(ATTACKING, attacking);
    }

    public void setAnimationTimeout(int time){
        this.entityData.set(attackTiming, time);
    }

    public boolean isAttacking(){
        return this.entityData.get(ATTACKING);
    }

    public int getAnimationTimeout(){
        return this.entityData.get(attackTiming);
    }

    public void applyRaidBuffs(int p_213660_1_, boolean p_213660_2_) {
    }

    private void setupAnim(){

        if (this.isAttacking() && getAnimationTimeout() <= 0){
            setAnimationTimeout(50);
            attackAnimationState.start(this.tickCount);
        } else {
            setAnimationTimeout(getAnimationTimeout()-1);
        }

        if (!this.isAttacking()){
            attackAnimationState.stop();
        }

        if (this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            this.idleAnimationTimeout--;
        }
    }

    protected void updateWalkAnimation(float f){
        float f1; 
        if (this.getPose() == Pose.STANDING){
            f1 = Math.min(f * 6, 1.0F);
        } else {
            f1 = 0;
        }

        this.walkAnimation.update(f1, 0.2F);

    }

    @Override
    public void tick(){
        super.tick();
        if (this.level().isClientSide())
        setupAnim();
    }


    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance p_213386_2_, MobSpawnType p_213386_3_, @Nullable SpawnGroupData p_213386_4_, @Nullable CompoundTag p_213386_5_) {
        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
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


    public SoundEvent getCelebrateSound() {
        return null;
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

    
    public boolean canBeLeader() {
        return false;
    }

    public void performRangedAttack(LivingEntity pTarget, float pDistanceFactor) {
        
        SnowBallEntity snowball = new SnowBallEntity(this.level(), this);
        double d0 = pTarget.getEyeY() - (double)1.1F;
        double d1 = pTarget.getX() - (this.getX());
        double d2 = d0 - snowball.getY();
        double d3 = pTarget.getZ() - (this.getZ());
        
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double)0.2F;
        snowball.shoot(d1, d2 + d4, d3, 1.6F, 5.0F);
        this.level().addFreshEntity(snowball);
        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        
   }
}
