package net.technic.snow_update.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.init.SnowItemsRegistry;
import java.util.List;

public class SnowUpdateToolTiers {
    public static final Tier GLACIER = TierSortingRegistry.registerTier(new ForgeTier(2, 250, 12.0F, 2.0F, 22, BlockTags.NEEDS_IRON_TOOL, ()-> {return Ingredient.of(SnowItemsRegistry.GLACIER_GEM.get());}), 
    new ResourceLocation(SnowUpdate.MOD_ID, "glacier"), List.of(Tiers.GOLD, Tiers.STONE, Tiers.WOOD), List.of(Tiers.DIAMOND, Tiers.NETHERITE));
}
