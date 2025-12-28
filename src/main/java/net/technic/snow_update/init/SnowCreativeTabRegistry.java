package net.technic.snow_update.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.SnowUpdate;

public class SnowCreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SnowUpdate.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SNOW_UPDATE_TAB = CREATIVE_TAB.register("snow_update_tab", ()-> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0).icon(()-> new ItemStack(SnowItemsRegistry.YETI_HORN.get()))
        .title(Component.translatable("creativetab.snow_update_tab"))
        .displayItems((pParameters, pOutput) -> {
            pOutput.accept(SnowItemsRegistry.YETI_HORN.get());
            pOutput.accept(SnowBlockRegistry.SNOWY_COBBLESTONE.get());
            pOutput.accept(SnowBlockRegistry.COBBLED_FRIGIDITE.get());
            pOutput.accept(SnowBlockRegistry.FROSTED_WOOD_FENCE.get());
            pOutput.accept(SnowBlockRegistry.FROSTED_PLANKS_SLAB.get());
            pOutput.accept(SnowBlockRegistry.FROSTED_PLANKS_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.CHISELED_FRIGIDITE.get());
            pOutput.accept(SnowBlockRegistry.CHISELED_KORISTONE.get());
            pOutput.accept(SnowBlockRegistry.FRIGIDITE.get());
            pOutput.accept(SnowBlockRegistry.FRIGIDITE_BRICKS.get());
            pOutput.accept(SnowBlockRegistry.FROSTED_FRIGIDITE.get());
            pOutput.accept(SnowBlockRegistry.GLACIER_BLOCK.get());
            pOutput.accept(SnowBlockRegistry.GLACIER_CRYSTAL.get());
            pOutput.accept(SnowBlockRegistry.FROSTWOOD_FENCE_GATE.get());
            pOutput.accept(SnowBlockRegistry.PACKED_ICE_BRICKS.get());
            pOutput.accept(SnowBlockRegistry.PACKED_SNOW.get());
            pOutput.accept(SnowBlockRegistry.PACKED_SNOW_BRICKS.get());
            pOutput.accept(SnowBlockRegistry.POLISHED_FRIGITIDE.get());
            pOutput.accept(SnowBlockRegistry.POLISHED_BLUE_ICE.get());
            pOutput.accept(SnowBlockRegistry.POLISHED_KORISTONE.get());
            pOutput.accept(SnowBlockRegistry.POLISHED_PACKED_ICE.get());
            pOutput.accept(SnowBlockRegistry.POLISHED_GLACIER_BLOCK.get());
            pOutput.accept(SnowBlockRegistry.SHREDDED_KORISTONE.get());
            pOutput.accept(SnowBlockRegistry.SNOW_BRICKS.get());
            pOutput.accept(SnowBlockRegistry.CHISELED_HOWLITE.get());
            pOutput.accept(SnowBlockRegistry.CHISELED_HOWLITE_BRICKS.get());
            pOutput.accept(SnowBlockRegistry.CHISELED_HOWLITE_TILES.get());
            pOutput.accept(SnowBlockRegistry.ICE_BRICKS.get());
            pOutput.accept(SnowBlockRegistry.CHISELED_POLISHED_HOWLITE.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE_BRICKS.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE_TILES.get());
            pOutput.accept(SnowBlockRegistry.POLISHED_HOWLITE.get());
            pOutput.accept(SnowBlockRegistry.SNOWY_STONE_BRICKS.get());
            pOutput.accept(SnowBlockRegistry.KORISTONE.get());
            pOutput.accept(SnowBlockRegistry.KORISTONE_BRICKS.get());
            pOutput.accept(SnowBlockRegistry.BLUE_ICE_BRICKS.get());
            pOutput.accept(SnowBlockRegistry.BLUE_ICE_BRICKS_2.get());
            pOutput.accept(SnowBlockRegistry.ICE_SPIKE_BLOCK.get());
            pOutput.accept(SnowBlockRegistry.SNOWY_GRASS.get());
            pOutput.accept(SnowBlockRegistry.SNOWY_TALL_GRASS.get());
            pOutput.accept(SnowBlockRegistry.FROSTWOOD_DOOR.get());
            pOutput.accept(SnowBlockRegistry.FROSTWOOD_TRAPDOOR.get());
            pOutput.accept(SnowBlockRegistry.FROSTED_LEAVES_SPIKES.get());
            pOutput.accept(SnowBlockRegistry.FROSTWOOD_SAPLING.get());
            pOutput.accept(SnowBlockRegistry.BLUE_ICE_BRICKS_SLAB.get());
            pOutput.accept(SnowBlockRegistry.BLUE_ICE_BRICKS_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.BLUE_ICE_BRICKS_WALL.get());
            pOutput.accept(SnowBlockRegistry.COBBLED_FRIGIDITE_SLAB.get());
            pOutput.accept(SnowBlockRegistry.COBBLED_FRIGIDITE_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.COBBLED_FRIGIDITE_WALL.get());
            pOutput.accept(SnowBlockRegistry.FRIGIDITE_SLAB.get());
            pOutput.accept(SnowBlockRegistry.FRIGIDITE_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.FRIGIDITE_WALL.get());
            pOutput.accept(SnowBlockRegistry.FRIGIDITE_BRICKS_SLAB.get());
            pOutput.accept(SnowBlockRegistry.FRIGIDITE_BRICKS_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.FRIGIDITE_BRICKS_WALL.get());
            pOutput.accept(SnowBlockRegistry.FROSTED_FRIGIDITE_SLAB.get());
            pOutput.accept(SnowBlockRegistry.FROSTED_FRIGIDITE_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.FROSTED_FRIGIDITE_WALL.get());
            pOutput.accept(SnowBlockRegistry.ICE_BRICKS_SLAB.get());
            pOutput.accept(SnowBlockRegistry.ICE_BRICKS_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.ICE_BRICKS_WALL.get());
            pOutput.accept(SnowBlockRegistry.KORISTONE_SLAB.get());
            pOutput.accept(SnowBlockRegistry.KORISTONE_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.KORISTONE_WALL.get());
            pOutput.accept(SnowBlockRegistry.KORISTONE_BRICKS_SLAB.get());
            pOutput.accept(SnowBlockRegistry.KORISTONE_BRICKS_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.KORISTONE_BRICKS_WALL.get());
            pOutput.accept(SnowBlockRegistry.PACKED_ICE_BRICKS_SLAB.get());
            pOutput.accept(SnowBlockRegistry.PACKED_ICE_BRICKS_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.PACKED_ICE_BRICKS_WALL.get());
            pOutput.accept(SnowBlockRegistry.PACKED_SNOW_SLAB.get());
            pOutput.accept(SnowBlockRegistry.PACKED_SNOW_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.PACKED_SNOW_WALL.get());
            pOutput.accept(SnowBlockRegistry.PACKED_SNOW_BRICKS_SLAB.get());
            pOutput.accept(SnowBlockRegistry.PACKED_SNOW_BRICKS_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.PACKED_SNOW_BRICKS_WALL.get());
            pOutput.accept(SnowBlockRegistry.SNOWY_COBBLESTONE_SLAB.get());
            pOutput.accept(SnowBlockRegistry.SNOWY_COBBLESTONE_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.SNOWY_COBBLESTONE_WALL.get());
            pOutput.accept(SnowBlockRegistry.SNOWY_STONE_BRICKS_SLAB.get());
            pOutput.accept(SnowBlockRegistry.SNOWY_STONE_BRICKS_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.SNOWY_STONE_BRICKS_WALL.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE_SLAB.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE_WALL.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE_BRICKS.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE_BRICKS_SLAB.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE_BRICKS_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE_BRICKS_WALL.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE_TILES.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE_TILES_SLAB.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE_TILES_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.HOWLITE_TILES_WALL.get());
            pOutput.accept(SnowBlockRegistry.POLISHED_HOWLITE.get());
            pOutput.accept(SnowBlockRegistry.POLISHED_HOWLITE_SLAB.get());
            pOutput.accept(SnowBlockRegistry.POLISHED_HOWLITE_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.POLISHED_HOWLITE_WALL.get());
            pOutput.accept(SnowBlockRegistry.BLUE_ICE_BRICKS_2.get());
            pOutput.accept(SnowBlockRegistry.BLUE_ICE_BRICKS_2_SLAB.get());
            pOutput.accept(SnowBlockRegistry.BLUE_ICE_BRICKS_2_STAIRS.get());
            pOutput.accept(SnowBlockRegistry.BLUE_ICE_BRICKS_2_WALL.get());
            
        })
        .build());

    public static void register(IEventBus pBus) {
        CREATIVE_TAB.register(pBus);
    }
}
