package net.technic.snow_update.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import net.minecraft.world.ticks.TickPriority;
import net.technic.snow_update.init.SnowTags;

public class IcebudBlock extends DoublePlantBlock {

    public static final IntegerProperty LIGHT = IntegerProperty.create("light", 0, 15);

    // Tuning knobs
    private static final int UPDATE_INTERVAL_TICKS = 10; // 0.5s
    private static final int HELD_ITEM_RANGE = 12;       // blocks
    private static final int BLOCK_SCAN_RADIUS = 8;      // blocks

    public IcebudBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(LIGHT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, LIGHT);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);

        if (!level.isClientSide) {
            level.scheduleTick(pos, this, 1, TickPriority.NORMAL);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);

        if (!level.isClientSide) {
            level.scheduleTick(pos, this, 1, TickPriority.NORMAL);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, net.minecraft.util.RandomSource random) {
        BlockPos base = (state.getValue(HALF) == DoubleBlockHalf.UPPER) ? pos.below() : pos;

        int heldGlow = computeHeldItemGlow(level, base);
        int blockGlow = computeNearbyBlockGlow(level, base);

        int newLight = Math.max(heldGlow, blockGlow);
        newLight = Mth.clamp(newLight, 0, 15);

        setLightBothHalves(level, base, newLight);

        // Re-schedule
        level.scheduleTick(base, this, UPDATE_INTERVAL_TICKS, TickPriority.NORMAL);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return super.canSurvive(state, level, pos);
        }
        BlockState below = level.getBlockState(pos.below());
        return below.getBlock() == this;
    }

    private static void setLightBothHalves(ServerLevel level, BlockPos base, int light) {
        BlockState lower = level.getBlockState(base);
        BlockState upper = level.getBlockState(base.above());

        if (lower.getBlock() instanceof IcebudBlock && lower.getValue(HALF) == DoubleBlockHalf.LOWER) {
            if (lower.getValue(LIGHT) != light) {
                level.setBlock(base, lower.setValue(LIGHT, light), 3);
            }
        }

        if (upper.getBlock() instanceof IcebudBlock && upper.getValue(HALF) == DoubleBlockHalf.UPPER) {
            if (upper.getValue(LIGHT) != light) {
                level.setBlock(base.above(), upper.setValue(LIGHT, light), 3);
            }
        }
    }

    private static int computeHeldItemGlow(ServerLevel level, BlockPos base) {
        // Find nearest player in range
        double range = HELD_ITEM_RANGE + 0.5;
        Player player = level.getNearestPlayer(base.getX() + 0.5, base.getY() + 0.5, base.getZ() + 0.5, range, false);
        if (player == null) return 0;

        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();

        boolean holdingGlacier =
                (!main.isEmpty() && main.is(SnowTags.Items.GLACIER_ITEMS)) ||
                        (!off.isEmpty() && off.is(SnowTags.Items.GLACIER_ITEMS));

        if (!holdingGlacier) return 0;

        // Use squared distance (no sqrt)
        double dx = (player.getX() - (base.getX() + 0.5));
        double dy = (player.getY() - (base.getY() + 0.5));
        double dz = (player.getZ() - (base.getZ() + 0.5));
        double distSq = dx*dx + dy*dy + dz*dz;

        double maxSq = HELD_ITEM_RANGE * HELD_ITEM_RANGE;
        if (distSq >= maxSq) return 0;

        // Map to 0..15
        double t = 1.0 - (distSq / maxSq); // 1 near, 0 far
        return (int) Math.round(15.0 * t);
    }

    private static int computeNearbyBlockGlow(ServerLevel level, BlockPos base) {
        int r = BLOCK_SCAN_RADIUS;

        // Track the closest tagged block
        double bestDistSq = Double.MAX_VALUE;

        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -r; dy <= r; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    cursor.set(base.getX() + dx, base.getY() + dy, base.getZ() + dz);

                    BlockState s = level.getBlockState(cursor);
                    if (!s.is(SnowTags.Blocks.GLACIER_BLOCKS)) continue;

                    double distSq = dx*dx + dy*dy + dz*dz;
                    if (distSq < bestDistSq) {
                        bestDistSq = distSq;
                        if (bestDistSq <= 1) {
                            // early exit if extremely close
                            return 15;
                        }
                    }
                }
            }
        }

        if (bestDistSq == Double.MAX_VALUE) return 0;

        double maxSq = r * r;
        if (bestDistSq >= maxSq) return 0;

        double t = 1.0 - (bestDistSq / maxSq);
        return (int) Math.round(15.0 * t);
    }
}
