package net.technic.snow_update.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.worldgen.tree.FrostwoodTrunkPlacer;

public class SnowUpdateTrunkPlacerRegistry {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, SnowUpdate.MOD_ID);

    public static final RegistryObject<TrunkPlacerType<FrostwoodTrunkPlacer>> FROSTWOOD_TRUNK_PLACER = TRUNK_PLACER_TYPES.register("frostwood_trunk_placer", ()-> new TrunkPlacerType<>(FrostwoodTrunkPlacer.COEDC));

    public static void register(IEventBus pBus) {
        TRUNK_PLACER_TYPES.register(pBus);
    }
}
