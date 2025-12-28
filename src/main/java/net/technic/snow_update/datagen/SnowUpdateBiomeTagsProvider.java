package net.technic.snow_update.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.worldgen.biome.SnowUpdateBiomes;

public class SnowUpdateBiomeTagsProvider extends BiomeTagsProvider {

    public SnowUpdateBiomeTagsProvider(PackOutput pOutput, CompletableFuture<Provider> pProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, SnowUpdate.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider pProvider) {
        this.tag(BiomeTags.IS_OVERWORLD).add(SnowUpdateBiomes.ICE_CAVES);
        this.tag(Tags.Biomes.IS_UNDERGROUND).add(SnowUpdateBiomes.ICE_CAVES);
    }

}
