package net.technic.snow_update.entity.client.yeti.titan;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.entity.PartEntity;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.entity.TitanYetiEntity;
import net.technic.snow_update.entity.layers.SnowUpdateLayers;

public class TitanYetiRenderer extends MobRenderer<TitanYetiEntity, TitanYetiModel<TitanYetiEntity>>{

    private ResourceLocation tex = new ResourceLocation(SnowUpdate.MOD_ID, "textures/entity/titan_yeti.png");
    private ResourceLocation frozen = new ResourceLocation(SnowUpdate.MOD_ID, "textures/entity/titan_yeti_frozen.png");

    public TitanYetiRenderer(Context pContext) {
        super(pContext, new TitanYetiModel<>(pContext.bakeLayer(SnowUpdateLayers.TITAN_YETI_LAYER)), 1.2F);
    }
    
    @Override
    public void render(TitanYetiEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
            MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isStunned() || pEntity.isCrashing()){
            pPoseStack.pushPose();
            pPoseStack.translate(0, -1.4F, 0);
            super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
            pPoseStack.popPose();
        } else {
            super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        }
            
        if (pEntity.isMultipartEntity()){
            for (PartEntity<?> p : pEntity.getParts()){
                LevelRenderer.renderLineBox(pPoseStack, pBuffer.getBuffer(RenderType.LINES), p.getBoundingBox().move(-pEntity.getX(), -pEntity.getY(), -pEntity.getZ()), 0, 1, 0, 1);
            }
        }
        
    }
    @Override
    public ResourceLocation getTextureLocation(TitanYetiEntity pEntity) {
        return tex;
    }

    
    
}
