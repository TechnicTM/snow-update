package net.technic.snow_update.init;

import java.util.Optional;

import com.mojang.serialization.Codec;

import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.SnowUpdate;

public class SnowMemoryModulesRegistry {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULES = DeferredRegister.create(ForgeRegistries.Keys.MEMORY_MODULE_TYPES, SnowUpdate.MOD_ID);

    public static final RegistryObject<MemoryModuleType<Unit>> CHARGE_CD = MEMORY_MODULES.register("charge_cd", ()-> new MemoryModuleType<>(Optional.of(Codec.unit(Unit.INSTANCE))));
    public static final RegistryObject<MemoryModuleType<LivingEntity>> CHARGE_TARGET = MEMORY_MODULES.register("charge_target", ()-> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Unit>> SLAM_CD = MEMORY_MODULES.register("slam_cd", ()-> new MemoryModuleType<>(Optional.of(Codec.unit(Unit.INSTANCE))));

    public static void register(IEventBus pBus){
        MEMORY_MODULES.register(pBus);
    }
}
