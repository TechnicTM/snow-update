package net.technic.snow_update.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.block.GlacierIceBlockEntity;

public class SnowBlockEntitiesRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SnowUpdate.MOD_ID);

    public static final RegistryObject<BlockEntityType<GlacierIceBlockEntity>> GLACIER_BLOCK_ENTITY = BLOCK_ENTITIES.register("glacier_block_entity", ()-> BlockEntityType.Builder.of(GlacierIceBlockEntity::new, 
        SnowBlockRegistry.GLACIER_ICE.get()).build(null));

    public static void register(IEventBus pBus) {
        BLOCK_ENTITIES.register(pBus);
    }
}
