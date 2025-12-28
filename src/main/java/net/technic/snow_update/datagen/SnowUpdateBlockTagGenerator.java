package net.technic.snow_update.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.init.SnowBlockRegistry;

public class SnowUpdateBlockTagGenerator extends BlockTagsProvider{

    public SnowUpdateBlockTagGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, SnowUpdate.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider pProvider) {
        this.tag(BlockTags.FENCES).add(
            SnowBlockRegistry.FROSTED_WOOD_FENCE.get(),
            SnowBlockRegistry.FROSTWOOD_FENCE_GATE.get()
        );
        this.tag(BlockTags.LOGS_THAT_BURN).add(
            SnowBlockRegistry.FROSTED_LOG.get(),
            SnowBlockRegistry.FROSTED_WOOD.get(),
            SnowBlockRegistry.STRIPPED_FROSTED_LOG.get(),
            SnowBlockRegistry.STRIPPED_FROSTED_WOOD.get()
        );
        this.tag(BlockTags.PLANKS).add(SnowBlockRegistry.FROSTED_PLANKS.get());
        this.tag(BlockTags.STAIRS).add(
            SnowBlockRegistry.BLUE_ICE_BRICKS_STAIRS.get(),
            SnowBlockRegistry.COBBLED_FRIGIDITE_STAIRS.get(),
            SnowBlockRegistry.FRIGIDITE_STAIRS.get(),
            SnowBlockRegistry.FRIGIDITE_BRICKS_STAIRS.get(),
            SnowBlockRegistry.FROSTED_FRIGIDITE_STAIRS.get(),
            SnowBlockRegistry.ICE_BRICKS_STAIRS.get(),
            SnowBlockRegistry.KORISTONE_STAIRS.get(),
            SnowBlockRegistry.KORISTONE_BRICKS_STAIRS.get(),
            SnowBlockRegistry.PACKED_ICE_BRICKS_STAIRS.get(),
            SnowBlockRegistry.PACKED_SNOW_STAIRS.get(),
            SnowBlockRegistry.PACKED_SNOW_BRICKS_STAIRS.get(),
            SnowBlockRegistry.SNOWY_COBBLESTONE_STAIRS.get(),
            SnowBlockRegistry.SNOWY_STONE_BRICKS_STAIRS.get(),
            SnowBlockRegistry.FROSTED_PLANKS_STAIRS.get(),
            SnowBlockRegistry.HOWLITE_STAIRS.get(),
            SnowBlockRegistry.HOWLITE_BRICKS_STAIRS.get(),
            SnowBlockRegistry.HOWLITE_TILES_STAIRS.get(),
            SnowBlockRegistry.POLISHED_HOWLITE_STAIRS.get(),
            SnowBlockRegistry.BLUE_ICE_BRICKS_2_STAIRS.get()
            );
        this.tag(BlockTags.WALLS).add(
            SnowBlockRegistry.BLUE_ICE_BRICKS_WALL.get(),
            SnowBlockRegistry.COBBLED_FRIGIDITE_WALL.get(),
            SnowBlockRegistry.FRIGIDITE_WALL.get(),
            SnowBlockRegistry.FRIGIDITE_BRICKS_WALL.get(),
            SnowBlockRegistry.FROSTED_FRIGIDITE_WALL.get(),
            SnowBlockRegistry.ICE_BRICKS_WALL.get(),
            SnowBlockRegistry.KORISTONE_WALL.get(),
            SnowBlockRegistry.KORISTONE_BRICKS_WALL.get(),
            SnowBlockRegistry.PACKED_ICE_BRICKS_WALL.get(),
            SnowBlockRegistry.PACKED_SNOW_WALL.get(),
            SnowBlockRegistry.PACKED_SNOW_BRICKS_WALL.get(),
            SnowBlockRegistry.SNOWY_COBBLESTONE_WALL.get(),
            SnowBlockRegistry.SNOWY_STONE_BRICKS_WALL.get(),
            SnowBlockRegistry.HOWLITE_WALL.get(),
            SnowBlockRegistry.HOWLITE_BRICKS_WALL.get(),
            SnowBlockRegistry.HOWLITE_TILES_WALL.get(),
            SnowBlockRegistry.POLISHED_HOWLITE_WALL.get(),
            SnowBlockRegistry.BLUE_ICE_BRICKS_2_WALL.get()
            );
        this.tag(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON).add(SnowBlockRegistry.KEY_STONE.get());
    }

}
