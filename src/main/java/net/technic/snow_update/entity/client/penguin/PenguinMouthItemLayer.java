package net.technic.snow_update.entity.client.penguin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.technic.snow_update.entity.PenguinEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.RenderUtils;

import java.util.Optional;

public class PenguinMouthItemLayer extends GeoRenderLayer<PenguinEntity> {

    private final ItemRenderer itemRenderer;

    public PenguinMouthItemLayer(GeoRenderer<PenguinEntity> renderer, ItemRenderer itemRenderer) {
        super(renderer);
        this.itemRenderer = itemRenderer;
    }

    public void render(PoseStack poseStack,
                       PenguinEntity animatable,
                       BakedGeoModel bakedModel,
                       MultiBufferSource bufferSource,
                       float partialTick,
                       int packedLight,
                       int packedOverlay) {

        ItemStack stack = animatable.getMouthItem();
        if (stack == null || stack.isEmpty()) return;

        Optional<GeoBone> boneOpt = bakedModel.getBone("beak_item");
        if (boneOpt.isEmpty()) return;

        GeoBone bone = boneOpt.get();

        poseStack.pushPose();

        RenderUtils.translateAndRotateMatrixForBone(poseStack, bone);

        poseStack.scale(0.45f, 0.45f, 0.45f);

        this.itemRenderer.renderStatic(
                stack,
                ItemDisplayContext.GROUND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                animatable.level(),
                0
        );

        poseStack.popPose();
    }
}
