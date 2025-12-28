package net.technic.snow_update.worldgen.structure;



import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;

import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.entity.TitanYetiEntity;
import net.technic.snow_update.init.SnowEntityRegistry;
import net.technic.snow_update.init.SnowStructuresRegistry;
import org.slf4j.Logger;

public class KeyStoneStructurePieces extends StructurePiece {
    private static final Logger LOGGER = LogUtils.getLogger();
    protected final String templateName;
    protected StructureTemplate template;
    protected StructurePlaceSettings placeSettings;
    protected BlockPos templatePosition;

    public KeyStoneStructurePieces(StructureTemplateManager pStructureTemplateManager, ResourceLocation pLocation, BlockPos pTemplatePosition, Rotation pRotation, Mirror pMirror, BlockPos pPivotPos) {
        super(SnowStructuresRegistry.KEYSTONE_STRUCTURE_PIeCE.get(), 0, pStructureTemplateManager.getOrCreate(pLocation).getBoundingBox(makeSettings(pMirror, pRotation, pPivotPos), pTemplatePosition));
        //super(SnowStructuresRegistry.KEYSTONE_STRUCTURE_PIeCE.get(), 0, pStructureTemplateManager, pLocation, pLocation.toString(), makeSettings(pMirror, pRotation, pPivotPos), pTemplatePosition);
        this.setOrientation(Direction.NORTH);
        this.templateName = pLocation.toString();
        this.templatePosition = pTemplatePosition;
        this.template = pStructureTemplateManager.getOrCreate(pLocation);
        this.placeSettings = makeSettings(pMirror, pRotation, pPivotPos);
        
    }

    public KeyStoneStructurePieces(StructurePieceSerializationContext pContext, CompoundTag pTag) {
        super(SnowStructuresRegistry.KEYSTONE_STRUCTURE_PIeCE.get(), pTag);
        this.templateName = pTag.getString("Template");
        this.templatePosition = new BlockPos(pTag.getInt("TPX"), pTag.getInt("TPY"), pTag.getInt("TPZ"));
        StructureTemplateManager structureTemplateManager = pContext.structureTemplateManager();
        ResourceLocation resourcelocation = this.makeTemplateLocation();
        this.template = structureTemplateManager.getOrCreate(resourcelocation);
        BlockPos blockpos = new BlockPos(this.template.getSize().getX() / 2, 0, this.template.getSize().getZ() / 2);
        this.placeSettings = makeSettings(getMirror(), getRotation(), blockpos);
        
    }

    protected ResourceLocation makeTemplateLocation() {
        return new ResourceLocation(SnowUpdate.MOD_ID, "keystone");
    }
    

    private static StructurePlaceSettings makeSettings(Mirror pMirror, Rotation pRotation, BlockPos pPos) {
        StructurePlaceSettings structurePlaceSettings = new StructurePlaceSettings().setRotation(pRotation).setMirror(pMirror).setRotationPivot(pPos);
        return structurePlaceSettings;
    }

    public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {
        this.placeSettings.setBoundingBox(pBox);
        this.boundingBox = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
        if (this.template.placeInWorld(pLevel, this.templatePosition, pPos, this.placeSettings, pRandom, 2)) {

            for(StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo1 : this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.JIGSAW)) {
                if (structuretemplate$structureblockinfo1.nbt() != null) {
                String s = structuretemplate$structureblockinfo1.nbt().getString("final_state");
                BlockState blockstate = Blocks.AIR.defaultBlockState();

                try {
                    blockstate = BlockStateParser.parseForBlock(pLevel.holderLookup(Registries.BLOCK), s, true).blockState();
                } catch (CommandSyntaxException commandsyntaxexception) {
                    LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", s, structuretemplate$structureblockinfo1.pos());
                }

                pLevel.setBlock(structuretemplate$structureblockinfo1.pos(), blockstate, 3);
                }
            }
        }
        this.spawnTitanYeti(pLevel, pBox, 0, 0, 0);

    }
    
    private void spawnTitanYeti(WorldGenLevel pLevel, BoundingBox pBox, int pX, int pY, int pZ) {
        BlockPos blockPos = this.getWorldPos(pX, pY, pZ).immutable();
        Direction direction = getOrientation();
        BlockPos blockPos2;
        if (direction.equals(Direction.NORTH)) {
            blockPos2 = blockPos.offset(5,1,-5);
        } else if (direction.equals(Direction.SOUTH)) {
            blockPos2 = blockPos.offset(-5,1,5);
        } else if (direction.equals(Direction.EAST)) {
            blockPos2 = blockPos.offset(-5,1,-5);
        } else if (direction.equals(Direction.WEST)) {
            blockPos2 = blockPos.offset(5,1,5);
        } else 
            blockPos2 = blockPos;
        System.out.println(blockPos2+" "+direction);
        TitanYetiEntity titanYetiEntity = SnowEntityRegistry.TITAN_YETI.get().create(pLevel.getLevel());
        if (titanYetiEntity != null) {
            titanYetiEntity.heal(titanYetiEntity.getMaxHealth());
            titanYetiEntity.moveTo(blockPos2, 0, 0);
            titanYetiEntity.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(blockPos), MobSpawnType.STRUCTURE, null, null);
            pLevel.addFreshEntity(titanYetiEntity);
            System.out.println(titanYetiEntity.blockPosition());
        }
        
    }    

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
        pTag.putInt("TPX", this.templatePosition.getX());
        pTag.putInt("TPY", this.templatePosition.getY());
        pTag.putInt("TPZ", this.templatePosition.getZ());
        pTag.putString("Template", this.templateName);
    }


}
