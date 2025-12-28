package net.technic.snow_update.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.technic.snow_update.init.SnowBlockEntitiesRegistry;
import net.technic.snow_update.init.SnowBlockRegistry;

public class GlacierIceBlockEntity extends BlockEntity{
    private int ticks = 0;

    public GlacierIceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SnowBlockEntitiesRegistry.GLACIER_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("ticks", this.ticks);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.ticks = pTag.getInt("ticks");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pState.getValue(GlacierIce.ACTIVATED)) {
            if (this.ticks % 20 == 0) {
                int i = pState.getValue(GlacierIce.AGE);
            if (i < 3){
                    pLevel.setBlock(pPos, pState.setValue(GlacierIce.AGE, i+1), 3);
                } else {
                    pLevel.destroyBlock(pPos, false);
                    BlockPos pPos2 = pPos.offset(1, 0, 0);
                    BlockState blockState = pLevel.getBlockState(pPos2);
                    if (blockState.getBlock() == SnowBlockRegistry.FROSTED_WOOD_FENCE.get() || blockState.getBlock() == SnowBlockRegistry.STRIPPED_FROSTED_LOG.get())
                        pLevel.destroyBlock(pPos2, false);
                    pPos2 = pPos.offset(0, 0, 1);
                    if (blockState.getBlock() == SnowBlockRegistry.FROSTED_WOOD_FENCE.get() || blockState.getBlock() == SnowBlockRegistry.STRIPPED_FROSTED_LOG.get())
                        pLevel.destroyBlock(pPos2, false);
                    pPos2 = pPos.offset(-1, 0, 0);
                    if (blockState.getBlock() == SnowBlockRegistry.FROSTED_WOOD_FENCE.get() || blockState.getBlock() == SnowBlockRegistry.STRIPPED_FROSTED_LOG.get())
                        pLevel.destroyBlock(pPos2, false);
                    pPos2 = pPos.offset(0, 0, -1);
                    if (blockState.getBlock() == SnowBlockRegistry.FROSTED_WOOD_FENCE.get() || blockState.getBlock() == SnowBlockRegistry.STRIPPED_FROSTED_LOG.get())
                        pLevel.destroyBlock(pPos2, false);
                }
            }
            if (pLevel.random.nextInt(5) == 2)
                this.ticks ++;
        }
    }

}
