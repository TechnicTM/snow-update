package net.technic.snow_update.datagen.loot;

import java.util.Set;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.init.SnowBlockRegistry;

public class SnowUpdateBlockLootTable extends BlockLootSubProvider{

    public SnowUpdateBlockLootTable() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
       this.dropSelf(SnowBlockRegistry.FROSTED_LOG.get());
       this.dropSelf(SnowBlockRegistry.FROSTED_WOOD.get());
       this.dropSelf(SnowBlockRegistry.STRIPPED_FROSTED_LOG.get());
       this.dropSelf(SnowBlockRegistry.STRIPPED_FROSTED_WOOD.get());
       this.dropSelf(SnowBlockRegistry.FROSTED_PLANKS.get());
       this.dropSelf(SnowBlockRegistry.SNOWY_COBBLESTONE.get());
       this.dropSelf(SnowBlockRegistry.FROSTED_WOOD_FENCE.get());
       this.dropSelf(SnowBlockRegistry.FROSTED_PLANKS_STAIRS.get());

       this.add(SnowBlockRegistry.FROSTED_PLANKS_SLAB.get(), block -> createSlabItemTable(SnowBlockRegistry.FROSTED_PLANKS_SLAB.get()));

       this.add(SnowBlockRegistry.FROSTED_LEAVES.get(), block -> createLeavesDrops(block, block, NORMAL_LEAVES_SAPLING_CHANCES));


    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SnowBlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
