package net.technic.snow_update.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.SnowUpdate;

public class SnowSoundsRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SnowUpdate.MOD_ID);

    public static final RegistryObject<SoundEvent> JUVENILE_YETI_IDLE = regsiterSoundEvents("juvenile_yeti_idle");

    public static final RegistryObject<SoundEvent> JUVENILE_YETI_HURT = regsiterSoundEvents("juvenile_yeti_hurt");

    public static final RegistryObject<SoundEvent> JUVENILE_YETI_DEATH = regsiterSoundEvents("juvenile_yeti_death");

    public static final RegistryObject<SoundEvent> YETI_HORN = regsiterSoundEvents("yeti_horn");
    
    public static final RegistryObject<SoundEvent> KEYSTONE_BREAK = regsiterSoundEvents("keystone_break");

    public static final RegistryObject<SoundEvent> KEYSTONE_STEP = regsiterSoundEvents("keystone_step");
    
    public static final RegistryObject<SoundEvent> KEYSTONE_HIT = regsiterSoundEvents("keystone_hit");

    public static final RegistryObject<SoundEvent> KEYSTONE_PLACE = regsiterSoundEvents("keystone_place");

    public static final RegistryObject<SoundEvent> KEYSTONE_SHARD_PLACE = regsiterSoundEvents("keystone_shard_place");

    public static final ForgeSoundType KEYSTONE = new ForgeSoundType(1F, 1F, ()-> KEYSTONE_BREAK.get() , ()-> KEYSTONE_STEP.get(), ()-> KEYSTONE_PLACE.get(), ()-> KEYSTONE_HIT.get(), ()-> KEYSTONE_HIT.get());

    public static final RegistryObject<SoundEvent> ICE_CHUNK_DROP = regsiterSoundEvents("ice_chunk_drop");

    public static final RegistryObject<SoundEvent> ICE_WAND_WAVE = regsiterSoundEvents("ice_wand_wave");

    public static void register(IEventBus pEventBus){
        SOUNDS.register(pEventBus);
    }

    private static RegistryObject<SoundEvent> regsiterSoundEvents(String string) {
        ResourceLocation id = new ResourceLocation(SnowUpdate.MOD_ID, string);
        return SOUNDS.register(string, ()-> SoundEvent.createVariableRangeEvent(id));
    }
}
