package net.technic.snow_update.event;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.entity.JuvenileYetiEntity;
import net.technic.snow_update.entity.TitanYetiEntity;
import net.technic.snow_update.entity.client.ice_chunk.IceChunkEntityModel;
import net.technic.snow_update.entity.PenguinEntity;
import net.technic.snow_update.entity.client.yeti.juvenile.JuvenileYetiModel;
import net.technic.snow_update.entity.client.snowball.SnowBallModel;
import net.technic.snow_update.entity.client.yeti.titan.TitanYetiModel;
import net.technic.snow_update.entity.client.armor.YetiArmorModel;
import net.technic.snow_update.entity.layers.SnowUpdateLayers;
import net.technic.snow_update.init.SnowEntityRegistry;

@Mod.EventBusSubscriber(modid = SnowUpdate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(SnowUpdateLayers.JUVENILE_YETI_LOCATION, JuvenileYetiModel::createBodyLayer);
        event.registerLayerDefinition(SnowUpdateLayers.SNOW_BALL_LOCATION, SnowBallModel::createBodyLayer);
        event.registerLayerDefinition(SnowUpdateLayers.TITAN_YETI_LAYER, TitanYetiModel::createBodyLayer);
        event.registerLayerDefinition(SnowUpdateLayers.YETI_FUR_ARMOR_LAYER_1, YetiArmorModel.Helmet::createBodyLayer);
        event.registerLayerDefinition(SnowUpdateLayers.YETI_FUR_ARMOR_LAYER_2, YetiArmorModel.Chestplate::createBodyLayer);
        event.registerLayerDefinition(SnowUpdateLayers.YETI_FUR_ARMOR_LAYER_3, YetiArmorModel.Leggings::createBodyLayer);
        event.registerLayerDefinition(SnowUpdateLayers.YETI_FUR_ARMOR_LAYER_4, YetiArmorModel.Boots::createBodyLayer);
        event.registerLayerDefinition(SnowUpdateLayers.ICE_CHUNK, IceChunkEntityModel::createBodyLayer);
        event.registerLayerDefinition(SnowUpdateLayers.FROSTWOOD_BOAT, BoatModel::createBodyModel);
        event.registerLayerDefinition(SnowUpdateLayers.FROSTWOOD_CHESTBOAT, ChestBoatModel::createBodyModel);

    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(SnowEntityRegistry.JUVENILE_YETI.get(), JuvenileYetiEntity.createAttributes().build());
        event.put(SnowEntityRegistry.TITAN_YETI.get(), TitanYetiEntity.createAttributes().build());
        event.put(SnowEntityRegistry.PENGUIN.get(), PenguinEntity.createAttributes().build());
    }

}
