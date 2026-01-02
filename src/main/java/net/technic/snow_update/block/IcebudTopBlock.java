package net.technic.snow_update.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import net.minecraft.world.ticks.TickPriority;
import net.technic.snow_update.init.SnowBlockRegistry;
import net.technic.snow_update.init.SnowTags;

public class IcebudTopBlock extends Block {

    public static final IntegerProperty LIGHT = IntegerProperty.create("light", 0, 15);

    private static final int UPDATE_INTERVAL_TICKS = 10;
    private static final int HELD_ITEM_RANGE = 12;
    private static final int BLOCK_SCAN_RADIUS = 8;

    public IcebudTopBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIGHT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIGHT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();

        // Enforce: you must click the underside of a block
        if (ctx.getClickedFace() != Direction.DOWN) {
            return null;
        }

        // Need room for the bottom part
        if (!level.getBlockState(pos.below()).canBeReplaced(ctx)) {
            return null;
        }

        // Must have a sturdy ceiling directly above the top part
        BlockPos ceilingPos = pos.above();
        BlockState ceiling = level.getBlockState(ceilingPos);
        if (!ceiling.isFaceSturdy(level, ceilingPos, Direction.DOWN)) {
            return null;
        }

        return this.defaultBlockState().setValue(LIGHT, 0);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (level.isClientSide) return;

        // Place bottom block under it
        BlockState bottom = SnowBlockRegistry.ICEBUD_BOTTOM.get()
                .defaultBlockState()
                .setValue(IcebudBottomBlock.LIGHT, state.getValue(LIGHT));

        level.setBlock(pos.below(), bottom, 3);

        // Start ticking on the top anchor
        level.scheduleTick(pos, this, 1, TickPriority.NORMAL);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
        if (level.isClientSide) return;

        if (!canSurvive(state, level, pos)) {
            level.destroyBlock(pos.below(), false); // no drops for bottom
            level.destroyBlock(pos, true);         // drop the item once from top
            return;
        }

        // If bottom missing, restore it if possible
        BlockState below = level.getBlockState(pos.below());
        if (!(below.getBlock() instanceof IcebudBottomBlock) && below.canBeReplaced()) {
            BlockState bottom = SnowBlockRegistry.ICEBUD_BOTTOM.get()
                    .defaultBlockState()
                    .setValue(IcebudBottomBlock.LIGHT, state.getValue(LIGHT));
            level.setBlock(pos.below(), bottom, 3);
        }

        level.scheduleTick(pos, this, 1, TickPriority.NORMAL);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        // Ceiling must exist above
        BlockPos ceilingPos = pos.above();
        BlockState ceiling = level.getBlockState(ceilingPos);
        if (!ceiling.isFaceSturdy(level, ceilingPos, Direction.DOWN)) return false;

        // Bottom must exist below
        BlockState below = level.getBlockState(pos.below());
        return below.getBlock() instanceof IcebudBottomBlock;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canSurvive(state, level, pos)) {
            level.destroyBlock(pos.below(), false);
            level.destroyBlock(pos, true);
            return;
        }

        int heldGlow = computeHeldItemGlow(level, pos);
        int blockGlow = computeNearbyBlockGlow(level, pos);

        int newLight = Mth.clamp(Math.max(heldGlow, blockGlow), 0, 15);

        if (state.getValue(LIGHT) != newLight) {
            level.setBlock(pos, state.setValue(LIGHT, newLight), 3);

            BlockState bottom = level.getBlockState(pos.below());
            if (bottom.getBlock() instanceof IcebudBottomBlock && bottom.getValue(IcebudBottomBlock.LIGHT) != newLight) {
                level.setBlock(pos.below(), bottom.setValue(IcebudBottomBlock.LIGHT, newLight), 3);
            }
        }

        level.scheduleTick(pos, this, UPDATE_INTERVAL_TICKS, TickPriority.NORMAL);
    }

    private static int computeHeldItemGlow(ServerLevel level, BlockPos flowerTopPos) {
        double range = HELD_ITEM_RANGE + 0.5;
        Player player = level.getNearestPlayer(flowerTopPos.getX() + 0.5, flowerTopPos.getY() + 0.5, flowerTopPos.getZ() + 0.5, range, false);
        if (player == null) return 0;

        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();

        boolean holding =
                (!main.isEmpty() && main.is(SnowTags.Items.GLACIER_ITEMS)) ||
                        (!off.isEmpty() && off.is(SnowTags.Items.GLACIER_ITEMS));

        if (!holding) return 0;

        double dx = player.getX() - (flowerTopPos.getX() + 0.5);
        double dy = player.getY() - (flowerTopPos.getY() + 0.5);
        double dz = player.getZ() - (flowerTopPos.getZ() + 0.5);
        double distSq = dx * dx + dy * dy + dz * dz;

        double maxSq = (double) HELD_ITEM_RANGE * (double) HELD_ITEM_RANGE;
        if (distSq >= maxSq) return 0;

        double t = 1.0 - (distSq / maxSq);
        return (int) Math.round(15.0 * t);
    }

    private static int computeNearbyBlockGlow(ServerLevel level, BlockPos flowerTopPos) {
        int r = BLOCK_SCAN_RADIUS;
        double bestDistSq = Double.MAX_VALUE;

        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -r; dy <= r; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    cursor.set(flowerTopPos.getX() + dx, flowerTopPos.getY() + dy, flowerTopPos.getZ() + dz);

                    BlockState s = level.getBlockState(cursor);
                    if (!s.is(SnowTags.Blocks.GLACIER_BLOCKS)) continue;

                    double distSq = (double) dx * dx + (double) dy * dy + (double) dz * dz;
                    if (distSq < bestDistSq) {
                        bestDistSq = distSq;
                        if (bestDistSq <= 1.0) return 15;
                    }
                }
            }
        }

        if (bestDistSq == Double.MAX_VALUE) return 0;

        double maxSq = (double) r * (double) r;
        if (bestDistSq >= maxSq) return 0;

        double t = 1.0 - (bestDistSq / maxSq);
        return (int) Math.round(15.0 * t);
    }
}
