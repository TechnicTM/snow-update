package net.technic.snow_update.block;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.technic.snow_update.block.properties.IceStalactiteThickness;
import net.technic.snow_update.block.properties.SnowUpdateBlockProperties;
import net.technic.snow_update.init.SnowBlockRegistry;

public class PointedIceStalactite extends Block implements Fallable, SimpleWaterloggedBlock {
    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final EnumProperty<IceStalactiteThickness> THICKNESS = SnowUpdateBlockProperties.ICE_STALACTITE_THICKNESS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape TIP_MERGE_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private static final VoxelShape TIP_SHAPE_UP = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);
    private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    private static final VoxelShape BASE_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public PointedIceStalactite(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
        .setValue(TIP_DIRECTION, Direction.UP)
        .setValue(THICKNESS, IceStalactiteThickness.TIP)
        .setValue(WATERLOGGED, Boolean.valueOf(false)));
        
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return isValidPointedDripstonePlacement(pLevel, pPos, pState.getValue(TIP_DIRECTION));
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
                    pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
           
        if (pDirection != Direction.UP && pDirection != Direction.DOWN) {
                    return pState;
        } else {
            Direction direction = pState.getValue(TIP_DIRECTION);
            if (direction == Direction.DOWN && pLevel.getBlockTicks().hasScheduledTick(pPos, this)) {
                return pState;
            } else if (pDirection == direction.getOpposite() && !this.canSurvive(pState, pLevel, pPos)) {
                if (direction == Direction.DOWN) {
                    pLevel.scheduleTick(pPos, this, 2);
                } else {
                    pLevel.scheduleTick(pPos, this, 1);
                }
           
                return pState;
            } else {
                boolean flag = pState.getValue(THICKNESS) == IceStalactiteThickness.TIP_MERGE;
                IceStalactiteThickness IceSpikeThinkness = calculateIceSpikeThinkness(pLevel, pPos, direction, flag);
                return pState.setValue(THICKNESS, IceSpikeThinkness);
            }
        }
    }

    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile) {
        BlockPos pPos = pHit.getBlockPos();
        if (!pLevel.isClientSide() && pProjectile.mayInteract(pLevel, pPos) && pProjectile instanceof AbstractArrow && pProjectile.getDeltaMovement().length() > 0.6D
        || pProjectile instanceof ThrownTrident && pProjectile.getDeltaMovement().length() > 0.6D || pProjectile instanceof Snowball && pProjectile.getDeltaMovement().length() > 0.6D){
            pLevel.destroyBlock(pPos, true);
        }
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float f) {
      if (pState.getValue(TIP_DIRECTION) == Direction.UP && pState.getValue(THICKNESS) == IceStalactiteThickness.TIP) {
         pEntity.causeFallDamage(f + 2.0F, 2.0F, pLevel.damageSources().stalagmite());
      } else {
         super.fallOn(pLevel, pState, pPos, pEntity, f);
      }
    }

   public void tick(BlockState pState, ServerLevel pServer, BlockPos pBlockPos, RandomSource pRandomSource) {
      if (isStalagmite(pState) && !this.canSurvive(pState, pServer, pBlockPos)) {
         pServer.destroyBlock(pBlockPos, true);
      } else {
         spawnFallingStalactite(pState, pServer, pBlockPos);
      }
   }

   public void randomTick(BlockState pState, ServerLevel pServerLevel, BlockPos pPos, RandomSource pSource) {
      if (pSource.nextFloat() < 0.011377778F && isStalactiteStartPos(pState, pServerLevel, pPos)) {
         growStalactiteOrStalagmiteIfPossible(pState, pServerLevel, pPos, pSource);
      }
      spawnDripParticle(pServerLevel, pPos, pState);

   }
   

   public PushReaction getPistonPushReaction(BlockState pState) {
      return PushReaction.DESTROY;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext p_154040_) {
      LevelAccessor levelaccessor = p_154040_.getLevel();
      BlockPos blockpos = p_154040_.getClickedPos();
      Direction direction = p_154040_.getNearestLookingVerticalDirection().getOpposite();
      Direction direction1 = calculateTipDirection(levelaccessor, blockpos, direction);
      if (direction1 == null) {
         return null;
      } else {
         boolean flag = !p_154040_.isSecondaryUseActive();
         IceStalactiteThickness IceSpikeThinkness = calculateIceSpikeThinkness(levelaccessor, blockpos, direction1, flag);
         return IceSpikeThinkness == null ? null : this.defaultBlockState().setValue(TIP_DIRECTION, direction1).setValue(THICKNESS, IceSpikeThinkness).setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER));
      }
   }

   public FluidState getFluidState(BlockState p_154235_) {
      return p_154235_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_154235_);
   }

   public VoxelShape getOcclusionShape(BlockState p_154170_, BlockGetter p_154171_, BlockPos p_154172_) {
      return Shapes.empty();
   }

   public VoxelShape getShape(BlockState p_154117_, BlockGetter p_154118_, BlockPos p_154119_, CollisionContext p_154120_) {
      IceStalactiteThickness IceStalactiteThickness = p_154117_.getValue(THICKNESS);
      VoxelShape voxelshape;
      if (IceStalactiteThickness == IceStalactiteThickness.TIP_MERGE) {
         voxelshape = TIP_MERGE_SHAPE;
      } else if (IceStalactiteThickness == IceStalactiteThickness.TIP) {
         if (p_154117_.getValue(TIP_DIRECTION) == Direction.DOWN) {
            voxelshape = TIP_SHAPE_DOWN;
         } else {
            voxelshape = TIP_SHAPE_UP;
         }
      } else if (IceStalactiteThickness == IceStalactiteThickness.FRUSTUM) {
         voxelshape = FRUSTUM_SHAPE;
      } else if (IceStalactiteThickness == IceStalactiteThickness.MIDDLE) {
         voxelshape = MIDDLE_SHAPE;
      } else {
         voxelshape = BASE_SHAPE;
      }

      Vec3 vec3 = p_154117_.getOffset(p_154118_, p_154119_);
      return voxelshape.move(vec3.x, 0.0D, vec3.z);
   }

   public boolean isCollisionShapeFullBlock(BlockState p_181235_, BlockGetter p_181236_, BlockPos p_181237_) {
      return false;
   }

   public float getMaxHorizontalOffset() {
      return 0.125F;
   }

   public void onBrokenAfterFall(Level p_154059_, BlockPos p_154060_, FallingBlockEntity p_154061_) {
      if (!p_154061_.isSilent()) {
         p_154059_.levelEvent(1045, p_154060_, 0);
      }

   }

   public Predicate<Entity> getHurtsEntitySelector() {
      return EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
   }

   private static void spawnFallingStalactite(BlockState p_154098_, ServerLevel p_154099_, BlockPos p_154100_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_154100_.mutable();

      for(BlockState blockstate = p_154098_; isStalactite(blockstate); blockstate = p_154099_.getBlockState(blockpos$mutableblockpos)) {
         FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(p_154099_, blockpos$mutableblockpos, blockstate);
         if (isTip(blockstate, true)) {
            int i = Math.max(1 + p_154100_.getY() - blockpos$mutableblockpos.getY(), 6);
            float f = 1.0F * (float)i;
            fallingblockentity.setHurtsEntities(f, 40);
            break;
         }

         blockpos$mutableblockpos.move(Direction.DOWN);
      }

   }

   @VisibleForTesting
   public static void growStalactiteOrStalagmiteIfPossible(BlockState p_221888_, ServerLevel p_221889_, BlockPos p_221890_, RandomSource p_221891_) {
      BlockState blockstate = p_221889_.getBlockState(p_221890_.above(1));
      BlockState blockstate1 = p_221889_.getBlockState(p_221890_.above(2));
      if (canGrow(blockstate, blockstate1)) {
         BlockPos blockpos = findTip(p_221888_, p_221889_, p_221890_, 7, false);
         if (blockpos != null) {
            BlockState blockstate2 = p_221889_.getBlockState(blockpos);
            if (canDrip(blockstate2) && canTipGrow(blockstate2, p_221889_, blockpos)) {
               if (p_221891_.nextBoolean()) {
                  grow(p_221889_, blockpos, Direction.DOWN);
               } else {
                  growStalagmiteBelow(p_221889_, blockpos);
               }

            }
         }
      }
   }

   private static void growStalagmiteBelow(ServerLevel p_154033_, BlockPos p_154034_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_154034_.mutable();

      for(int i = 0; i < 10; ++i) {
         blockpos$mutableblockpos.move(Direction.DOWN);
         BlockState blockstate = p_154033_.getBlockState(blockpos$mutableblockpos);
         if (!blockstate.getFluidState().isEmpty()) {
            return;
         }

         if (isUnmergedTipWithDirection(blockstate, Direction.UP) && canTipGrow(blockstate, p_154033_, blockpos$mutableblockpos)) {
            grow(p_154033_, blockpos$mutableblockpos, Direction.UP);
            return;
         }

         if (isValidPointedDripstonePlacement(p_154033_, blockpos$mutableblockpos, Direction.UP) && !p_154033_.isWaterAt(blockpos$mutableblockpos.below())) {
            grow(p_154033_, blockpos$mutableblockpos.below(), Direction.UP);
            return;
         }

         if (!canDripThrough(p_154033_, blockpos$mutableblockpos, blockstate)) {
            return;
         }
      }

   }

   private static void grow(ServerLevel p_154036_, BlockPos p_154037_, Direction p_154038_) {
      BlockPos blockpos = p_154037_.relative(p_154038_);
      BlockState blockstate = p_154036_.getBlockState(blockpos);
      if (isUnmergedTipWithDirection(blockstate, p_154038_.getOpposite())) {
         createMergedTips(blockstate, p_154036_, blockpos);
      } else if (blockstate.isAir() || blockstate.is(Blocks.WATER)) {
         createIceSpike(p_154036_, blockpos, p_154038_, IceStalactiteThickness.TIP);
      }

   }

   private static void createIceSpike(LevelAccessor p_154088_, BlockPos p_154089_, Direction p_154090_, IceStalactiteThickness p_154091_) {
      BlockState blockstate = SnowBlockRegistry.POINTED_ICE_STALACTITE.get().defaultBlockState().setValue(TIP_DIRECTION, p_154090_).setValue(THICKNESS, p_154091_).setValue(WATERLOGGED, Boolean.valueOf(p_154088_.getFluidState(p_154089_).getType() == Fluids.WATER));
      p_154088_.setBlock(p_154089_, blockstate, 3);
   }

   private static void createMergedTips(BlockState p_154231_, LevelAccessor p_154232_, BlockPos p_154233_) {
      BlockPos blockpos;
      BlockPos blockpos1;
      if (p_154231_.getValue(TIP_DIRECTION) == Direction.UP) {
         blockpos1 = p_154233_;
         blockpos = p_154233_.above();
      } else {
         blockpos = p_154233_;
         blockpos1 = p_154233_.below();
      }

      createIceSpike(p_154232_, blockpos, Direction.DOWN, IceStalactiteThickness.TIP_MERGE);
      createIceSpike(p_154232_, blockpos1, Direction.UP, IceStalactiteThickness.TIP_MERGE);
   }

   private static void spawnDripParticle(Level p_154072_, BlockPos p_154073_, BlockState p_154074_) {
      Vec3 vec3 = p_154074_.getOffset(p_154072_, p_154073_);
      double d0 = 0.0625D;
      double d1 = (double)p_154073_.getX() + 0.5D + vec3.x;
      double d2 = (double)((float)(p_154073_.getY() + 1) - 0.6875F) - 0.0625D;
      double d3 = (double)p_154073_.getZ() + 0.5D + vec3.z;

      ParticleOptions particleoptions = ParticleTypes.DRIPPING_WATER;
      p_154072_.addParticle(particleoptions, d1, d2, d3, 0.0D, 0.0D, 0.0D);
   }

   @Nullable
   private static BlockPos findTip(BlockState p_154131_, LevelAccessor p_154132_, BlockPos p_154133_, int p_154134_, boolean p_154135_) {
      if (isTip(p_154131_, p_154135_)) {
         return p_154133_;
      } else {
         Direction direction = p_154131_.getValue(TIP_DIRECTION);
         BiPredicate<BlockPos, BlockState> bipredicate = (p_202023_, p_202024_) -> {
            return p_202024_.is(SnowBlockRegistry.POINTED_ICE_STALACTITE.get()) && p_202024_.getValue(TIP_DIRECTION) == direction;
         };
         return findBlockVertical(p_154132_, p_154133_, direction.getAxisDirection(), bipredicate, (p_154168_) -> {
            return isTip(p_154168_, p_154135_);
         }, p_154134_).orElse((BlockPos)null);
      }
   }

   @Nullable
   private static Direction calculateTipDirection(LevelReader p_154191_, BlockPos p_154192_, Direction p_154193_) {
      Direction direction;
      if (isValidPointedDripstonePlacement(p_154191_, p_154192_, p_154193_)) {
         direction = p_154193_;
      } else {
         if (!isValidPointedDripstonePlacement(p_154191_, p_154192_, p_154193_.getOpposite())) {
            return null;
         }

         direction = p_154193_.getOpposite();
      }

      return direction;
   }

   private static IceStalactiteThickness calculateIceSpikeThinkness(LevelReader p_154093_, BlockPos p_154094_, Direction p_154095_, boolean p_154096_) {
      Direction direction = p_154095_.getOpposite();
      BlockState blockstate = p_154093_.getBlockState(p_154094_.relative(p_154095_));
      if (isPointedDripstoneWithDirection(blockstate, direction)) {
         return !p_154096_ && blockstate.getValue(THICKNESS) != IceStalactiteThickness.TIP_MERGE ? IceStalactiteThickness.TIP : IceStalactiteThickness.TIP_MERGE;
      } else if (!isPointedDripstoneWithDirection(blockstate, p_154095_)) {
         return IceStalactiteThickness.TIP;
      } else {
         IceStalactiteThickness IceSpikeThinkness = blockstate.getValue(THICKNESS);
         if (IceSpikeThinkness != IceSpikeThinkness.TIP && IceSpikeThinkness != IceSpikeThinkness.TIP_MERGE) {
            BlockState blockstate1 = p_154093_.getBlockState(p_154094_.relative(direction));
            return !isPointedDripstoneWithDirection(blockstate1, p_154095_) ? IceSpikeThinkness.BASE : IceSpikeThinkness.MIDDLE;
         } else {
            return IceSpikeThinkness.FRUSTUM;
         }
      }
   }

   public static boolean canDrip(BlockState p_154239_) {
      return isStalactite(p_154239_) && p_154239_.getValue(THICKNESS) == IceStalactiteThickness.TIP && !p_154239_.getValue(WATERLOGGED);
   }

   private static boolean canTipGrow(BlockState p_154195_, ServerLevel p_154196_, BlockPos p_154197_) {
      Direction direction = p_154195_.getValue(TIP_DIRECTION);
      BlockPos blockpos = p_154197_.relative(direction);
      BlockState blockstate = p_154196_.getBlockState(blockpos);
      if (!blockstate.getFluidState().isEmpty()) {
         return false;
      } else {
         return blockstate.isAir() ? true : isUnmergedTipWithDirection(blockstate, direction.getOpposite());
      }
   }

   private static Optional<BlockPos> findRootBlock(Level p_154067_, BlockPos p_154068_, BlockState p_154069_, int p_154070_) {
      Direction direction = p_154069_.getValue(TIP_DIRECTION);
      BiPredicate<BlockPos, BlockState> bipredicate = (p_202015_, p_202016_) -> {
         return p_202016_.is(SnowBlockRegistry.POINTED_ICE_STALACTITE.get()) && p_202016_.getValue(TIP_DIRECTION) == direction;
      };
      return findBlockVertical(p_154067_, p_154068_, direction.getOpposite().getAxisDirection(), bipredicate, (p_154245_) -> {
         return !p_154245_.is(SnowBlockRegistry.POINTED_ICE_STALACTITE.get());
      }, p_154070_);
   }

   private static boolean isValidPointedDripstonePlacement(LevelReader p_154222_, BlockPos p_154223_, Direction p_154224_) {
      BlockPos blockpos = p_154223_.relative(p_154224_.getOpposite());
      BlockState blockstate = p_154222_.getBlockState(blockpos);
      return blockstate.isFaceSturdy(p_154222_, blockpos, p_154224_) || isPointedDripstoneWithDirection(blockstate, p_154224_);
   }

   private static boolean isTip(BlockState p_154154_, boolean p_154155_) {
      if (!p_154154_.is(SnowBlockRegistry.POINTED_ICE_STALACTITE.get())) {
         return false;
      } else {
         IceStalactiteThickness IceSpikeThinkness = p_154154_.getValue(THICKNESS);
         return IceSpikeThinkness == IceSpikeThinkness.TIP || p_154155_ && IceSpikeThinkness == IceSpikeThinkness.TIP_MERGE;
      }
   }

   private static boolean isUnmergedTipWithDirection(BlockState p_154144_, Direction p_154145_) {
      return isTip(p_154144_, false) && p_154144_.getValue(TIP_DIRECTION) == p_154145_;
   }

   private static boolean isStalactite(BlockState p_154241_) {
      return isPointedDripstoneWithDirection(p_154241_, Direction.DOWN);
   }

   private static boolean isStalagmite(BlockState p_154243_) {
      return isPointedDripstoneWithDirection(p_154243_, Direction.UP);
   }

   private static boolean isStalactiteStartPos(BlockState p_154204_, LevelReader p_154205_, BlockPos p_154206_) {
      return isStalactite(p_154204_) && !p_154205_.getBlockState(p_154206_.above()).is(SnowBlockRegistry.POINTED_ICE_STALACTITE.get());
   }

   public boolean isPathfindable(BlockState p_154112_, BlockGetter p_154113_, BlockPos p_154114_, PathComputationType p_154115_) {
      return false;
   }

   private static boolean isPointedDripstoneWithDirection(BlockState p_154208_, Direction p_154209_) {
      return p_154208_.is(SnowBlockRegistry.POINTED_ICE_STALACTITE.get()) && p_154208_.getValue(TIP_DIRECTION) == p_154209_;
   }
   
   @Nullable
   public static BlockPos findStalactiteTipAboveCauldron(Level p_154056_, BlockPos p_154057_) {
      BiPredicate<BlockPos, BlockState> bipredicate = (p_202030_, p_202031_) -> {
         return canDripThrough(p_154056_, p_202030_, p_202031_);
      };
      return findBlockVertical(p_154056_, p_154057_, Direction.UP.getAxisDirection(), bipredicate, PointedDripstoneBlock::canDrip, 11).orElse((BlockPos)null);
   }

   private static boolean canGrow(BlockState p_154141_, BlockState p_154142_) {
      return p_154141_.is(Blocks.DRIPSTONE_BLOCK) && p_154142_.is(Blocks.WATER) && p_154142_.getFluidState().isSource();
   }

   private static Fluid getDripFluid(Level p_154053_, Fluid p_154054_) {
      if (p_154054_.isSame(Fluids.EMPTY)) {
         return p_154053_.dimensionType().ultraWarm() ? Fluids.LAVA : Fluids.WATER;
      } else {
         return p_154054_;
      }
   }

   private static Optional<BlockPos> findBlockVertical(LevelAccessor p_202007_, BlockPos p_202008_, Direction.AxisDirection p_202009_, BiPredicate<BlockPos, BlockState> p_202010_, Predicate<BlockState> p_202011_, int p_202012_) {
      Direction direction = Direction.get(p_202009_, Direction.Axis.Y);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_202008_.mutable();

      for(int i = 1; i < p_202012_; ++i) {
         blockpos$mutableblockpos.move(direction);
         BlockState blockstate = p_202007_.getBlockState(blockpos$mutableblockpos);
         if (p_202011_.test(blockstate)) {
            return Optional.of(blockpos$mutableblockpos.immutable());
         }

         if (p_202007_.isOutsideBuildHeight(blockpos$mutableblockpos.getY()) || !p_202010_.test(blockpos$mutableblockpos, blockstate)) {
            return Optional.empty();
         }
      }

      return Optional.empty();
   }

   private static boolean canDripThrough(BlockGetter p_202018_, BlockPos p_202019_, BlockState p_202020_) {
      if (p_202020_.isAir()) {
         return true;
      } else if (p_202020_.isSolidRender(p_202018_, p_202019_)) {
         return false;
      } else if (!p_202020_.getFluidState().isEmpty()) {
         return false;
      } else {
         VoxelShape voxelshape = p_202020_.getCollisionShape(p_202018_, p_202019_);
         return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelshape, BooleanOp.AND);
      }
   }

   static record FluidInfo(BlockPos pos, Fluid fluid, BlockState sourceState) {
   }
    
}
