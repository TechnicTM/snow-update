package net.technic.snow_update.init;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.SnowUpdate;

public class SnowPaitingsRegistry {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, SnowUpdate.MOD_ID);

    public static final RegistryObject<PaintingVariant> SNOWY_HILLS = PAINTING_VARIANTS.register("snowy_hills", ()-> new PaintingVariant(32, 32));

    public static void register(IEventBus pBus) {
        PAINTING_VARIANTS.register(pBus);
    }
}
