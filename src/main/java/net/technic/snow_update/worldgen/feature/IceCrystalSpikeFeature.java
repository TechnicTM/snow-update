package net.technic.snow_update.worldgen.feature;

import java.util.HashSet;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.technic.snow_update.init.SnowBlockRegistry;
import net.technic.snow_update.worldgen.config.IceCrystalSpikeConfig;

public class IceCrystalSpikeFeature extends Feature<IceCrystalSpikeConfig> {

    public IceCrystalSpikeFeature(Codec<IceCrystalSpikeConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<IceCrystalSpikeConfig> pContext) {
        WorldGenLevel world = pContext.level();
        BlockPos blockPos = pContext.origin();
        RandomSource random = pContext.random();
        IceCrystalSpikeConfig config = (IceCrystalSpikeConfig)pContext.config();
        HashSet<BlockPos> trigList = Sets.newHashSet();
        HashSet<BlockPos> clusterPos = Sets.newHashSet();
        boolean flag = false;
        int radiusCheck = config.xzRadius.sample(random) + 1;
        int randomChance = random.nextInt(4);
        int stepHeight = radiusCheck + 14 + Mth.nextInt(random, 10, 14);
        
        return flag;
    }


    public boolean placeSpike(LevelAccessor world, BlockPos blockPos, int startRadius, int height, int randomChance, HashSet<BlockPos> crystalPos, Direction direction, RandomSource random) {
        boolean flag = false;

        for(int y = 0; y < height; ++y) {
            int radius = startRadius - y / 2;

            for(int x = -radius; x <= radius; ++x) {
                for(int z = -radius; z <= radius; ++z) {
                    BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY(), blockPos.getZ() + z);
                    if (x * x + z * z <= radius * radius) {
                        if (direction == Direction.DOWN) {
                            if (world.isStateAtPosition(pos.below(), DripstoneUtils::isEmptyOrWaterOrLava)) {
                                return this.placeSpike(world, blockPos.below(), startRadius / 2, height, randomChance, crystalPos, direction, random);
                            }
                        } else if (direction == Direction.UP) {
                            BlockPos.MutableBlockPos mut = pos.mutable();

                            for(int i = 0; i < 10 && world.isStateAtPosition(mut.above(), DripstoneUtils::isEmptyOrWaterOrLava); ++i) {
                                mut.move(Direction.UP);
                            }

                            pos = mut.immutable();
                            if (world.isStateAtPosition(pos.above(), DripstoneUtils::isEmptyOrWaterOrLava)) {
                                return false;
                            }
                        }

                        this.calciteBloom(world, pos.relative(direction), random, radius);
                        float var10000;
                        switch (randomChance) {
                            case 0:
                                var10000 = 2.617994F;
                                break;
                            case 1:
                                var10000 = 5.759587F;
                                break;
                            case 2:
                                var10000 = 0.5235988F;
                                break;
                            case 3:
                                var10000 = 3.6651917F;
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + randomChance);
                        }

                        float delta = var10000;
                        float q = Mth.cos(delta) * (float)y;
                        float k = Mth.sin(1.5707964F) * (float)y;
                        float l = Mth.sin(delta) * (float)y;
                        float xx = direction == Direction.UP ? -q : q;
                        float yy = direction == Direction.UP ? -k : k;
                        float zz = direction == Direction.UP ? -l : l;
                        BlockPos trigPos = pos.offset((int)xx, (int)yy, (int)zz);
                        if (world.isStateAtPosition(trigPos, DripstoneUtils::isEmptyOrWaterOrLava)) {
                            crystalPos.add(trigPos);
                            flag = true;
                        } else {
                            crystalPos.remove(trigPos);
                        }
                    }
                }
            }
        }

        return flag;
    }

    private boolean calciteBloom(LevelAccessor world, BlockPos blockPos, RandomSource random, int crystalRadius) {
        int radius = crystalRadius / 4;
        int height = ConstantInt.of(2).sample(random);
        boolean flag = false;

        for(int x = -radius; x <= radius; ++x) {
            for(int z = -radius; z <= radius; ++z) {
                for(int y = -height; y <= height; ++y) {
                    BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                    Direction[] var12 = Direction.values();
                    int var13 = var12.length;

                    for(int var14 = 0; var14 < var13; ++var14) {
                        Direction direction = var12[var14];
                        if (world.getBlockState(pos).is(BlockTags.BASE_STONE_NETHER) && world.isStateAtPosition(pos.relative(direction), DripstoneUtils::isEmptyOrWaterOrLava)) {
                            world.setBlock(pos, SnowBlockRegistry.ICE_SPIKE_BLOCK.get().defaultBlockState(), 2);
                            flag = true;
                        }
                    }
                }
            }
        }

        return flag;
    }

}
