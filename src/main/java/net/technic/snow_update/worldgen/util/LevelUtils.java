package net.technic.snow_update.worldgen.util;

import java.util.Map;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.worldgen.DimensionTypeTags;
import net.technic.snow_update.worldgen.IExtendedBiomeSource;
import net.technic.snow_update.worldgen.IExtendedNoiseGeneratorSettings;
import net.technic.snow_update.worldgen.IExtendedParameterList;
import net.technic.snow_update.worldgen.IExtendedTheEndBiomeSource;
import net.technic.snow_update.worldgen.api.RegionType;
import net.technic.snow_update.worldgen.api.Regions;
import net.technic.snow_update.worldgen.api.SurfaceRuleManager;

public class LevelUtils {
    public static void initializeOnServerStart(MinecraftServer server)
    {
        RegistryAccess registryAccess = server.registryAccess();
        Registry<LevelStem> levelStemRegistry = registryAccess.registryOrThrow(Registries.LEVEL_STEM);
        long seed = server.getWorldData().worldGenOptions().seed();

        for (Map.Entry<ResourceKey<LevelStem>, LevelStem> entry : levelStemRegistry.entrySet())
        {
            LevelStem stem = entry.getValue();
            initializeBiomes(registryAccess, stem.type(), entry.getKey(), stem.generator(), seed);
        }
    }

    public static boolean shouldApplyToChunkGenerator(ChunkGenerator chunkGenerator)
    {
        return chunkGenerator instanceof NoiseBasedChunkGenerator && shouldApplyToBiomeSource(chunkGenerator.getBiomeSource());
    }

    public static boolean shouldApplyToBiomeSource(BiomeSource biomeSource)
    {
        return biomeSource instanceof MultiNoiseBiomeSource;
    }

    public static RegionType getRegionTypeForDimension(Holder<DimensionType> dimensionType)
    {
        if (dimensionType.is(DimensionTypeTags.NETHER_REGIONS)) return RegionType.NETHER;
        else if (dimensionType.is(DimensionTypeTags.OVERWORLD_REGIONS)) return RegionType.OVERWORLD;
        else return null;
    }

    @SuppressWarnings("rawtypes")
    public static void initializeBiomes(RegistryAccess registryAccess, Holder<DimensionType> dimensionType, ResourceKey<LevelStem> levelResourceKey, ChunkGenerator chunkGenerator, long seed)
    {
        if (!(chunkGenerator instanceof NoiseBasedChunkGenerator noiseBasedChunkGenerator))
            return;

        NoiseGeneratorSettings generatorSettings = noiseBasedChunkGenerator.generatorSettings().value();

        if (chunkGenerator.getBiomeSource() instanceof TheEndBiomeSource)
        {   
            ((IExtendedTheEndBiomeSource)chunkGenerator.getBiomeSource()).initializeForTerraBlender(registryAccess, seed);
            ((IExtendedNoiseGeneratorSettings)(Object)generatorSettings).setRuleCategory(SurfaceRuleManager.RuleCategory.END);
            return;
        }
        else if (!shouldApplyToBiomeSource(chunkGenerator.getBiomeSource())) return;

        RegionType regionType = getRegionTypeForDimension(dimensionType);
        MultiNoiseBiomeSource biomeSource = (MultiNoiseBiomeSource)chunkGenerator.getBiomeSource();
        IExtendedBiomeSource biomeSourceEx = (IExtendedBiomeSource)biomeSource;

        // Don't continue if region type is uninitialized
        if (regionType == null)
            return;

        // Set the chunk generator settings' region type
        SurfaceRuleManager.RuleCategory ruleCategory = switch(regionType) {
            case OVERWORLD -> SurfaceRuleManager.RuleCategory.OVERWORLD;
            case NETHER -> SurfaceRuleManager.RuleCategory.NETHER;
            default -> throw new IllegalArgumentException("Attempted to get surface rule category for unsupported region type " + regionType);
        };
        ((IExtendedNoiseGeneratorSettings)(Object)generatorSettings).setRuleCategory(ruleCategory);

        Climate.ParameterList parameters = biomeSource.parameters();
        IExtendedParameterList parametersEx = (IExtendedParameterList)parameters;

        // Initialize the parameter list for TerraBlender
        parametersEx.initializeForTerraBlender(registryAccess, regionType, seed);

        // Append modded biomes to the biome source biome list
        Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);
        ImmutableList.Builder<Holder<Biome>> builder = ImmutableList.builder();
        Regions.get(regionType).forEach(region -> region.addBiomes(biomeRegistry, pair -> builder.add(biomeRegistry.getHolderOrThrow(pair.getSecond()))));
        biomeSourceEx.appendDeferredBiomesList(builder.build());

        SnowUpdate.LOGGER.info(String.format("Initialized TerraBlender biomes for level stem %s", levelResourceKey.location()));
    }
}
