package net.technic.snow_update.event;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.technic.snow_update.worldgen.biome.SnowUpdateBiomes;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "snow_update", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventBus {

    @SubscribeEvent
    public static void renderFog(ViewportEvent.RenderFog pRenderFog) {
        Camera camera = pRenderFog.getCamera();
        if (camera.getEntity() instanceof Player p && !p.isSpectator()) {
            Level level = camera.getEntity().level();
            if (level != null) {
                if (level.getBiome(camera.getBlockPosition()).is(SnowUpdateBiomes.ICE_CAVES)) {
                    pRenderFog.setNearPlaneDistance(pRenderFog.getFarPlaneDistance()*0.05f);
                    pRenderFog.scaleFarPlaneDistance(0.3f);
                    pRenderFog.setCanceled(true);
                }
            }
        }
    }
}
