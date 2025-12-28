package net.technic.snow_update.entity.client.penguin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.technic.snow_update.entity.PenguinEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PenguinModel extends GeoModel<PenguinEntity> {
    @Override
    public ResourceLocation getAnimationResource(PenguinEntity entity) {
        return new ResourceLocation("snow_update", "animations/penguin.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(PenguinEntity entity) {
        return new ResourceLocation("snow_update", "geo/penguin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PenguinEntity entity) {
        return new ResourceLocation("snow_update", "textures/entity/penguin/penguin.png");
    }

    @Override
    public void setCustomAnimations(PenguinEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

    }
}