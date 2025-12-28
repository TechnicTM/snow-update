package net.technic.snow_update.worldgen.feature;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.technic.snow_update.worldgen.config.FrozenSpringConfig;

public class FrozenSpringFeature extends Feature<FrozenSpringConfig> {

    public FrozenSpringFeature(Codec<FrozenSpringConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FrozenSpringConfig> pContext) {
        FrozenSpringConfig springconfiguration = pContext.config();
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        if (!worldgenlevel.getBlockState(blockpos.above()).is(springconfiguration.validBlocks)) {
            return false;
        } else if (springconfiguration.requiresBlockBelow && !worldgenlevel.getBlockState(blockpos.below()).is(springconfiguration.validBlocks)) {
            return false;
        } else {
            BlockState blockstate = worldgenlevel.getBlockState(blockpos);
            if (!blockstate.isAir() && !blockstate.is(springconfiguration.validBlocks)) {
                return false;
            } else {
                int i = 0;
                int j = 0;
                if (worldgenlevel.getBlockState(blockpos.west()).is(springconfiguration.validBlocks)) {
                    ++j;
                }

                if (worldgenlevel.getBlockState(blockpos.east()).is(springconfiguration.validBlocks)) {
                    ++j;
                }

                if (worldgenlevel.getBlockState(blockpos.north()).is(springconfiguration.validBlocks)) {
                    ++j;
                }

                if (worldgenlevel.getBlockState(blockpos.south()).is(springconfiguration.validBlocks)) {
                    ++j;
                }

                if (worldgenlevel.getBlockState(blockpos.below()).is(springconfiguration.validBlocks)) {
                    ++j;
                }

                int k = 0;
                if (worldgenlevel.isEmptyBlock(blockpos.west())) {
                    ++k;
                }

                if (worldgenlevel.isEmptyBlock(blockpos.east())) {
                    ++k;
                }

                if (worldgenlevel.isEmptyBlock(blockpos.north())) {
                    ++k;
                }

                if (worldgenlevel.isEmptyBlock(blockpos.south())) {
                    ++k;
                }

                if (worldgenlevel.isEmptyBlock(blockpos.below())) {
                    ++k;
                }

                if (j == springconfiguration.rockCount && k == springconfiguration.holeCount) {
                    worldgenlevel.setBlock(blockpos, springconfiguration.state, 2);
                    worldgenlevel.scheduleTick(blockpos, springconfiguration.state.getBlock(), 0);
                    ++i;
                }

                return i > 0;
            }
        }
    }

}
