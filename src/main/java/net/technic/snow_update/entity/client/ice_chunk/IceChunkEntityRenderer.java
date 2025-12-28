package net.technic.snow_update.entity.client.ice_chunk;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.entity.IceChunkEntity;
import net.technic.snow_update.entity.layers.SnowUpdateLayers;

public class IceChunkEntityRenderer extends EntityRenderer<IceChunkEntity>{
    IceChunkEntityModel<IceChunkEntity> model;
    ResourceLocation tex = new ResourceLocation(SnowUpdate.MOD_ID, "textures/entity/ice_chunk.png");

    public IceChunkEntityRenderer(Context pContext) {
        super(pContext);
        this.model = new IceChunkEntityModel<>(pContext.bakeLayer(SnowUpdateLayers.ICE_CHUNK));
    }

    @Override
    public void render(IceChunkEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight){
        pPoseStack.pushPose();
        VertexConsumer pConsumer =  ItemRenderer.getFoilBufferDirect(pBuffer, this.model.renderType(tex), false, false);
        pPoseStack.mulPose(new Quaternionf(0, 0, 1, 0));
        pPoseStack.translate(0, -1.5F, 0);
        this.model.renderToBuffer(pPoseStack, pConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(IceChunkEntity pEntity) {
        return tex;
    }

}
