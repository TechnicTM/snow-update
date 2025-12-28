package net.technic.snow_update.entity.client.penguin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.entity.PenguinEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PenguinRenderer extends GeoEntityRenderer<PenguinEntity> {
    public PenguinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PenguinModel());
        this.shadowRadius = 0.5f;
        this.addRenderLayer(new PenguinMouthItemLayer(this, renderManager.getItemRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(PenguinEntity animatable) {
        if (animatable.isPesto()) {
            return new ResourceLocation(SnowUpdate.MOD_ID, "textures/entity/penguin/penguin_pesto.png");
        }
        return new ResourceLocation(SnowUpdate.MOD_ID, "textures/entity/penguin/penguin.png");
    }


    @Override
    public void render(PenguinEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource
            pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.4f,0.4f,0.4f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}