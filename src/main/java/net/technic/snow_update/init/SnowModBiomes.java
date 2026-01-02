package net.technic.snow_update.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.technic.snow_update.SnowUpdate;

public class SnowModBiomes {
    public static final ResourceKey<Biome> ICE_CAVES = register("ice_caves");

    public static void boostrap(BootstapContext<Biome> pContext){
        pContext.register(ICE_CAVES, null);
    }



    public static ResourceKey<Biome> register(String pName){
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(SnowUpdate.MOD_ID, pName));
    }

    public static Biome iceCaves(BootstapContext<Biome> pContext){
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        return null;

    }

}
