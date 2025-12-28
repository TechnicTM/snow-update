package net.technic.snow_update.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.worldgen.biome.SnowUpdateBiomes;

public class SnowUpdateBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_TREE_FROSTWOOD = registerKey("add_tree_frosted");
    public static final ResourceKey<BiomeModifier> ADD_ICE_STALACTITE = registerKey("add_ice_stalactite");
    public static final ResourceKey<BiomeModifier> ADD_TEST_GEODE = registerKey("add_test_geode");
    public static final ResourceKey<BiomeModifier> ADD_SNOW_LAYER = registerKey("add_snow_layer");
    public static final ResourceKey<BiomeModifier> ADD_ICE_PATCH_FLOOR = registerKey("add_ice_patch_floor");
    public static final ResourceKey<BiomeModifier> ADD_ICE_PATCH_CEILING = registerKey("add_ice_patch_ceiling");
    public static final ResourceKey<BiomeModifier> ADD_SNOW_PATCH_FLOOR = registerKey("add_snow_patch_floor");
    public static final ResourceKey<BiomeModifier> ADD_SNOW_PATCH_CEILING = registerKey("add_snow_patch_ceiling");
    public static final ResourceKey<BiomeModifier> ADD_POWDER_SNOW_PATCH = registerKey("add_powder_snow_patch");
    public static final ResourceKey<BiomeModifier> ADD_KORISTONE_PATCH = registerKey("add_koristone_patch");
    public static final ResourceKey<BiomeModifier> ADD_FRIGIDITE_PATCH = registerKey("add_frigidite_patch");
    public static final ResourceKey<BiomeModifier> ADD_ICE_SPIKES = registerKey("add_ice_spikes");
    public static final ResourceKey<BiomeModifier> ADD_FROZEN_WATER = registerKey("add_frozen_water");


    public static void bootstrap(BootstapContext<BiomeModifier> pContext) {
        var placedFeatures = pContext.lookup(Registries.PLACED_FEATURE);
        var biomes = pContext.lookup(Registries.BIOME);

        pContext.register(ADD_TREE_FROSTWOOD, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(Tags.Biomes.IS_COLD), 
        HolderSet.direct(placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.FROSTWOOD_PLACED_KEY)), 
        GenerationStep.Decoration.VEGETAL_DECORATION));

        pContext.register(ADD_ICE_STALACTITE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biomes.getOrThrow(SnowUpdateBiomes.ICE_CAVES)), 
        HolderSet.direct(placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.ICE_STALACTITE_PLACED)), 
        GenerationStep.Decoration.UNDERGROUND_DECORATION));

        pContext.register(ADD_TEST_GEODE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biomes.getOrThrow(SnowUpdateBiomes.ICE_CAVES)), 
        HolderSet.direct(placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.TEST_GEODE)),
        GenerationStep.Decoration.UNDERGROUND_DECORATION));

        pContext.register(ADD_SNOW_LAYER, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biomes.getOrThrow(SnowUpdateBiomes.ICE_CAVES)), 
        HolderSet.direct(placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.SNOW_LAYER_PLACED)), Decoration.UNDERGROUND_DECORATION));

        pContext.register(ADD_ICE_PATCH_FLOOR, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biomes.getOrThrow(SnowUpdateBiomes.ICE_CAVES)), 
        HolderSet.direct(placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.ICE_PATCH_FLOOR_PLACED)), Decoration.UNDERGROUND_DECORATION));

        pContext.register(ADD_ICE_PATCH_CEILING, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biomes.getOrThrow(SnowUpdateBiomes.ICE_CAVES)), 
        HolderSet.direct(placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.ICE_PATCH_CEILING_PLACED)), Decoration.UNDERGROUND_DECORATION));

        pContext.register(ADD_SNOW_PATCH_FLOOR, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biomes.getOrThrow(SnowUpdateBiomes.ICE_CAVES)), 
        HolderSet.direct(placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.SNOW_PATCH_FLOOR_PLACED)), Decoration.UNDERGROUND_DECORATION));

        pContext.register(ADD_SNOW_PATCH_CEILING, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biomes.getOrThrow(SnowUpdateBiomes.ICE_CAVES)), 
        HolderSet.direct(placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.SNOW_PATCH_CEILING_PLACED)), Decoration.UNDERGROUND_DECORATION));

        pContext.register(ADD_POWDER_SNOW_PATCH, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biomes.getOrThrow(SnowUpdateBiomes.ICE_CAVES)), 
        HolderSet.direct(placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.POWDER_SNOW_PATCH_FLOOR_PLACED), 
        placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.POWDER_SNOW_PATCH_CEILING_PLACED)), Decoration.UNDERGROUND_DECORATION));

        pContext.register(ADD_KORISTONE_PATCH, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biomes.getOrThrow(SnowUpdateBiomes.ICE_CAVES)), 
        HolderSet.direct(placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.KORISTONE_PATCH_FLOOR_PLACED), 
        placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.KORISTONE_PATCH_CEILING_PLACED)), Decoration.UNDERGROUND_DECORATION));

        pContext.register(ADD_FRIGIDITE_PATCH, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biomes.getOrThrow(SnowUpdateBiomes.ICE_CAVES)), 
        HolderSet.direct(placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.FRIGIDITE_PATCH_FLOOR_PLACED), 
        placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.FRIGIDITE_PATCH_CEILING_PLACED)), Decoration.UNDERGROUND_DECORATION));

        pContext.register(ADD_ICE_SPIKES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biomes.getOrThrow(SnowUpdateBiomes.ICE_CAVES)), 
        HolderSet.direct(placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.LARGE_ICE_SPIKE_PLACED), placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.LARGE_ICE_CRYSTAL_SPIKE_PLACED)), Decoration.UNDERGROUND_DECORATION));

        pContext.register(ADD_FROZEN_WATER, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biomes.getOrThrow(SnowUpdateBiomes.ICE_CAVES)), 
        HolderSet.direct(placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.FROZEN_POOL_PATCH_PLACED), placedFeatures.getOrThrow(SnowUpdatePlacedFeatures.FROZEN_SPRING_PLACED)), Decoration.UNDERGROUND_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey(String string) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(SnowUpdate.MOD_ID, string));
    }
}
