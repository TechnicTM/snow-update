package net.technic.snow_update.block;

import com.google.common.base.Predicates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.technic.snow_update.entity.TitanYetiEntity;
import net.technic.snow_update.init.SnowBlockRegistry;
import net.technic.snow_update.init.SnowItemsRegistry;
import net.technic.snow_update.init.SnowSoundsRegistry;
import java.util.List;

public class KeyStone extends HorizontalDirectionalBlock{
    public static final BooleanProperty HAS_KEY = BooleanProperty.create("key");
    private static BlockPattern icechunkShape;

    public KeyStone(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HAS_KEY, false));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HAS_KEY);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
        
    }
    
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack pItem = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide()){
            if (pItem.getItem() == SnowItemsRegistry.GLACIER_SHARD.get() && pState.getValue(HAS_KEY) == false){
                pLevel.setBlock(pPos, pState.setValue(HAS_KEY, true), 3);
                pItem.setCount(pItem.getCount()-1);
                pLevel.playSound(null, pPos, SnowSoundsRegistry.KEYSTONE_SHARD_PLACE.get(), SoundSource.AMBIENT, 16F, 1F);
                BlockPattern.BlockPatternMatch blockPatternMatch = getOrCreateIceChunkShape().find(pLevel, pPos);
                if (blockPatternMatch != null) {
                    int[] offset = findOffset(blockPatternMatch);
                    
                    BlockPos blockPos = blockPatternMatch.getFrontTopLeft().offset(offset[1], 6, offset[0]);
                    System.out.println(blockPos+" "+blockPatternMatch.getUp());
                    BlockPos blockPos2 = blockPos.offset(7,-5,7);
                    List<TitanYetiEntity> list = pLevel.getEntitiesOfClass(TitanYetiEntity.class, new AABB(blockPos, blockPos2));
                    for (TitanYetiEntity t : list) {
                        t.setShouldUnfreeze(true);
                    }
                    for (int x = blockPos.getX(); x <= blockPos2.getX(); x++) {
                        for (int y = blockPos.getY(); y >= blockPos2.getY(); y--) {
                            for (int z = blockPos.getZ(); z <= blockPos2.getZ(); z++) {
                                BlockPos blockPos3 = new BlockPos(x, y, z);
                                BlockState blockState = pLevel.getBlockState(blockPos3);
                                if (blockState.getBlock() instanceof GlacierIce) {
                                    pLevel.setBlock(blockPos3, blockState.setValue(GlacierIce.ACTIVATED, true), 3);
                                }
                            }
                        }
                    }
                    
                    
                }
            } else if (pState.getValue(HAS_KEY) == true){
                pLevel.setBlock(pPos, pState.setValue(HAS_KEY, false), 3);
                pLevel.playSound(null, pPos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.AMBIENT, 16F, 1F);
                ItemStack out = new ItemStack(SnowItemsRegistry.GLACIER_SHARD.get());
                if (!pPlayer.addItem(out))
                    pPlayer.drop(pItem, false);
                    
            } 
        }

        return InteractionResult.SUCCESS;
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(HAS_KEY, Boolean.valueOf(false));
    }

    
    public static BlockPattern getOrCreateIceChunkShape() {
        if (icechunkShape == null) {
            icechunkShape = BlockPatternBuilder.start().aisle("?????@?????", "???????????", "???????????", "???????????", "???????????", "@?????????@", "???????????", "???????????", "???????????", "???????????", "?????@?????")
            .where('?', BlockInWorld.hasState(BlockStatePredicate.ANY)).where('@', BlockInWorld.hasState(BlockStatePredicate.forBlock(SnowBlockRegistry.KEY_STONE.get()).where(HAS_KEY, Predicates.equalTo(true))))
            .where('^', BlockInWorld.hasState(BlockStatePredicate.forBlock(SnowBlockRegistry.GLACIER_ICE.get()))).build();
            
        }

        return icechunkShape;
    }

    private int[] findOffset(BlockPattern.BlockPatternMatch pBlockPatternMatch) {
        int[] offset = new int[2];
        Direction direction = pBlockPatternMatch.getUp();
        if (direction.equals(Direction.SOUTH)) {
            offset[0] = -8;
            offset[1] = -8;
        } else if (direction.equals(Direction.NORTH)) {
            offset[0] = 4;
            offset[1] = 4;
        } else if (direction.equals(Direction.WEST)) {
            offset[0] = -8;
            offset[1] = 2;
        } else {
            offset[0] = 2;
            offset[1] = -8;
        }
        return offset;
    }

}
