package net.technic.snow_update.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.entity.IceChunkEntity;
import net.technic.snow_update.entity.JuvenileYetiEntity;
import net.technic.snow_update.entity.SnowBallEntity;
import net.technic.snow_update.entity.SnowUpdateBoats;
import net.technic.snow_update.entity.TitanYetiEntity;
import net.technic.snow_update.entity.PenguinEntity;

public class SnowEntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SnowUpdate.MOD_ID);

    public static final RegistryObject<EntityType<JuvenileYetiEntity>> JUVENILE_YETI = ENTITY_TYPES.register("juvenile_yeti",
        ()-> EntityType.Builder.of(JuvenileYetiEntity::new, MobCategory.MONSTER).sized(2.0F, 2.0F).build("juvenile_yeti"));

    public static final RegistryObject<EntityType<TitanYetiEntity>> TITAN_YETI = ENTITY_TYPES.register("titan_yeti", 
        ()-> EntityType.Builder.of(TitanYetiEntity::new, MobCategory.MONSTER).sized(2.8F, 3F).build("titan_yeti"));

    public static final RegistryObject<EntityType<PenguinEntity>> PENGUIN = ENTITY_TYPES.register("penguin",
            ()-> EntityType.Builder.of(PenguinEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).build("penguin"));

    public static final RegistryObject<EntityType<SnowBallEntity>> SNOW_BALL = ENTITY_TYPES.register("snow_ball",
        ()-> EntityType.Builder.<SnowBallEntity>of(SnowBallEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
        .clientTrackingRange(4)
        .updateInterval(20)
        .build("snow_ball"));

    public static final RegistryObject<EntityType<IceChunkEntity>> ICE_CHUNK = ENTITY_TYPES.register("ice_chunk", 
        ()-> EntityType.Builder.<IceChunkEntity>of(IceChunkEntity::new, MobCategory.MISC)
        .sized(2F, 2F)
        .clientTrackingRange(10)
        .updateInterval(1)
        .setUpdateInterval(1)
        .setCustomClientFactory((spawnEntity, level) -> new IceChunkEntity(level))
        .build("ice_chunk"));

    public static final RegistryObject<EntityType<SnowUpdateBoats>> FROSTWOOD_BOAT = ENTITY_TYPES.register("frostwood_boat", ()-> EntityType.Builder.<SnowUpdateBoats>of(SnowUpdateBoats::new, MobCategory.MISC)
        .sized(1.375F, 0.5625F)
        .build("frostwood_boat"));
        
    public static final RegistryObject<EntityType<SnowUpdateBoats>> FROSTWOOD_CHESTBOAT = ENTITY_TYPES.register("frostwood_chestboat", ()-> EntityType.Builder.<SnowUpdateBoats>of(SnowUpdateBoats::new, MobCategory.MISC)
        .sized(1.375F, 0.5625F)
        .build("frostwood_boat"));

    public static void register(IEventBus pEventBus){
        ENTITY_TYPES.register(pEventBus);
    }
}
