package net.technic.snow_update.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class IcebudBottomBlock extends Block {

    public static final IntegerProperty LIGHT = IntegerProperty.create("light", 0, 15);

    public IcebudBottomBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIGHT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIGHT);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.above()).getBlock() instanceof IcebudTopBlock;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);

        if (level.isClientSide) return;

        if (!canSurvive(state, level, pos)) {
            level.destroyBlock(pos, false);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, level, pos, newState, isMoving);

        if (level.isClientSide) return;

        if (state.getBlock() != newState.getBlock()) {
            BlockState above = level.getBlockState(pos.above());
            if (above.getBlock() instanceof IcebudTopBlock) {
                level.destroyBlock(pos.above(), true);
            }
        }
    }
}
