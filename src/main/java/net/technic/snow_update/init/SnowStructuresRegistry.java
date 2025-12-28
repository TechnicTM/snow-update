package net.technic.snow_update.init;

import com.mojang.serialization.Codec;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.worldgen.structure.KeyStoneStructure;
import net.technic.snow_update.worldgen.structure.KeyStoneStructurePieces;

public class SnowStructuresRegistry {
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, SnowUpdate.MOD_ID);
    public static final DeferredRegister<StructurePieceType> STRUCTURES_PICES = DeferredRegister.create(Registries.STRUCTURE_PIECE, SnowUpdate.MOD_ID);

    public static final RegistryObject<StructureType<?>> KEYSTONE_STRUCTURE = STRUCTURES.register("keystone", ()-> explicitStructureTypeTyping(KeyStoneStructure.CODEC));

    public static final RegistryObject<StructurePieceType> KEYSTONE_STRUCTURE_PIeCE = STRUCTURES_PICES.register("keystone_structure_piece", ()-> KeyStoneStructurePieces::new);

    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(Codec<T> structureCodec) {
        return () -> structureCodec;
    }

    public static void register(IEventBus pBus) {
        STRUCTURES.register(pBus);
        STRUCTURES_PICES.register(pBus);
    }
}
