package net.technic.snow_update.worldgen.biome;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.technic.snow_update.SnowUpdate;

public class SnowUpdateBiomes {
    public static final ResourceKey<Biome> ICE_CAVES = register("ice_caves");

    public static void bosstrap(BootstapContext<Biome> pContext) {
        pContext.register(ICE_CAVES, iceCaves(pContext));
    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder pBuilder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(pBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(pBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(pBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(pBuilder);
        BiomeDefaultFeatures.addDefaultSprings(pBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(pBuilder);
    }

    public static Biome iceCaves(BootstapContext<Biome> pContext) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);
        BiomeGenerationSettings.Builder generationbBuilder = new BiomeGenerationSettings.Builder(pContext.lookup(Registries.PLACED_FEATURE), pContext.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(generationbBuilder);
        BiomeDefaultFeatures.addBlueIce(generationbBuilder);
        BiomeDefaultFeatures.addFrozenSprings(generationbBuilder);
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.0F)
            .downfall(1F)
            .mobSpawnSettings(spawnBuilder.build())
            .temperatureAdjustment(Biome.TemperatureModifier.FROZEN)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3f76e4)
                .waterFogColor(0x050533)
                .fogColor(0xc0d8ff)
                .skyColor(calculateSkyColor(0.8f))
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.0225f))
                .build()) 
            .generationSettings(generationbBuilder.build()).build();
    }

    protected static int calculateSkyColor(float pTemperature) {
        float $$1 = pTemperature / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }

    private static ResourceKey<Biome> register(String string) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(SnowUpdate.MOD_ID, string));
    }
}
