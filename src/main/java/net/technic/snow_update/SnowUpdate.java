package net.technic.snow_update;

import com.mojang.logging.LogUtils;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.technic.snow_update.entity.client.penguin.PenguinRenderer;
import net.technic.snow_update.event.ModEventBusEvents;
import net.technic.snow_update.init.*;
import net.technic.snow_update.item.SnowUpdateItemProperties;
import net.technic.snow_update.config.SnowUpdateConfig;
import net.technic.snow_update.entity.client.ice_chunk.IceChunkEntityRenderer;
import net.technic.snow_update.entity.client.yeti.juvenile.JuvenileYetiRenderer;
import net.technic.snow_update.entity.client.snowball.SnowBallRenderer;
import net.technic.snow_update.entity.client.SnowUpdateBoatRenderer;
import net.technic.snow_update.entity.client.yeti.titan.TitanYetiRenderer;
import net.technic.snow_update.handler.IniatializationHandler;
import net.technic.snow_update.worldgen.api.SurfaceRuleManager;
import net.technic.snow_update.worldgen.surface.SnowUpdateSurfaceRules;

import org.slf4j.Logger;

@Mod(SnowUpdate.MOD_ID)
public class SnowUpdate
{
    public static final String MOD_ID = "snow_update";
    public static SnowUpdateConfig CONFIG = new SnowUpdateConfig(FMLPaths.CONFIGDIR.get().resolve(SnowUpdate.MOD_ID+".toml"));
    public static final Logger LOGGER = LogUtils.getLogger();

    public SnowUpdate()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, IniatializationHandler::onServerAboutToStart);
        setCONFIG(CONFIG);
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
        SnowBlockRegistry.register(modEventBus);
        SnowItemsRegistry.register(modEventBus);

        SnowEntityRegistry.register(modEventBus);

        SnowSoundsRegistry.register(modEventBus);
        SnowEffectsRegistry.register(modEventBus);
        SnowPotions.register(modEventBus);

        SnowBlockEntitiesRegistry.register(modEventBus);
        SnowPaitingsRegistry.register(modEventBus);

        SnowMemoryModulesRegistry.register(modEventBus);

        SnowFeaturesRegistry.register(modEventBus);
        SnowStructuresRegistry.register(modEventBus);
        SnowUpdateTrunkPlacerRegistry.register(modEventBus);
        SnowUpdateFoliagePlacerRegistry.register(modEventBus);
        SnowUpdateRegionsRegistry.registerRegions();

        SnowCreativeTabRegistry.register(modEventBus);



    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        
    }
    
    public static void setCONFIG(SnowUpdateConfig pCONFIG) {
        CONFIG = pCONFIG;
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(()-> {
                EntityRenderers.register(SnowEntityRegistry.JUVENILE_YETI.get(), JuvenileYetiRenderer::new);
                EntityRenderers.register(SnowEntityRegistry.SNOW_BALL.get(), SnowBallRenderer::new);
                EntityRenderers.register(SnowEntityRegistry.TITAN_YETI.get(), TitanYetiRenderer::new);
                EntityRenderers.register(SnowEntityRegistry.PENGUIN.get(), PenguinRenderer::new);

                EntityRenderers.register(SnowEntityRegistry.ICE_CHUNK.get(), IceChunkEntityRenderer::new);
                EntityRenderers.register(SnowEntityRegistry.FROSTWOOD_BOAT.get(), pContext -> new SnowUpdateBoatRenderer(pContext, false));
                EntityRenderers.register(SnowEntityRegistry.FROSTWOOD_CHESTBOAT.get(), pContext -> new SnowUpdateBoatRenderer(pContext, true));
                SnowUpdateItemProperties.addItemProperties();
                SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, SnowUpdateSurfaceRules.makeRules());

                ItemBlockRenderTypes.setRenderLayer(SnowBlockRegistry.ICEBUD.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(SnowBlockRegistry.ICEBUD_BOTTOM.get(), RenderType.cutout());
            });
        }
    }
}
