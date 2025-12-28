package net.technic.snow_update.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.init.SnowBlockRegistry;
import net.technic.snow_update.init.SnowItemsRegistry;

public class SnowUpdateItemTagGenerator extends ItemTagsProvider {

    public SnowUpdateItemTagGenerator(PackOutput pOutput, CompletableFuture<Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, SnowUpdate.MOD_ID, existingFileHelper);
        
    }

    @Override
    protected void addTags(Provider pProvider) {
        this.tag(ItemTags.TRIMMABLE_ARMOR)
            .add(SnowItemsRegistry.YETI_FUR_HELMET.get())
            .add(SnowItemsRegistry.YETI_FUR_CHESTPLATE.get())
            .add(SnowItemsRegistry.YETI_FUR_LEGGINGS.get())
            .add(SnowItemsRegistry.YETI_FUR_BOOTS.get())
            .add(SnowItemsRegistry.GLACIER_HELMET.get())
            .add(SnowItemsRegistry.GLACIER_CHESTPLATE.get())
            .add(SnowItemsRegistry.GLACIER_LEGGINGS.get())
            .add(SnowItemsRegistry.GLACIER_BOOTS.get());

        this.tag(ItemTags.TRIM_MATERIALS)
            .add(SnowItemsRegistry.GLACIER_GEM.get());

        this.tag(ItemTags.LOGS_THAT_BURN).add(
            SnowBlockRegistry.FROSTED_LOG.get().asItem(),
            SnowBlockRegistry.FROSTED_WOOD.get().asItem(),
            SnowBlockRegistry.STRIPPED_FROSTED_LOG.get().asItem(),
            SnowBlockRegistry.STRIPPED_FROSTED_WOOD.get().asItem() 
        );

        this.tag(ItemTags.PLANKS).add(SnowBlockRegistry.FROSTED_PLANKS.get().asItem());
        
    }

}
