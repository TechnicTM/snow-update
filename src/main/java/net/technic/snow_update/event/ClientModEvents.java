package net.technic.snow_update.event;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.init.SnowBlockRegistry;

@Mod.EventBusSubscriber(modid = SnowUpdate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block pEvent) {
        pEvent.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null && pPos != null ? BiomeColors.getAverageGrassColor(pLevel, pPos) : FoliageColor.getDefaultColor(), SnowBlockRegistry.SNOWY_GRASS.get());
        pEvent.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null && pPos != null ? BiomeColors.getAverageGrassColor(pLevel, pPos) : FoliageColor.getDefaultColor(), SnowBlockRegistry.SNOWY_TALL_GRASS.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item pEvent) {
        pEvent.register((pStack, pTintIndex) -> {
            if (pTintIndex == 1) return -1;
            return 0x7cbd6b;
        }, SnowBlockRegistry.SNOWY_GRASS.get());
        pEvent.register((pStack, pTintIndex) -> {
            if (pTintIndex == 1) return -1;
            return GrassColor.get(0.5D, 1.0D);
        }, SnowBlockRegistry.SNOWY_TALL_GRASS.get());
    }
}
