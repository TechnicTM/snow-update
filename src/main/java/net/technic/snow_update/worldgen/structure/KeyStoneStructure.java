package net.technic.snow_update.worldgen.structure;

import java.util.Optional;

import com.mojang.serialization.Codec;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.init.SnowStructuresRegistry;

public class KeyStoneStructure extends Structure{
    public static final Codec<KeyStoneStructure> CODEC = simpleCodec(KeyStoneStructure::new);
    public static final ResourceLocation keystone_structure = new ResourceLocation(SnowUpdate.MOD_ID, "keystone");
    private static final int terrainHeightCheckRadius = 1;
    private static final int allowedTerrainRange = 8;

    public KeyStoneStructure(StructureSettings pSettings) {
        super(pSettings);
    }
    
    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext pContext) {
        if (!extraSpawningChecks(pContext)) {
            return Optional.empty();
        }
        StructureTemplate structureTemplate = pContext.structureTemplateManager().getOrCreate(keystone_structure);
        WorldgenRandom worldgenRandom = pContext.random();
        BlockPos initiaBlockPos = pContext.chunkPos().getWorldPosition();
        int height  = pContext.chunkGenerator().getFirstFreeHeight(initiaBlockPos.getX(), initiaBlockPos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, pContext.heightAccessor(), pContext.randomState());
        BlockPos finaBlockPos = new BlockPos(initiaBlockPos.getX(), height, initiaBlockPos.getZ());
        BlockPos blockpos = new BlockPos(structureTemplate.getSize().getX() / 2, 0, structureTemplate.getSize().getZ() / 2);
        Rotation rotation = Util.getRandom(Rotation.values(), worldgenRandom);
        Mirror mirror = worldgenRandom.nextFloat() < 0.5F ? Mirror.NONE : Mirror.FRONT_BACK;
        return Optional.of(new Structure.GenerationStub(finaBlockPos, pBuilder -> {
            pBuilder.addPiece(new KeyStoneStructurePieces(pContext.structureTemplateManager(), keystone_structure, finaBlockPos, rotation, mirror, blockpos));
        }));
    }

    protected boolean extraSpawningChecks(GenerationContext pContext) {
        int maxTerrain = Integer.MAX_VALUE;
        int minTerrain = Integer.MIN_VALUE;
        ChunkPos chunkPos = pContext.chunkPos();

        for (int curChunkX = chunkPos.x - terrainHeightCheckRadius; curChunkX <= chunkPos.x + terrainHeightCheckRadius; curChunkX++) {
            for (int curChunkZ = chunkPos.z - terrainHeightCheckRadius; curChunkZ <= chunkPos.z + terrainHeightCheckRadius; curChunkZ++) {
                int height = pContext.chunkGenerator().getBaseHeight((curChunkX << 4) + 7, (curChunkZ << 4) + 7, Heightmap.Types.WORLD_SURFACE_WG, pContext.heightAccessor(), pContext.randomState());
                maxTerrain = Math.max(maxTerrain, height);
                minTerrain = Math.min(minTerrain, height);
            }
        }

        if (allowedTerrainRange < maxTerrain - minTerrain) {
            return false;
        }

        return true;
    }

    @Override
    public StructureType<?> type() {
        return SnowStructuresRegistry.KEYSTONE_STRUCTURE.get();
    }

}
