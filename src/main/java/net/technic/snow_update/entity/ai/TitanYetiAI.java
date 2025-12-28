package net.technic.snow_update.entity.ai;


import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.technic.snow_update.entity.TitanYetiEntity;
import net.technic.snow_update.init.SnowMemoryModulesRegistry;

public class TitanYetiAI {
    private static final List<SensorType<? extends Sensor<? super TitanYetiEntity>>> SENSOR_TYPES = List.of(SensorType.NEAREST_PLAYERS, SensorType.NEAREST_LIVING_ENTITIES);
    private static final List<MemoryModuleType<?>> MODULE_TYPES = List.of(MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, 
    MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, 
    MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, 
    MemoryModuleType.NEAREST_ATTACKABLE, SnowMemoryModulesRegistry.CHARGE_CD.get(), SnowMemoryModulesRegistry.CHARGE_TARGET.get(), SnowMemoryModulesRegistry.SLAM_CD.get());

    public static void updateActivity(TitanYetiEntity pEntity){
        pEntity.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    public static Brain<?> makeBrain(TitanYetiEntity pEntity, Dynamic<?> pDynamic){
        Brain.Provider<TitanYetiEntity> provider = Brain.provider(MODULE_TYPES, SENSOR_TYPES);
        Brain<TitanYetiEntity> brain = provider.makeBrain(pDynamic);
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(pEntity, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }
    
    private static void initCoreActivity(Brain<TitanYetiEntity> pBrain) {
        pBrain.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(1F), new ModLookAtTargetSink(45, 90), new ModMoveToTargetSink()));
    }
        
    private static void initIdleActivity(Brain<TitanYetiEntity> pBrain) {
        pBrain.addActivity(Activity.IDLE, 10, ImmutableList.of(StartAttacking.create(TitanYetiAI::findNearestValidAttackTarget), new RunOne<>(ImmutableList.of(Pair.of(BehaviorBuilder.triggerIf(Predicate.not(TitanYetiEntity::isCharging) ,RandomStroll.stroll(1.0F)), 2), Pair.of(new DoNothing(30, 60), 1)))));
    }
    
    private static void initFightActivity(TitanYetiEntity pYeti, Brain<TitanYetiEntity> pBrain){
        pBrain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of(StopAttackingIfTargetInvalid.<TitanYetiEntity>create((pEntity)-> {
            return !pYeti.canTargetEntity(pEntity);
        }), SetEntityLookTarget.create((pEntity) -> {
            return isTarget(pYeti, pEntity);
        }, (float)pYeti.getAttributeValue(Attributes.FOLLOW_RANGE)), SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F), new ChargeAttack(), new SlamAttack(), BehaviorBuilder.triggerIf(Predicate.not(TitanYetiEntity::isOcupied), MeleeAttack.create(40))), MemoryModuleType.ATTACK_TARGET);
    }
    
    private static boolean isTarget(TitanYetiEntity pYeti, LivingEntity pEntity){
        return pYeti.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter((pTarget)-> {
            return pTarget == pEntity;
        }).isPresent();
    }

    

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(TitanYetiEntity pYeti){
        if (!pYeti.isStunned() && !pYeti.isRecovering() && !pYeti.isCrashing()){
            Optional<Player> player = pYeti.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
            pYeti.getBrain().setMemory(SnowMemoryModulesRegistry.CHARGE_TARGET.get(), player);
            return player;
        }
        return Optional.empty();
    }

}
