package net.technic.snow_update.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.worldgen.tree.FrostwoodFoliagePlacer;

public class SnowUpdateFoliagePlacerRegistry {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS  = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, SnowUpdate.MOD_ID);

    public static final RegistryObject<FoliagePlacerType<FrostwoodFoliagePlacer>> FROSTWOOD_FOLIAGE_PALCER = FOLIAGE_PLACERS.register("frostwood_foliage_placer", ()-> new FoliagePlacerType<>(FrostwoodFoliagePlacer.CODEC));

    public static void register(IEventBus pBus) {
        FOLIAGE_PLACERS.register(pBus);
    }
}
