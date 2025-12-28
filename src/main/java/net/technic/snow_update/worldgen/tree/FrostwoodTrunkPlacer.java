package net.technic.snow_update.worldgen.tree;

import java.util.List;
import java.util.function.BiConsumer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.technic.snow_update.init.SnowUpdateTrunkPlacerRegistry;

public class FrostwoodTrunkPlacer extends TrunkPlacer{
    public static final Codec<FrostwoodTrunkPlacer> COEDC = RecordCodecBuilder.create(pFrostwoodTrunkPlacerInstace -> 
    trunkPlacerParts(pFrostwoodTrunkPlacerInstace).apply(pFrostwoodTrunkPlacerInstace, FrostwoodTrunkPlacer::new));

    public FrostwoodTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return SnowUpdateTrunkPlacerRegistry.FROSTWOOD_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {
        setDirtAt(pLevel, pBlockSetter, pRandom, pPos.below(), pConfig);
        int height = 5;

        for (int i = 0; i < height; i++) {
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i), pConfig);

            if (i == 0) {
                placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i).relative(Direction.NORTH), pConfig, (pState) -> {
                    return pState.trySetValue(RotatedPillarBlock.AXIS, Direction.NORTH.getAxis());
                });
            }

            if (i == 2) {
                if (pRandom.nextFloat() > 0.333F) {
                    placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i).relative(Direction.SOUTH), pConfig, (pState) -> {
                        return pState.trySetValue(RotatedPillarBlock.AXIS, Direction.SOUTH.getAxis());
                    });
                } else if(pRandom.nextFloat() > 0.333F) {
                    placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i).relative(Direction.EAST), pConfig, (pState) -> {
                        return pState.trySetValue(RotatedPillarBlock.AXIS, Direction.EAST.getAxis());
                    });
                } else if (pRandom.nextFloat() > 0.333F) {
                    placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i).relative(Direction.WEST), pConfig, (pState) -> {
                        return pState.trySetValue(RotatedPillarBlock.AXIS, Direction.WEST.getAxis());
                    });
                }
            }
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pPos.above(height), 0, false));
    }

}
