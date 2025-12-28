package net.technic.snow_update.init;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.block.BuddingIceCrystal;
import net.technic.snow_update.block.FrostedWood;
import net.technic.snow_update.block.GlacierIce;
import net.technic.snow_update.block.KeyStone;
import net.technic.snow_update.block.PointedIceStalactite;
import net.technic.snow_update.worldgen.tree.FrostwoodTreeGrower;

public class SnowBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SnowUpdate.MOD_ID);
    
    public static final RegistryObject<Block> POINTED_ICE_STALACTITE = registerBlock("pointed_ice_stalactite", 
    ()-> new PointedIceStalactite(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().sound(SoundType.POINTED_DRIPSTONE).randomTicks()
    .dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).noLootTable()));

    public static final RegistryObject<Block> KEY_STONE = registerBlock("key_stone", ()-> new KeyStone(BlockBehaviour.Properties.of().sound(SnowSoundsRegistry.KEYSTONE).mapColor(MapColor.COLOR_BLUE).noLootTable()
    .strength(-1, 3600000.0F)));

    public static final RegistryObject<Block> GLACIER_ICE = registerBlock("glacier_ice", ()-> new GlacierIce(BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(-1, 3600000.0F).randomTicks()
    .noOcclusion().isSuffocating(SnowBlockRegistry::never).noLootTable()));

    public static final RegistryObject<Block> FROSTED_LOG = registerBlock("frosted_log", ()-> new FrostedWood(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> FROSTED_WOOD = registerBlock("frosted_wood", ()-> new FrostedWood(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block> STRIPPED_FROSTED_LOG = registerBlock("stripped_frosted_log", ()-> new FrostedWood(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<Block> STRIPPED_FROSTED_WOOD = registerBlock("stripped_frosted_wood", ()-> new FrostedWood(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistryObject<Block> FROSTED_PLANKS = registerBlock("frosted_planks", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)){
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 20;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }
    });
    public static final RegistryObject<Block> FROSTED_LEAVES = registerBlock("frosted_leaves", ()-> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)){
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 60;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }
    });

    public static final RegistryObject<Block> SNOWY_COBBLESTONE = registerBlock("snowy_cobblestone", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> COBBLED_FRIGIDITE = registerBlock("cobbled_frigidite", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> FROSTED_WOOD_FENCE = registerBlock("frosted_wood_fence", ()-> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));

    public static final RegistryObject<Block> FROSTED_PLANKS_SLAB = registerBlock("frosted_planks_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> FROSTED_PLANKS_STAIRS = registerBlock("frosted_planks_stairs", 
    ()-> new StairBlock(()-> SnowBlockRegistry.FROSTED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));

    public static final RegistryObject<Block> CHISELED_FRIGIDITE = registerBlock("chiseled_frigidite", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> CHISELED_KORISTONE = registerBlock("chiseled_koristone", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> FRIGIDITE = registerBlock("frigidite", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> FRIGIDITE_BRICKS = registerBlock("frigidite_bricks", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> FROSTED_FRIGIDITE = registerBlock("frosted_frigidite", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));

    public static final RegistryObject<Block> GLACIER_BLOCK = registerBlock("glacier_block_material", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> GLACIER_CRYSTAL = registerBlock("glacier_crystal", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));

    public static final RegistryObject<Block> FROSTWOOD_FENCE_GATE = registerBlock("frostwood_fence_gate", ()-> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE), SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));

    public static final RegistryObject<Block> PACKED_ICE_BRICKS = registerBlock("packed_ice_bricks", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> PACKED_SNOW = registerBlock("packed_snow", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK)));
    public static final RegistryObject<Block> PACKED_SNOW_BRICKS = registerBlock("packed_snow_bricks", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK)));

    public static final RegistryObject<Block> POLISHED_FRIGITIDE = registerBlock("polished_frigidite", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> POLISHED_BLUE_ICE = registerBlock("polished_blue_ice", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.BLUE_ICE)));
    public static final RegistryObject<Block> POLISHED_KORISTONE = registerBlock("polished_koristone", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> POLISHED_PACKED_ICE = registerBlock("polished_packed_ice", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE)));
    public static final RegistryObject<Block> POLISHED_GLACIER_BLOCK = registerBlock("polished_glacier_block", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));

    public static final RegistryObject<Block> SHREDDED_KORISTONE = registerBlock("shredded_koristone", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> SNOW_BRICKS = registerBlock("snow_bricks", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK)));

    public static final RegistryObject<Block> CHISELED_HOWLITE = registerBlock("chiseled_howlite", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> CHISELED_HOWLITE_BRICKS = registerBlock("chiseled_howlite_bricks", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> CHISELED_HOWLITE_TILES = registerBlock("chiseled_howlite_tiles", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> ICE_BRICKS = registerBlock("ice_bricks", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> CHISELED_POLISHED_HOWLITE = registerBlock("chiseled_polished_howlite", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE)));
    public static final RegistryObject<Block> HOWLITE = registerBlock("howlite", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> HOWLITE_BRICKS = registerBlock("howlite_bricks", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> HOWLITE_TILES = registerBlock("howlite_tiles", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> POLISHED_HOWLITE = registerBlock("polished_howlite", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> SNOWY_STONE_BRICKS = registerBlock("snowy_stone_bricks", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    public static final RegistryObject<Block> KORISTONE = registerBlock("koristone", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> KORISTONE_BRICKS = registerBlock("koristone_bricks", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    public static final RegistryObject<Block> BLUE_ICE_BRICKS = registerBlock("blue_ice_bricks", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> BLUE_ICE_BRICKS_STAIRS = registerBlock("blue_ice_bricks_stairs", 
    ()-> new StairBlock(()-> SnowBlockRegistry.FROSTED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block> BLUE_ICE_BRICKS_2 = registerBlock("blue_ice_bricks2", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> ICE_SPIKE_BLOCK = registerBlock("ice_spike_block", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    public static final RegistryObject<Block> SNOWY_GRASS = registerBlock("snowy_grass", ()-> new TallGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).mapColor(MapColor.PLANT)));
    public static final RegistryObject<Block> SNOWY_TALL_GRASS = registerBlock("snowy_tall_grass", ()-> new DoublePlantBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS).mapColor(MapColor.PLANT)));

    public static final RegistryObject<Block> FROSTWOOD_DOOR = registerBlock("frostwood_door", ()-> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), BlockSetType.OAK));
    public static final RegistryObject<Block> FROSTWOOD_TRAPDOOR = registerBlock("frostwood_trapdoor", ()-> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistryObject<Block> FROSTED_LEAVES_SPIKES = registerBlock("frosted_leaves_spikes", ()-> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)){
        @Override
        public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
            Direction direction = pState.getValue(FACING);
            BlockPos blockpos = pPos.relative(direction.getOpposite());
            return pLevel.getBlockState(blockpos).is(SnowBlockRegistry.FROSTED_LEAVES.get()) ? true : pLevel.getBlockState(blockpos).isFaceSturdy(pLevel, blockpos, direction);
        }
    });
    public static final RegistryObject<Block> FROSTWOOD_SAPLING = registerBlock("frostwood_sapling", ()-> new SaplingBlock(new FrostwoodTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    // Slabs
    public static final RegistryObject<Block> BLUE_ICE_BRICKS_SLAB = registerBlock("blue_ice_bricks_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB)));
    public static final RegistryObject<Block> BLUE_ICE_BRICKS_2_SLAB = registerBlock("blue_ice_bricks2_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.ICE)));

    public static final RegistryObject<Block> COBBLED_FRIGIDITE_SLAB = registerBlock("cobbled_frigidite_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_SLAB)));

    public static final RegistryObject<Block> FRIGIDITE_SLAB = registerBlock("frigidite_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_SLAB)));
    public static final RegistryObject<Block> FRIGIDITE_BRICKS_SLAB = registerBlock("frigidite_bricks_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB)));
    public static final RegistryObject<Block> FROSTED_FRIGIDITE_SLAB = registerBlock("frosted_frigidite_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_SLAB)));

    public static final RegistryObject<Block> ICE_BRICKS_SLAB = registerBlock("ice_bricks_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB)));

    public static final RegistryObject<Block> HOWLITE_SLAB = registerBlock("howlite_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB)));
    public static final RegistryObject<Block> HOWLITE_BRICKS_SLAB = registerBlock("howlite_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistryObject<Block> HOWLITE_TILES_SLAB = registerBlock("howlite_tiles_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB)));

    public static final RegistryObject<Block> KORISTONE_SLAB = registerBlock("koristone_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB)));
    public static final RegistryObject<Block> KORISTONE_BRICKS_SLAB = registerBlock("koristone_bricks_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB)));

    public static final RegistryObject<Block> PACKED_ICE_BRICKS_SLAB = registerBlock("packed_ice_bricks_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB)));
    public static final RegistryObject<Block> PACKED_SNOW_SLAB = registerBlock("packed_snow_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK)));
    public static final RegistryObject<Block> PACKED_SNOW_BRICKS_SLAB = registerBlock("packed_snow_bricks_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK)));

    public static final RegistryObject<Block> POLISHED_HOWLITE_SLAB = registerBlock("polished_howlite_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB)));
    
    public static final RegistryObject<Block> SNOWY_COBBLESTONE_SLAB = registerBlock("snowy_cobblestone_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_SLAB)));
    public static final RegistryObject<Block> SNOWY_STONE_BRICKS_SLAB = registerBlock("snowy_stone_bricks_slab", ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB)));

    // Stairs
    public static final RegistryObject<Block> BLUE_ICE_BRICKS_2_STAIRS = registerBlock("blue_ice_bricks_2_staris", 
    ()-> new StairBlock(() -> SnowBlockRegistry.BLUE_ICE_BRICKS_2.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_STAIRS)));
    public static final RegistryObject<Block> COBBLED_FRIGIDITE_STAIRS = registerBlock("cobbled_frigidite_stairs", 
    ()-> new StairBlock(() -> SnowBlockRegistry.COBBLED_FRIGIDITE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_STAIRS)));
    public static final RegistryObject<Block> HOWLITE_STAIRS = registerBlock("howlite_stairs", 
    () -> new StairBlock(() -> SnowBlockRegistry.HOWLITE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS)));
    public static final RegistryObject<Block> HOWLITE_BRICKS_STAIRS = registerBlock("howlite_bricks_stairs", 
    () -> new StairBlock(() -> SnowBlockRegistry.HOWLITE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block> HOWLITE_TILES_STAIRS = registerBlock("howlite_tiles_stairs", 
    () -> new StairBlock(() -> SnowBlockRegistry.HOWLITE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS)));
    public static final RegistryObject<Block> ICE_BRICKS_STAIRS = registerBlock("ice_bricks_stairs", 
    ()-> new StairBlock(() -> SnowBlockRegistry.ICE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block> KORISTONE_STAIRS = registerBlock("koristone_stairs", 
    ()-> new StairBlock(() -> SnowBlockRegistry.KORISTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block> KORISTONE_BRICKS_STAIRS = registerBlock("koristone_bricks_stairs", 
    ()-> new StairBlock(() -> SnowBlockRegistry.KORISTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block> PACKED_ICE_BRICKS_STAIRS = registerBlock("packed_ice_bricks_stairs", 
    ()-> new StairBlock(() -> SnowBlockRegistry.PACKED_ICE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block> PACKED_SNOW_STAIRS = registerBlock("packed_snow_stairs", 
    ()-> new StairBlock(() -> SnowBlockRegistry.PACKED_SNOW.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK)));
    public static final RegistryObject<Block> PACKED_SNOW_BRICKS_STAIRS = registerBlock("packed_snow_bricks_stairs", 
    ()-> new StairBlock(() -> SnowBlockRegistry.PACKED_SNOW_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK)));
    public static final RegistryObject<Block> POLISHED_HOWLITE_STAIRS = registerBlock("polished_howlite_stairs", 
    () -> new StairBlock(() -> SnowBlockRegistry.POLISHED_HOWLITE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS)));
    public static final RegistryObject<Block> SNOWY_COBBLESTONE_STAIRS = registerBlock("snowy_cobblestone_stairs", 
    ()-> new StairBlock(() -> SnowBlockRegistry.SNOWY_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_STAIRS)));
    public static final RegistryObject<Block> SNOWY_STONE_BRICKS_STAIRS = registerBlock("snowy_stone_bricks_stairs", 
    ()-> new StairBlock(() -> SnowBlockRegistry.SNOWY_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block> FRIGIDITE_STAIRS = registerBlock("frigidite_stairs", 
    ()-> new StairBlock(()-> SnowBlockRegistry.FRIGIDITE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block> FROSTED_FRIGIDITE_STAIRS = registerBlock("frosted_frigidite_stairs", 
    ()-> new StairBlock(()-> SnowBlockRegistry.FROSTED_FRIGIDITE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistryObject<Block> FRIGIDITE_BRICKS_STAIRS = registerBlock("frigidite_bricks_stairs", 
    ()-> new StairBlock(()-> SnowBlockRegistry.FROSTED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));

    // Walls
    public static final RegistryObject<Block> BLUE_ICE_BRICKS_WALL = registerBlock("blue_ice_bricks_wall", 
    ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_WALL)));
    public static final RegistryObject<Block> BLUE_ICE_BRICKS_2_WALL = registerBlock("blue_ice_bricks2_wall", 
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.ICE)));
    public static final RegistryObject<Block> COBBLED_FRIGIDITE_WALL = registerBlock("cobbled_frigidite_wall", 
    ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_WALL)));
    public static final RegistryObject<Block> FRIGIDITE_WALL = registerBlock("frigidite_wall", 
    ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_WALL)));
    public static final RegistryObject<Block> FRIGIDITE_BRICKS_WALL = registerBlock("frigidite_bricks_wall", 
    ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block> FROSTED_FRIGIDITE_WALL = registerBlock("frosted_frigidite_wall", 
    ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_WALL)));

    public static final RegistryObject<Block> HOWLITE_WALL = registerBlock("howlite_wall", 
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block> HOWLITE_BRICKS_WALL = registerBlock("howlite_bricks_wall", 
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block> HOWLITE_TILES_WALL = registerBlock("howlite_tiles_wall", 
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block> POLISHED_HOWLITE_WALL = registerBlock("polished_howlite_wall", 
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));

    public static final RegistryObject<Block> ICE_BRICKS_WALL = registerBlock("ice_bricks_wall", 
    ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));

    public static final RegistryObject<Block> KORISTONE_WALL = registerBlock("koristone_wall", 
    ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));
    public static final RegistryObject<Block> KORISTONE_BRICKS_WALL = registerBlock("koristone_bricks_wall", 
    ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));

    public static final RegistryObject<Block> PACKED_ICE_BRICKS_WALL = registerBlock("packed_ice_bricks_wall", 
    ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));

    public static final RegistryObject<Block> PACKED_SNOW_WALL = registerBlock("packed_snow_wall", 
    ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK)));
    public static final RegistryObject<Block> PACKED_SNOW_BRICKS_WALL = registerBlock("packed_snow_bricks_wall", 
    ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK)));
    public static final RegistryObject<Block> SNOWY_COBBLESTONE_WALL = registerBlock("snowy_cobblestone_wall", 
    ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_WALL)));
    public static final RegistryObject<Block> SNOWY_STONE_BRICKS_WALL = registerBlock("snowy_stone_bricks_wall", 
    ()-> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));

    public static final RegistryObject<Block> BUDDING_ICE_CRYSTAL = registerBlock("budding_ice_crystal", 
    ()-> new BuddingIceCrystal(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST)));
    public static final RegistryObject<Block> SMALL_ICE_CRYSTAL_BUD = registerBlock("small_ice_crystal_bud", 
    ()-> new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.copy(Blocks.SMALL_AMETHYST_BUD)));
    public static final RegistryObject<Block> MEDIUM_ICE_CRYSTAL_BUD = registerBlock("medium_ice_crystal_bud", 
    ()-> new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.copy(Blocks.MEDIUM_AMETHYST_BUD)));
    public static final RegistryObject<Block> LARGE_ICE_CRYSTAL_BUD = registerBlock("large_ice_crystal_bud", 
    ()-> new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD)));
    public static final RegistryObject<Block> ICE_CRYSTAL_CLUSTER = registerBlock("ice_crystal_cluster", 
    ()-> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));


    private static <T extends Block> RegistryObject<T> registerBlock(String pName, Supplier<T> pSupplier){
        RegistryObject<T> toReturn = BLOCKS.register(pName, pSupplier);
        registerBlock(pName, toReturn);
        return toReturn;
    }
    

    private static <T extends Block> RegistryObject<Item> registerBlock(String pName, RegistryObject<T> pBlock){
        return SnowItemsRegistry.ITEMS.register(pName, ()-> new BlockItem(pBlock.get(), new Item.Properties()));
    }

    public static void register(IEventBus pEventBus){
        BLOCKS.register(pEventBus);
    }

    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
      return false;
    }
}
