package net.technic.snow_update.worldgen;


import java.util.List;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.init.SnowBlockRegistry;
import net.technic.snow_update.init.SnowFeaturesRegistry;
import net.technic.snow_update.worldgen.config.FrozenSpringConfig;
import net.technic.snow_update.worldgen.config.IceCrystalSpikeConfig;
import net.technic.snow_update.worldgen.tree.FrostwoodFoliagePlacer;
import net.technic.snow_update.worldgen.tree.FrostwoodTrunkPlacer;

public class SnowUpdateConfigFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ICE_STALACTITE =  registerKey("ice_stalactite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTWOOD_KEY = registerKey("frostwood_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TEST_GEODE = registerKey("test_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SNOW_LAYER = registerKey("snow_layer");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ICE_PATCH_FLOOR = registerKey("ice_patch_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ICE_PATCH_CEILING = registerKey("ice_patch_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SNOW_PATCH_FLOOR = registerKey("snow_patch_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SNOW_PATCH_CEILING = registerKey("snow_patch_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POWDER_SNOW_PATCH_FLOOR = registerKey("powder_snow_patch_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POWDER_SNOW_PATCH_CEILING = registerKey("powder_snow_patch_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> KORISTONE_PATCH_FLOOR = registerKey("koristone_patch_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> KORISTONE_PATCH_CEILING = registerKey("koristone_patch_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FRIGIDITE_PATCH_FLOOR = registerKey("frigidite_patch_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FRIGIDITE_PATCH_CEILING = registerKey("frigidite_patch_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_ICE_SPIKE_CONFIG = registerKey("large_ice_spike_config");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_CRYSTAL_SPIKE_CONFIG = registerKey("large_crystal_spike_config");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROZEN_POOL = registerKey("frozen_pool");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROZEN_SPRING = registerKey("frozen_spring");
    
    @SuppressWarnings("deprecation")
    public static void boostrap(BootstapContext<ConfiguredFeature<?, ?>> pContext){
        register(pContext, ICE_STALACTITE, SnowFeaturesRegistry.ICE_STALACTITE_FEATURE.get(), 
            new DripstoneClusterConfiguration(12, UniformInt.of(3, 6), 
            UniformInt.of(2, 8), 1, 3, 
            UniformInt.of(2, 4), UniformFloat.of(0.3F, 0.7F), 
            ClampedNormalFloat.of(0.1F, 0.3F, 0.1F, 0.9F), 0.1F, 3, 8));

        register(pContext, FROSTWOOD_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(SnowBlockRegistry.FROSTED_LOG.get()), 
            new FrostwoodTrunkPlacer(2, 3, 4), 
            BlockStateProvider.simple(SnowBlockRegistry.FROSTED_LEAVES.get()), 
            new FrostwoodFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3), 
            new TwoLayersFeatureSize(1, 0, 2)).build());
            
        register(pContext, TEST_GEODE, Feature.GEODE, new GeodeConfiguration(new GeodeBlockSettings(
            BlockStateProvider.simple(Blocks.AIR), 
            BlockStateProvider.simple(SnowBlockRegistry.GLACIER_BLOCK.get()), 
            BlockStateProvider.simple(SnowBlockRegistry.BUDDING_ICE_CRYSTAL.get()), 
            BlockStateProvider.simple(Blocks.CALCITE), 
            BlockStateProvider.simple(Blocks.SMOOTH_BASALT), 
            List.of(
                SnowBlockRegistry.SMALL_ICE_CRYSTAL_BUD.get().defaultBlockState(), 
                SnowBlockRegistry.MEDIUM_ICE_CRYSTAL_BUD.get().defaultBlockState(),
                SnowBlockRegistry.LARGE_ICE_CRYSTAL_BUD.get().defaultBlockState(),
                SnowBlockRegistry.ICE_CRYSTAL_CLUSTER.get().defaultBlockState()
            ), 
            BlockTags.FEATURES_CANNOT_REPLACE, 
            BlockTags.GEODE_INVALID_BLOCKS), new GeodeLayerSettings(1.7D, 1.2D, 2.5D, 3.5D),
            new GeodeCrackSettings(0.25D, 1.5D, 1), 0.5D, 0.1D, true, UniformInt.of(3, 8), UniformInt.of(2, 6), UniformInt.of(1, 2), -18, 18, 0.075D, 1));

        register(pContext, SNOW_LAYER, SnowFeaturesRegistry.SNOW_LAYER_FEATURE.get(), new NoneFeatureConfiguration());

        register(pContext, ICE_PATCH_FLOOR, SnowFeaturesRegistry.ICE_PATCH_FLOOR_FEATURE.get(), new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, 
            BlockStateProvider.simple(SnowBlockRegistry.ICE_SPIKE_BLOCK.get()), PlacementUtils.inlinePlaced(pContext.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ICE_STALACTITE)), CaveSurface.FLOOR, 
            UniformInt.of(1, 2), 0, 5, 0.08f, UniformInt.of(4, 7), 0.3f));

        register(pContext, ICE_PATCH_CEILING, SnowFeaturesRegistry.ICE_PATCH_CEILING_FEATURE.get(), new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, 
            BlockStateProvider.simple(SnowBlockRegistry.ICE_SPIKE_BLOCK.get()), PlacementUtils.inlinePlaced(pContext.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ICE_STALACTITE)), CaveSurface.FLOOR, 
            UniformInt.of(1, 2), 0, 5, 0.08f, UniformInt.of(4, 7), 0.3f));

        register(pContext, SNOW_PATCH_FLOOR, SnowFeaturesRegistry.SNOW_PATCH_FLOOR_FEATURE.get(), new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, 
            BlockStateProvider.simple(Blocks.SNOW_BLOCK), PlacementUtils.inlinePlaced(pContext.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ICE_STALACTITE)), CaveSurface.FLOOR, 
            UniformInt.of(1, 1), 0, 5, 0.08f, UniformInt.of(4, 7), 0.3f));

        register(pContext, SNOW_PATCH_CEILING, SnowFeaturesRegistry.SNOW_PATCH_CEILING_FEATURE.get(), new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, 
            BlockStateProvider.simple(Blocks.SNOW_BLOCK), PlacementUtils.inlinePlaced(pContext.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ICE_STALACTITE)), CaveSurface.FLOOR, 
            UniformInt.of(1, 1), 0, 5, 0.08f, UniformInt.of(4, 7), 0.3f));

        register(pContext, POWDER_SNOW_PATCH_FLOOR, SnowFeaturesRegistry.SNOW_PATCH_FLOOR_FEATURE.get(), new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, 
            BlockStateProvider.simple(Blocks.POWDER_SNOW), PlacementUtils.inlinePlaced(pContext.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ICE_STALACTITE)), CaveSurface.FLOOR, 
            UniformInt.of(1, 2), 0, 5, 0.08f, UniformInt.of(4, 7), 0.3f));

        register(pContext, POWDER_SNOW_PATCH_CEILING, SnowFeaturesRegistry.SNOW_PATCH_CEILING_FEATURE.get(), new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, 
            BlockStateProvider.simple(Blocks.POWDER_SNOW), PlacementUtils.inlinePlaced(pContext.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ICE_STALACTITE)), CaveSurface.FLOOR, 
            UniformInt.of(1, 2), 0, 5, 0.08f, UniformInt.of(4, 7), 0.3f));
        
        register(pContext, KORISTONE_PATCH_FLOOR, SnowFeaturesRegistry.KORISTONE_PATCH_FLOOR_FEATURE.get(), new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, 
            BlockStateProvider.simple(SnowBlockRegistry.KORISTONE.get()), PlacementUtils.inlinePlaced(pContext.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ICE_STALACTITE)), CaveSurface.FLOOR, 
            UniformInt.of(1, 2), 0, 5, 0.08f, UniformInt.of(4, 7), 0.3f));

        register(pContext, KORISTONE_PATCH_CEILING, SnowFeaturesRegistry.KORISTONE_PATCH_CEILING_FEATURE.get(), new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, 
            BlockStateProvider.simple(SnowBlockRegistry.KORISTONE.get()), PlacementUtils.inlinePlaced(pContext.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ICE_STALACTITE)), CaveSurface.FLOOR, 
            UniformInt.of(1, 2), 0, 5, 0.08f, UniformInt.of(4, 7), 0.3f));

        register(pContext, FRIGIDITE_PATCH_FLOOR, SnowFeaturesRegistry.FRIGIDITE_PATCH_FLOOR_FEATURE.get(), new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, 
            BlockStateProvider.simple(SnowBlockRegistry.FRIGIDITE.get()), PlacementUtils.inlinePlaced(pContext.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ICE_STALACTITE)), CaveSurface.FLOOR, 
            UniformInt.of(1, 2), 0, 5, 0.08f, UniformInt.of(4, 7), 0.3f));

        register(pContext, FRIGIDITE_PATCH_CEILING, SnowFeaturesRegistry.FRIGIDITE_PATCH_CEILING_FEATURE.get(), new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, 
            BlockStateProvider.simple(SnowBlockRegistry.FRIGIDITE.get()), PlacementUtils.inlinePlaced(pContext.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ICE_STALACTITE)), CaveSurface.FLOOR, 
            UniformInt.of(1, 2), 0, 5, 0.08f, UniformInt.of(4, 7), 0.3f));

        register(pContext, LARGE_ICE_SPIKE_CONFIG, SnowFeaturesRegistry.LARGE_ICE_SPIKE.get(), new LargeDripstoneConfiguration(30, 
            UniformInt.of(3, 19), UniformFloat.of(0.4F, 2.0F), 0.33F, UniformFloat.of(0.3F, 0.9F), UniformFloat.of(0.4F, 1.0F), UniformFloat.of(0, 0.3F), 4, 0.6F));

        register(pContext, LARGE_CRYSTAL_SPIKE_CONFIG, SnowFeaturesRegistry.LARGE_ICE_CRYSTAL_SPIKE.get(), new IceCrystalSpikeConfig(SnowBlockRegistry.ICE_SPIKE_BLOCK.get().defaultBlockState(), 
            Blocks.CAVE_AIR.defaultBlockState(), UniformInt.of(1, 3), CaveSurface.CEILING));

        register(pContext, FROZEN_POOL, SnowFeaturesRegistry.FROZEN_POOL_PATCH_FEATURE.get(), new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE,
            BlockStateProvider.simple(SnowBlockRegistry.ICE_SPIKE_BLOCK.get()), PlacementUtils.inlinePlaced(pContext.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ICE_STALACTITE)), CaveSurface.FLOOR, 
            ConstantInt.of(3), 0.8F, 5, 0.1F, UniformInt.of(4, 7), 0.7F));

        register(pContext, FROZEN_SPRING, SnowFeaturesRegistry.FROZEN_SPRING_FEATURE.get(), new FrozenSpringConfig(Blocks.ICE.defaultBlockState(), true, 4, 1,
            HolderSet.direct(Block::builtInRegistryHolder, Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DEEPSLATE, Blocks.TUFF, Blocks.CALCITE, Blocks.DIRT, Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW, Blocks.PACKED_ICE, Blocks.CALCITE)));
        
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> pContext,
            ResourceKey<ConfiguredFeature<?, ?>> pKey,F pFeature,
            FC pFeatureConfig){
        pContext.register(pKey, new ConfiguredFeature<>(pFeature, pFeatureConfig));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String pName) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SnowUpdate.MOD_ID, pName));
    }

    



}
