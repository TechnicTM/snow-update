package net.technic.snow_update.entity.ai;


import com.google.common.collect.ImmutableMap;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.technic.snow_update.entity.TitanYetiEntity;
import net.technic.snow_update.init.SnowMemoryModulesRegistry;

public class SlamAttack extends Behavior<TitanYetiEntity>{

    private static final int DURATION = Mth.ceil(60.0F);
    private boolean hasAttacked = false;
    private int attackTicks;
    private LivingEntity target;

    public SlamAttack(){
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, SnowMemoryModulesRegistry.SLAM_CD.get(), MemoryStatus.VALUE_ABSENT), DURATION);
    }


    @Override
    protected boolean checkExtraStartConditions(ServerLevel pLevel, TitanYetiEntity pOwner) {
        return pOwner.isWithinMeleeAttackRange(pOwner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get()) && !(pOwner.isStartCharging() || pOwner.isCharging() || pOwner.isCrashing() || pOwner.isStunned());
    }

    @Override
    protected void start(ServerLevel pLevel, TitanYetiEntity pEntity, long pGameTime) {
        pEntity.setAttacking(true);
        pEntity.attackAnimationTimeout = 0;
        this.attackTicks = 0;
        this.target = pEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
    }

    @Override
    protected void tick(ServerLevel pLevel, TitanYetiEntity pOwner, long pGameTime) {

        if(this.attackTicks == 25){
            pOwner.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
            pOwner.swing(InteractionHand.MAIN_HAND);
            if (pOwner.isWithinMeleeAttackRange(this.target)){
                pOwner.doHurtTarget(this.target);
            }
            
        }

        if (this.attackTicks == 70){
            this.hasAttacked = true;
        }
        ++this.attackTicks;
    }

    @Override
    protected boolean canStillUse(ServerLevel pLevel, TitanYetiEntity pEntity, long pGameTime) {
        return !this.hasAttacked;
    }

    @Override
    protected void stop(ServerLevel pLevel, TitanYetiEntity pEntity, long pGameTime) {
        pEntity.setAttacking(false);
        setCooldown(pEntity);
        this.hasAttacked = false;
    }

    private static void setCooldown(TitanYetiEntity pEntity){
        pEntity.getBrain().setMemoryWithExpiry(SnowMemoryModulesRegistry.SLAM_CD.get(), Unit.INSTANCE, 80L);
    }
}
