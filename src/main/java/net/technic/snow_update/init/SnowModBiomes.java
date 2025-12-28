package net.technic.snow_update.init;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.worldgen.SnowUpdatePlacedFeatures;

public class SnowModBiomes {
    public static final ResourceKey<Biome> ICE_CAVES = ResourceKey.create(
            Registries.BIOME, new ResourceLocation("snow_update", "ice_caves"));
    public static final ResourceKey<Biome> FROSTWOOD_FOREST = ResourceKey.create(
            Registries.BIOME, new ResourceLocation("snow_update", "frostwood_forest"));

    public static void bootstrap(BootstapContext<Biome> context) {
        context.register(ICE_CAVES, createIceCaves(context));
        context.register(FROSTWOOD_FOREST, createFrostwoodForest(context));

    }


    private static Biome createIceCaves(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, context.lookup(Registries.CONFIGURED_CARVER));

        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);

        BiomeDefaultFeatures.addDefaultCarversAndLakes(gen);
        BiomeDefaultFeatures.addDefaultOres(gen);
        BiomeDefaultFeatures.addDefaultFlowers(gen);
        BiomeDefaultFeatures.addDefaultGrass(gen);

        return new Biome.BiomeBuilder()
                .temperature(0.75f)
                .downfall(0.5f)
                .hasPrecipitation(true)
                .mobSpawnSettings(spawns.build())
                .generationSettings(gen.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(12638463)
                        .skyColor(7907327)
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .grassColorOverride(3173410)
                        .foliageColorOverride(2379546)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    private static Biome createFrostwoodForest(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placed, context.lookup(Registries.CONFIGURED_CARVER));

        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placed.getOrThrow(SnowUpdatePlacedFeatures.FROSTWOOD_SPAWN_KEY));

        return new Biome.BiomeBuilder()
                .temperature(0.0f)
                .downfall(0.5f)
                .hasPrecipitation(true)
                .mobSpawnSettings(spawns.build())
                .generationSettings(gen.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(15132390)
                        .skyColor(12510435)
                        .waterColor(5812969)
                        .waterFogColor(11457514)
                        .grassColorOverride(16448250)
                        .foliageColorOverride(16448250)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

}
