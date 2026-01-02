package net.technic.snow_update.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.technic.snow_update.SnowUpdate;

public class SnowTags {

    public static class Items {
        public static final TagKey<Item> GLACIER_ITEMS = TagKey.create(
                Registries.ITEM,
                new ResourceLocation(SnowUpdate.MOD_ID, "glacier_items")
        );
    }

    public static class Blocks {
        public static final TagKey<Block> GLACIER_BLOCKS = TagKey.create(
                Registries.BLOCK,
                new ResourceLocation(SnowUpdate.MOD_ID, "glacier_blocks")
        );
    }
}
