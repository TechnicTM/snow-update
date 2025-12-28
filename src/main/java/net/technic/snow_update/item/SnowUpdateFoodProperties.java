package net.technic.snow_update.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.technic.snow_update.init.SnowEffectsRegistry;

public class SnowUpdateFoodProperties {
    public static final FoodProperties GLACIAL_RAW_YETI_MEAT = new FoodProperties.Builder().nutrition(2).saturationMod(0.25F)
    .effect(()-> new MobEffectInstance(SnowEffectsRegistry.FREEZE_EFFECT.get(), 300), 1).meat().build();

    public static final FoodProperties RAW_YETI_MEAT = new FoodProperties.Builder().nutrition(2).saturationMod(0.25F).meat().build();
    
    public static final FoodProperties COOKED_YETI_MEAT = new FoodProperties.Builder().nutrition(6).saturationMod(0.45F).meat().build();
}
