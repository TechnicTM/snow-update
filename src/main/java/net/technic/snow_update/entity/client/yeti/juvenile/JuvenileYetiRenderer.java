package net.technic.snow_update.entity.client.yeti.juvenile;

import net.minecraft.client.renderer.entity.MobRenderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.entity.JuvenileYetiEntity;
import net.technic.snow_update.entity.layers.SnowUpdateLayers;

public class JuvenileYetiRenderer extends MobRenderer<JuvenileYetiEntity, JuvenileYetiModel<JuvenileYetiEntity>>{
    private ResourceLocation tex = new ResourceLocation(SnowUpdate.MOD_ID, "textures/entity/juvenile_yeti.png");

    public JuvenileYetiRenderer(Context pContext) {
        super(pContext, new JuvenileYetiModel<>(pContext.bakeLayer(SnowUpdateLayers.JUVENILE_YETI_LOCATION)), 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(JuvenileYetiEntity pEntity) {
        return tex;
    }

    @Override
    public void render(JuvenileYetiEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight){
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

}
