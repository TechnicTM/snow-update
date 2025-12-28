package net.technic.snow_update.entity.client.snowball;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.entity.SnowBallEntity;
import net.technic.snow_update.entity.layers.SnowUpdateLayers;

public class SnowBallRenderer extends EntityRenderer<SnowBallEntity>{
    private static final ResourceLocation tex = new ResourceLocation(SnowUpdate.MOD_ID, "textures/entity/snow_ball.png");
    protected SnowBallModel<SnowBallEntity> model;

    public SnowBallRenderer(Context pContext) {
        super(pContext);
        this.model = new SnowBallModel<>(pContext.bakeLayer(SnowUpdateLayers.SNOW_BALL_LOCATION));
    }

    @Override
    public void render(SnowBallEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight){
        pPoseStack.pushPose();
        VertexConsumer pConsumer =  ItemRenderer.getFoilBufferDirect(pBuffer, this.model.renderType(tex), false, false);
        this.model.renderToBuffer(pPoseStack, pConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }
    @Override
    public ResourceLocation getTextureLocation(SnowBallEntity pEntity) {
        return tex;
    }

}
