package net.technic.snow_update.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.effect.FreezeEffect;

public class SnowEffectsRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SnowUpdate.MOD_ID);

    public static final RegistryObject<MobEffect> FREEZE_EFFECT = MOB_EFFECTS.register("freeze_effect", 
    ()-> new FreezeEffect(MobEffectCategory.NEUTRAL, 0x0099FF));

    public static void register(IEventBus pBus){
        MOB_EFFECTS.register(pBus);
    }
}
