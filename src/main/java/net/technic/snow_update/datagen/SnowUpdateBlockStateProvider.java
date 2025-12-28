package net.technic.snow_update.datagen;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.block.GlacierIce;
import net.technic.snow_update.block.KeyStone;
import net.technic.snow_update.block.properties.SnowUpdateBlockProperties;
import net.technic.snow_update.init.SnowBlockRegistry;

public class SnowUpdateBlockStateProvider extends BlockStateProvider{
    

    public SnowUpdateBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SnowUpdate.MOD_ID, existingFileHelper);
    }



    @Override
    protected void registerStatesAndModels() {
        createKeyStoneBlock();
        createGlacierIce();
        logBlock(((RotatedPillarBlock)SnowBlockRegistry.FROSTED_LOG.get()));
        axisBlock(((RotatedPillarBlock)SnowBlockRegistry.FROSTED_WOOD.get()), blockTexture(SnowBlockRegistry.FROSTED_LOG.get()), blockTexture(SnowBlockRegistry.FROSTED_LOG.get()));
        axisBlock(((RotatedPillarBlock)SnowBlockRegistry.STRIPPED_FROSTED_LOG.get()), blockTexture(SnowBlockRegistry.STRIPPED_FROSTED_LOG.get()), 
            new ResourceLocation(SnowUpdate.MOD_ID, "block/stripped_frosted_log_top"));
        axisBlock(((RotatedPillarBlock)SnowBlockRegistry.STRIPPED_FROSTED_WOOD.get()), blockTexture(SnowBlockRegistry.STRIPPED_FROSTED_LOG.get()),
        blockTexture(SnowBlockRegistry.STRIPPED_FROSTED_LOG.get()));
        leavesBlock(SnowBlockRegistry.FROSTED_LEAVES.get());
        blockAndItem(SnowBlockRegistry.FROSTED_PLANKS);
        blockAndItem(SnowBlockRegistry.BUDDING_ICE_CRYSTAL);
        saplingBlock(SnowBlockRegistry.SMALL_ICE_CRYSTAL_BUD);
        saplingBlock(SnowBlockRegistry.MEDIUM_ICE_CRYSTAL_BUD);
        saplingBlock(SnowBlockRegistry.LARGE_ICE_CRYSTAL_BUD);
        saplingBlock(SnowBlockRegistry.ICE_CRYSTAL_CLUSTER);
        blockItem(SnowBlockRegistry.FROSTED_LOG);
        blockItem(SnowBlockRegistry.FROSTED_WOOD);
        blockItem(SnowBlockRegistry.STRIPPED_FROSTED_LOG);
        blockItem(SnowBlockRegistry.STRIPPED_FROSTED_WOOD);
        blockAndItem(SnowBlockRegistry.SNOWY_COBBLESTONE);
        blockAndItem(SnowBlockRegistry.CHISELED_FRIGIDITE);
        blockAndItem(SnowBlockRegistry.CHISELED_KORISTONE);
        blockAndItem(SnowBlockRegistry.FRIGIDITE);
        blockAndItem(SnowBlockRegistry.FRIGIDITE_BRICKS);
        blockAndItem(SnowBlockRegistry.FROSTED_FRIGIDITE);
        blockAndItem(SnowBlockRegistry.GLACIER_BLOCK);
        blockAndItem(SnowBlockRegistry.GLACIER_CRYSTAL);
        blockAndItem(SnowBlockRegistry.PACKED_ICE_BRICKS);
        blockAndItem(SnowBlockRegistry.PACKED_SNOW);
        blockAndItem(SnowBlockRegistry.PACKED_SNOW_BRICKS);
        blockAndItem(SnowBlockRegistry.POLISHED_BLUE_ICE);
        blockAndItem(SnowBlockRegistry.POLISHED_FRIGITIDE);
        blockAndItem(SnowBlockRegistry.POLISHED_KORISTONE);
        blockAndItem(SnowBlockRegistry.POLISHED_PACKED_ICE);
        blockAndItem(SnowBlockRegistry.SHREDDED_KORISTONE);
        blockAndItem(SnowBlockRegistry.SNOW_BRICKS);
        blockAndItem(SnowBlockRegistry.CHISELED_HOWLITE);
        blockAndItem(SnowBlockRegistry.CHISELED_HOWLITE_BRICKS);
        blockAndItem(SnowBlockRegistry.CHISELED_HOWLITE_TILES);
        blockAndItem(SnowBlockRegistry.CHISELED_POLISHED_HOWLITE);
        blockAndItem(SnowBlockRegistry.HOWLITE);
        blockAndItem(SnowBlockRegistry.HOWLITE_BRICKS);
        blockAndItem(SnowBlockRegistry.HOWLITE_TILES);
        blockAndItem(SnowBlockRegistry.POLISHED_HOWLITE);
        snowyStoneBricks(SnowBlockRegistry.SNOWY_STONE_BRICKS);
        blockAndItem(SnowBlockRegistry.KORISTONE);
        blockAndItem(SnowBlockRegistry.KORISTONE_BRICKS);
        blockAndItem(SnowBlockRegistry.BLUE_ICE_BRICKS);
        blockAndItem(SnowBlockRegistry.BLUE_ICE_BRICKS_2);
        blockAndItem(SnowBlockRegistry.COBBLED_FRIGIDITE);
        blockAndItem(SnowBlockRegistry.ICE_SPIKE_BLOCK);
        blockAndItem(SnowBlockRegistry.ICE_BRICKS);
        dripstone(SnowBlockRegistry.POINTED_ICE_STALACTITE);
        

        fenceBlock(((FenceBlock)SnowBlockRegistry.FROSTED_WOOD_FENCE.get()), blockTexture(SnowBlockRegistry.FROSTED_PLANKS.get()));

        slabBlock(((SlabBlock)SnowBlockRegistry.BLUE_ICE_BRICKS_SLAB.get()), blockTexture(SnowBlockRegistry.BLUE_ICE_BRICKS.get()), blockTexture(SnowBlockRegistry.BLUE_ICE_BRICKS.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.FROSTED_PLANKS_SLAB.get()), blockTexture(SnowBlockRegistry.FROSTED_PLANKS.get()), blockTexture(SnowBlockRegistry.FROSTED_PLANKS.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.COBBLED_FRIGIDITE_SLAB.get()), blockTexture(SnowBlockRegistry.COBBLED_FRIGIDITE.get()), blockTexture(SnowBlockRegistry.COBBLED_FRIGIDITE.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.FRIGIDITE_SLAB.get()), blockTexture(SnowBlockRegistry.FRIGIDITE.get()), blockTexture(SnowBlockRegistry.FRIGIDITE.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.FRIGIDITE_BRICKS_SLAB.get()), blockTexture(SnowBlockRegistry.FRIGIDITE_BRICKS.get()), blockTexture(SnowBlockRegistry.FRIGIDITE_BRICKS.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.FROSTED_FRIGIDITE_SLAB.get()), blockTexture(SnowBlockRegistry.FROSTED_FRIGIDITE.get()), blockTexture(SnowBlockRegistry.FROSTED_FRIGIDITE.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.ICE_BRICKS_SLAB.get()), blockTexture(SnowBlockRegistry.ICE_BRICKS.get()), blockTexture(SnowBlockRegistry.ICE_BRICKS.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.KORISTONE_SLAB.get()), blockTexture(SnowBlockRegistry.KORISTONE.get()), blockTexture(SnowBlockRegistry.KORISTONE.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.KORISTONE_BRICKS_SLAB.get()), blockTexture(SnowBlockRegistry.KORISTONE_BRICKS.get()), blockTexture(SnowBlockRegistry.KORISTONE_BRICKS.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.PACKED_ICE_BRICKS_SLAB.get()), blockTexture(SnowBlockRegistry.PACKED_ICE_BRICKS.get()), blockTexture(SnowBlockRegistry.PACKED_ICE_BRICKS.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.PACKED_SNOW_SLAB.get()), blockTexture(SnowBlockRegistry.PACKED_SNOW.get()), blockTexture(SnowBlockRegistry.PACKED_SNOW.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.PACKED_SNOW_BRICKS_SLAB.get()), blockTexture(SnowBlockRegistry.PACKED_SNOW_BRICKS.get()), blockTexture(SnowBlockRegistry.PACKED_SNOW_BRICKS.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.SNOWY_COBBLESTONE_SLAB.get()), blockTexture(SnowBlockRegistry.SNOWY_COBBLESTONE.get()), blockTexture(SnowBlockRegistry.SNOWY_COBBLESTONE.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.SNOWY_STONE_BRICKS_SLAB.get()), blockTexture(SnowBlockRegistry.SNOWY_STONE_BRICKS.get()), 
        blockTexture(SnowBlockRegistry.SNOWY_STONE_BRICKS.get()), blockTexture(SnowBlockRegistry.SNOWY_STONE_BRICKS.get()), mcLoc("block/snow"));
        slabBlock(((SlabBlock)SnowBlockRegistry.HOWLITE_SLAB.get()), blockTexture(SnowBlockRegistry.HOWLITE.get()), blockTexture(SnowBlockRegistry.HOWLITE.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.HOWLITE_BRICKS_SLAB.get()), blockTexture(SnowBlockRegistry.HOWLITE_BRICKS.get()), blockTexture(SnowBlockRegistry.HOWLITE_BRICKS.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.HOWLITE_TILES_SLAB.get()), blockTexture(SnowBlockRegistry.HOWLITE_TILES.get()), blockTexture(SnowBlockRegistry.HOWLITE_TILES.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.POLISHED_HOWLITE_SLAB.get()), blockTexture(SnowBlockRegistry.POLISHED_HOWLITE.get()), blockTexture(SnowBlockRegistry.POLISHED_HOWLITE.get()));
        slabBlock(((SlabBlock)SnowBlockRegistry.BLUE_ICE_BRICKS_2_SLAB.get()), blockTexture(SnowBlockRegistry.BLUE_ICE_BRICKS_2.get()), blockTexture(SnowBlockRegistry.BLUE_ICE_BRICKS_2.get()));


        stairsBlock(((StairBlock)SnowBlockRegistry.FROSTED_PLANKS_STAIRS.get()), blockTexture(SnowBlockRegistry.FROSTED_PLANKS.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.BLUE_ICE_BRICKS_STAIRS.get()), blockTexture(SnowBlockRegistry.BLUE_ICE_BRICKS.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.COBBLED_FRIGIDITE_STAIRS.get()), blockTexture(SnowBlockRegistry.COBBLED_FRIGIDITE.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.FRIGIDITE_STAIRS.get()), blockTexture(SnowBlockRegistry.FRIGIDITE.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.FRIGIDITE_BRICKS_STAIRS.get()), blockTexture(SnowBlockRegistry.FRIGIDITE_BRICKS.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.FROSTED_FRIGIDITE_STAIRS.get()), blockTexture(SnowBlockRegistry.FROSTED_FRIGIDITE.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.ICE_BRICKS_STAIRS.get()), blockTexture(SnowBlockRegistry.ICE_BRICKS.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.KORISTONE_STAIRS.get()), blockTexture(SnowBlockRegistry.KORISTONE.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.KORISTONE_BRICKS_STAIRS.get()), blockTexture(SnowBlockRegistry.KORISTONE_BRICKS.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.PACKED_ICE_BRICKS_STAIRS.get()), blockTexture(SnowBlockRegistry.PACKED_ICE_BRICKS.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.PACKED_SNOW_STAIRS.get()), blockTexture(SnowBlockRegistry.PACKED_SNOW.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.PACKED_SNOW_BRICKS_STAIRS.get()), blockTexture(SnowBlockRegistry.PACKED_SNOW_BRICKS.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.SNOWY_COBBLESTONE_STAIRS.get()), blockTexture(SnowBlockRegistry.SNOWY_COBBLESTONE.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.SNOWY_STONE_BRICKS_STAIRS.get()), blockTexture(SnowBlockRegistry.SNOWY_STONE_BRICKS.get()), 
        blockTexture(SnowBlockRegistry.SNOWY_STONE_BRICKS.get()), mcLoc("block/snow"));
        stairsBlock(((StairBlock)SnowBlockRegistry.HOWLITE_STAIRS.get()), blockTexture(SnowBlockRegistry.HOWLITE.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.HOWLITE_BRICKS_STAIRS.get()), blockTexture(SnowBlockRegistry.HOWLITE_BRICKS.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.HOWLITE_TILES_STAIRS.get()), blockTexture(SnowBlockRegistry.HOWLITE_TILES.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.POLISHED_HOWLITE_STAIRS.get()), blockTexture(SnowBlockRegistry.POLISHED_HOWLITE.get()));
        stairsBlock(((StairBlock)SnowBlockRegistry.BLUE_ICE_BRICKS_2_STAIRS.get()), blockTexture(SnowBlockRegistry.BLUE_ICE_BRICKS_2.get()));

        
        wallBlock(((WallBlock)SnowBlockRegistry.BLUE_ICE_BRICKS_WALL.get()), blockTexture(SnowBlockRegistry.BLUE_ICE_BRICKS.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.COBBLED_FRIGIDITE_WALL.get()), blockTexture(SnowBlockRegistry.COBBLED_FRIGIDITE.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.FRIGIDITE_WALL.get()), blockTexture(SnowBlockRegistry.FRIGIDITE.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.FRIGIDITE_BRICKS_WALL.get()), blockTexture(SnowBlockRegistry.FRIGIDITE_BRICKS.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.FROSTED_FRIGIDITE_WALL.get()), blockTexture(SnowBlockRegistry.FROSTED_FRIGIDITE.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.ICE_BRICKS_WALL.get()), blockTexture(SnowBlockRegistry.ICE_BRICKS.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.KORISTONE_WALL.get()), blockTexture(SnowBlockRegistry.KORISTONE.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.KORISTONE_BRICKS_WALL.get()), blockTexture(SnowBlockRegistry.KORISTONE_BRICKS.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.PACKED_ICE_BRICKS_WALL.get()), blockTexture(SnowBlockRegistry.PACKED_ICE_BRICKS.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.PACKED_SNOW_WALL.get()), blockTexture(SnowBlockRegistry.PACKED_SNOW.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.PACKED_SNOW_BRICKS_WALL.get()), blockTexture(SnowBlockRegistry.PACKED_SNOW_BRICKS.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.SNOWY_COBBLESTONE_WALL.get()), blockTexture(SnowBlockRegistry.SNOWY_COBBLESTONE.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.SNOWY_STONE_BRICKS_WALL.get()), blockTexture(SnowBlockRegistry.SNOWY_STONE_BRICKS.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.HOWLITE_WALL.get()), blockTexture(SnowBlockRegistry.HOWLITE.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.HOWLITE_BRICKS_WALL.get()), blockTexture(SnowBlockRegistry.HOWLITE_BRICKS.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.HOWLITE_TILES_WALL.get()), blockTexture(SnowBlockRegistry.HOWLITE_TILES.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.POLISHED_HOWLITE_WALL.get()), blockTexture(SnowBlockRegistry.POLISHED_HOWLITE.get()));
        wallBlock(((WallBlock)SnowBlockRegistry.BLUE_ICE_BRICKS_2_WALL.get()), blockTexture(SnowBlockRegistry.BLUE_ICE_BRICKS_2.get()));


        blockItem(SnowBlockRegistry.FROSTED_PLANKS_SLAB);
        blockItem(SnowBlockRegistry.FROSTED_PLANKS_STAIRS);

        blockItem(SnowBlockRegistry.BLUE_ICE_BRICKS_SLAB);
        blockItem(SnowBlockRegistry.BLUE_ICE_BRICKS_STAIRS);
        wallItem(SnowBlockRegistry.BLUE_ICE_BRICKS_WALL, SnowBlockRegistry.BLUE_ICE_BRICKS);

        blockItem(SnowBlockRegistry.COBBLED_FRIGIDITE_SLAB);
        blockItem(SnowBlockRegistry.COBBLED_FRIGIDITE_STAIRS);
        wallItem(SnowBlockRegistry.COBBLED_FRIGIDITE_WALL, SnowBlockRegistry.COBBLED_FRIGIDITE);

        blockItem(SnowBlockRegistry.FRIGIDITE_SLAB);
        blockItem(SnowBlockRegistry.FRIGIDITE_STAIRS);
        wallItem(SnowBlockRegistry.FRIGIDITE_WALL, SnowBlockRegistry.FRIGIDITE);

        blockItem(SnowBlockRegistry.FRIGIDITE_BRICKS_SLAB);
        blockItem(SnowBlockRegistry.FRIGIDITE_BRICKS_STAIRS);
        wallItem(SnowBlockRegistry.FRIGIDITE_BRICKS_WALL, SnowBlockRegistry.FRIGIDITE_BRICKS);

        blockItem(SnowBlockRegistry.FROSTED_FRIGIDITE_SLAB);
        blockItem(SnowBlockRegistry.FROSTED_FRIGIDITE_STAIRS);
        wallItem(SnowBlockRegistry.FROSTED_FRIGIDITE_WALL, SnowBlockRegistry.FROSTED_FRIGIDITE);

        blockItem(SnowBlockRegistry.ICE_BRICKS_SLAB);
        blockItem(SnowBlockRegistry.ICE_BRICKS_STAIRS);
        wallItem(SnowBlockRegistry.ICE_BRICKS_WALL, SnowBlockRegistry.ICE_BRICKS);

        blockItem(SnowBlockRegistry.KORISTONE_SLAB);
        blockItem(SnowBlockRegistry.KORISTONE_STAIRS);
        wallItem(SnowBlockRegistry.KORISTONE_WALL, SnowBlockRegistry.KORISTONE);

        blockItem(SnowBlockRegistry.KORISTONE_BRICKS_SLAB);
        blockItem(SnowBlockRegistry.KORISTONE_BRICKS_STAIRS);
        wallItem(SnowBlockRegistry.KORISTONE_BRICKS_WALL, SnowBlockRegistry.KORISTONE_BRICKS);

        blockItem(SnowBlockRegistry.PACKED_ICE_BRICKS_SLAB);
        blockItem(SnowBlockRegistry.PACKED_ICE_BRICKS_STAIRS);
        wallItem(SnowBlockRegistry.PACKED_ICE_BRICKS_WALL, SnowBlockRegistry.PACKED_ICE_BRICKS);

        blockItem(SnowBlockRegistry.PACKED_SNOW_SLAB);
        blockItem(SnowBlockRegistry.PACKED_SNOW_STAIRS);
        wallItem(SnowBlockRegistry.PACKED_SNOW_WALL, SnowBlockRegistry.PACKED_SNOW);

        blockItem(SnowBlockRegistry.PACKED_SNOW_BRICKS_SLAB);
        blockItem(SnowBlockRegistry.PACKED_SNOW_BRICKS_STAIRS);
        wallItem(SnowBlockRegistry.PACKED_SNOW_BRICKS_WALL, SnowBlockRegistry.PACKED_SNOW_BRICKS);

        blockItem(SnowBlockRegistry.SNOWY_COBBLESTONE_SLAB);
        blockItem(SnowBlockRegistry.SNOWY_COBBLESTONE_STAIRS);
        wallItem(SnowBlockRegistry.SNOWY_COBBLESTONE_WALL, SnowBlockRegistry.SNOWY_COBBLESTONE);

        blockItem(SnowBlockRegistry.SNOWY_STONE_BRICKS_SLAB);
        blockItem(SnowBlockRegistry.SNOWY_STONE_BRICKS_STAIRS);
        wallItem(SnowBlockRegistry.SNOWY_STONE_BRICKS_WALL, SnowBlockRegistry.SNOWY_STONE_BRICKS);

        blockItem(SnowBlockRegistry.HOWLITE_SLAB);
        blockItem(SnowBlockRegistry.HOWLITE_STAIRS);
        wallItem(SnowBlockRegistry.HOWLITE_WALL, SnowBlockRegistry.HOWLITE);

        blockItem(SnowBlockRegistry.HOWLITE_BRICKS_SLAB);
        blockItem(SnowBlockRegistry.HOWLITE_BRICKS_STAIRS);
        wallItem(SnowBlockRegistry.HOWLITE_BRICKS_WALL, SnowBlockRegistry.HOWLITE_BRICKS);

        blockItem(SnowBlockRegistry.HOWLITE_TILES_SLAB);
        blockItem(SnowBlockRegistry.HOWLITE_TILES_STAIRS);
        wallItem(SnowBlockRegistry.HOWLITE_TILES_WALL, SnowBlockRegistry.HOWLITE_TILES);

        blockItem(SnowBlockRegistry.POLISHED_HOWLITE_SLAB);
        blockItem(SnowBlockRegistry.POLISHED_HOWLITE_STAIRS);
        wallItem(SnowBlockRegistry.POLISHED_HOWLITE_WALL, SnowBlockRegistry.POLISHED_HOWLITE);

        blockItem(SnowBlockRegistry.BLUE_ICE_BRICKS_2_SLAB);
        blockItem(SnowBlockRegistry.BLUE_ICE_BRICKS_2_STAIRS);
        wallItem(SnowBlockRegistry.BLUE_ICE_BRICKS_2_WALL, SnowBlockRegistry.BLUE_ICE_BRICKS_2);

        
        blockItem(SnowBlockRegistry.FROSTWOOD_FENCE_GATE);
        blockAndItem(SnowBlockRegistry.POLISHED_GLACIER_BLOCK);
        fenceGateBlock(((FenceGateBlock)SnowBlockRegistry.FROSTWOOD_FENCE_GATE.get()), blockTexture(SnowBlockRegistry.FROSTED_PLANKS.get()));
        createColumnBlock(SnowBlockRegistry.FROSTED_LEAVES_SPIKES);
        itemModels().getBuilder(ForgeRegistries.BLOCKS.getKey(SnowBlockRegistry.FROSTED_LEAVES_SPIKES.get()).getPath()).parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated"))).texture("layer0", 
        blockTexture(SnowBlockRegistry.FROSTED_LEAVES_SPIKES.get()));
        saplingBlock(SnowBlockRegistry.FROSTWOOD_SAPLING);
        //itemModels().getBuilder(ForgeRegistries.BLOCKS.getKey(SnowBlockRegistry.FROSTWOOD_SAPLING.get()).getPath()).parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated"))).texture("layer0", 
        //blockTexture(SnowBlockRegistry.FROSTWOOD_SAPLING.get()));

        grassBlock(SnowBlockRegistry.SNOWY_GRASS);
        doubleGrassBlock(SnowBlockRegistry.SNOWY_TALL_GRASS);

        doorBlockWithRenderType(((DoorBlock)SnowBlockRegistry.FROSTWOOD_DOOR.get()), modLoc("block/frostwood_door_bottom"), modLoc("block/frostwood_door_top"), "cutout");
        itemModels().getBuilder(ForgeRegistries.BLOCKS.getKey(SnowBlockRegistry.FROSTWOOD_DOOR.get()).getPath())
        .parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated"))).texture("layer0", "item/"+SnowBlockRegistry.FROSTWOOD_DOOR.getId().getPath());
        trapdoorBlockWithRenderType(((TrapDoorBlock)SnowBlockRegistry.FROSTWOOD_TRAPDOOR.get()), modLoc("block/frostwood_trapdoor"), true, "cutout");

        blockItem(SnowBlockRegistry.FROSTWOOD_TRAPDOOR, "_bottom");
        

    }

    private void leavesBlock(@NotNull Block block) {
        simpleBlockWithItem(block, models().singleTexture(ForgeRegistries.BLOCKS.getKey(block).getPath(), new ResourceLocation("minecraft:block/leaves"), "all", 
        blockTexture(block)).renderType("cutout"));
    }

    private void dripstone(RegistryObject<Block> pObject) {
        Block block = pObject.get();
        ResourceLocation resourceLocation = new ResourceLocation(SnowUpdate.MOD_ID, ForgeRegistries.BLOCKS.getKey(block).getPath());
        itemModels().withExistingParent(resourceLocation.getPath(), mcLoc("item/generated")).texture("layer0", resourceLocation.withPrefix("block/").withSuffix("_tip"));
        getVariantBuilder(block).forAllStates(pState -> {
            switch (pState.getValue(SnowUpdateBlockProperties.ICE_STALACTITE_THICKNESS)) {
                case BASE:
                    if (pState.getValue(BlockStateProperties.VERTICAL_DIRECTION).equals(Direction.DOWN))
                        return ConfiguredModel.builder().modelFile(models().cross(resourceLocation.toString()+"_base", blockTexture(block).withSuffix("_base")).renderType("cutout")).build();
                    else 
                    return ConfiguredModel.builder().modelFile(models().cross(resourceLocation.toString()+"_base_up", blockTexture(block).withSuffix("_base_up")).renderType("cutout")).build();
                case FRUSTUM:
                    if (pState.getValue(BlockStateProperties.VERTICAL_DIRECTION).equals(Direction.DOWN))
                        return ConfiguredModel.builder().modelFile(models().cross(resourceLocation.toString()+"_frustum", blockTexture(block).withSuffix("_frustum")).renderType("cutout")).build();
                    else 
                        return ConfiguredModel.builder().modelFile(models().cross(resourceLocation.toString()+"_frustum_up", blockTexture(block).withSuffix("_frustum_up")).renderType("cutout")).build();
                case MIDDLE: 
                    if (pState.getValue(BlockStateProperties.VERTICAL_DIRECTION).equals(Direction.DOWN))
                        return ConfiguredModel.builder().modelFile(models().cross(resourceLocation.toString()+"_middle", blockTexture(block).withSuffix("_middle")).renderType("cutout")).build();
                    else 
                        return ConfiguredModel.builder().modelFile(models().cross(resourceLocation.toString()+"_middle_up", blockTexture(block).withSuffix("_middle_up")).renderType("cutout")).build();
                case TIP:
                    if (pState.getValue(BlockStateProperties.VERTICAL_DIRECTION).equals(Direction.DOWN))
                        return ConfiguredModel.builder().modelFile(models().cross(resourceLocation.toString()+"_tip", blockTexture(block).withSuffix("_tip")).renderType("cutout")).build();
                    else 
                        return ConfiguredModel.builder().modelFile(models().cross(resourceLocation.toString()+"_tip_up", blockTexture(block).withSuffix("_tip_up")).renderType("cutout")).build();
                case TIP_MERGE:
                    if (pState.getValue(BlockStateProperties.VERTICAL_DIRECTION).equals(Direction.DOWN))
                        return ConfiguredModel.builder().modelFile(models().cross(resourceLocation.toString()+"_tip_merge", blockTexture(block).withSuffix("_tip_merge")).renderType("cutout")).build();
                    else 
                        return ConfiguredModel.builder().modelFile(models().cross(resourceLocation.toString()+"_tip_merge_up", blockTexture(block).withSuffix("_tip_merge_up")).renderType("cutout")).build();
                default:
                    return null;
            }
        });
    }

    private void createColumnBlock(RegistryObject<Block> pObject) {
        Block block = pObject.get();
        ResourceLocation resourceLocation = new ResourceLocation(SnowUpdate.MOD_ID, ForgeRegistries.BLOCKS.getKey(block).getPath());
        getVariantBuilder(block).forAllStates(pState -> {
            if (pState.getValue(BlockStateProperties.FACING).equals(Direction.DOWN) ) {
                return ConfiguredModel.builder().modelFile(models().getExistingFile(resourceLocation)).rotationX(180).build();
            } else if (pState.getValue(BlockStateProperties.FACING).equals(Direction.EAST)) {
                return ConfiguredModel.builder().modelFile(models().getExistingFile(resourceLocation)).rotationX(90).rotationY(90).build();
            } else if (pState.getValue(BlockStateProperties.FACING).equals(Direction.NORTH)) {
                return ConfiguredModel.builder().modelFile(models().getExistingFile(resourceLocation)).rotationX(90).build();
            } else if (pState.getValue(BlockStateProperties.FACING).equals(Direction.SOUTH)) {
                return ConfiguredModel.builder().modelFile(models().getExistingFile(resourceLocation)).rotationX(90).rotationY(180).build();
            } else if (pState.getValue(BlockStateProperties.FACING).equals(Direction.UP)) {
                return ConfiguredModel.builder().modelFile(models().getExistingFile(resourceLocation)).build();
            } else if (pState.getValue(BlockStateProperties.FACING).equals(Direction.WEST)) {
                return ConfiguredModel.builder().modelFile(models().getExistingFile(resourceLocation)).rotationX(90).rotationY(270).build();
            } else return null;
        });
    }

    private void snowyStoneBricks(RegistryObject<Block> pObject) {
        simpleBlockWithItem(pObject.get(), models().cubeColumn(ForgeRegistries.BLOCKS.getKey(pObject.get()).getPath(), blockTexture(pObject.get()), mcLoc("block/snow")));
    }

    private void wallItem(RegistryObject<Block> pObject, RegistryObject<Block> pBase) {
        itemModels().withExistingParent(ForgeRegistries.BLOCKS.getKey(pObject.get()).getPath(), mcLoc("block/wall_inventory"))
        .texture("wall",  new ResourceLocation(SnowUpdate.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(pBase.get()).getPath()));
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
                itemModels().getBuilder(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()).parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated"))).texture("layer0", 
                blockTexture(blockRegistryObject.get()));
    }

    private void doubleGrassBlock(RegistryObject<Block> pObject) {
        ResourceLocation resourceLocation = ForgeRegistries.BLOCKS.getKey(pObject.get());
        getVariantBuilder(pObject.get()).forAllStates(pState -> {
            if (pState.getValue(DoublePlantBlock.HALF).equals(DoubleBlockHalf.UPPER)) {
                return ConfiguredModel.builder().modelFile(models().withExistingParent(resourceLocation.getPath()+"_top", new ResourceLocation(SnowUpdate.MOD_ID, "block/tinted_grass")).texture("cross", 
                new ResourceLocation(SnowUpdate.MOD_ID, "block/"+resourceLocation.getPath()+"_top_tint")).texture("snow", "block/"+resourceLocation.getPath()+"_top")).build();
            } else {
                return ConfiguredModel.builder().modelFile(models().withExistingParent(resourceLocation.getPath()+"_bottom", new ResourceLocation(SnowUpdate.MOD_ID, "block/tinted_grass")).texture("cross", 
                new ResourceLocation(SnowUpdate.MOD_ID, "block/"+resourceLocation.getPath()+"_bottom_tint")).texture("snow", "block/"+resourceLocation.getPath()+"_bottom")).build();
            }
        });
        itemModels().getBuilder(resourceLocation.getPath()).parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated"))).texture("layer0", new ResourceLocation(SnowUpdate.MOD_ID, "block/"+resourceLocation.getPath()+"_top_tint"))
        .texture("layer1", "block/"+resourceLocation.getPath()+"_top");
    }

    private void grassBlock(RegistryObject<Block> pObject) {
        simpleBlock(pObject.get(), models().withExistingParent(ForgeRegistries.BLOCKS.getKey(pObject.get()).getPath(), new ResourceLocation(SnowUpdate.MOD_ID, "block/tinted_grass")).texture("cross", 
        new ResourceLocation(SnowUpdate.MOD_ID, "block/"+ForgeRegistries.BLOCKS.getKey(pObject.get()).getPath()+"_tint")).texture("snow", "block/"+ForgeRegistries.BLOCKS.getKey(pObject.get()).getPath()));
        itemModels().getBuilder(ForgeRegistries.BLOCKS.getKey(pObject.get()).getPath()).parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated"))).texture("layer0", 
        new ResourceLocation(SnowUpdate.MOD_ID, "block/"+ForgeRegistries.BLOCKS.getKey(pObject.get()).getPath()+"_tint")).texture("layer1", "block/"+ForgeRegistries.BLOCKS.getKey(pObject.get()).getPath());
    }

    private void createKeyStoneBlock(){
        getVariantBuilder(SnowBlockRegistry.KEY_STONE.get()).forAllStates(pState -> {
            if (pState.getValue(KeyStone.HAS_KEY)){
                return ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(SnowUpdate.MOD_ID, "block/keystone_with_key")))
                .rotationY((int)(pState.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180)%360).build();
            } else {
                return ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(SnowUpdate.MOD_ID, "block/keystone")))
                .rotationY((int)(pState.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180)%360).build();
            }
            
        });
        simpleBlockItem(SnowBlockRegistry.KEY_STONE.get(), models().getExistingFile(new ResourceLocation(SnowUpdate.MOD_ID, "block/keystone")));
    }

    private void blockAndItem(RegistryObject<Block> registryObject) {
        simpleBlockWithItem(registryObject.get(), cubeAll(registryObject.get()));
    }

    private void blockItem(RegistryObject<Block> registryObject) {
        simpleBlockItem(registryObject.get(), new ModelFile.UncheckedModelFile(SnowUpdate.MOD_ID + ":block/" + ForgeRegistries.BLOCKS.getKey(registryObject.get()).getPath()));
    }

    private void blockItem(RegistryObject<Block> registryObject, String appendix) {
        simpleBlockItem(registryObject.get(), new ModelFile.UncheckedModelFile(SnowUpdate.MOD_ID + ":block/" + ForgeRegistries.BLOCKS.getKey(registryObject.get()).getPath()+appendix));
    }
    
    private void createGlacierIce() {

        simpleBlockItem(SnowBlockRegistry.GLACIER_ICE.get(), models().getExistingFile(new ResourceLocation(SnowUpdate.MOD_ID, "block/glacier_ice")));

        getVariantBuilder(SnowBlockRegistry.GLACIER_ICE.get()).forAllStates(pState -> {
            switch (pState.getValue(GlacierIce.AGE)) {
                case 0:
                    return ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(SnowUpdate.MOD_ID, "block/glacier_ice"))).build();
                    
                case 1:
                    return ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(SnowUpdate.MOD_ID, "block/glacier_ice_1"))).build();
                
                case 2: 
                    return ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(SnowUpdate.MOD_ID, "block/glacier_ice_2"))).build();
                case 3: 
                    return ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(SnowUpdate.MOD_ID, "block/glacier_ice_3"))).build();

                default:
                    return null;
            }
        });
        
    }
    
    

}
