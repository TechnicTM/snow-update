package net.technic.snow_update.worldgen.biome;

import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.technic.snow_update.worldgen.api.Region;
import net.technic.snow_update.worldgen.api.RegionType;

public class SnowUpdateOverworldRegion extends Region {

    public SnowUpdateOverworldRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, modifiedVanillaBiomes -> {
            modifiedVanillaBiomes.replaceBiome(Biomes.DRIPSTONE_CAVES, SnowUpdateBiomes.ICE_CAVES);
            modifiedVanillaBiomes.replaceBiome(Biomes.LUSH_CAVES, SnowUpdateBiomes.ICE_CAVES);
        });
    }

}
