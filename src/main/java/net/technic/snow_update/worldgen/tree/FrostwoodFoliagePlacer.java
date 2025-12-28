package net.technic.snow_update.worldgen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.technic.snow_update.init.SnowBlockRegistry;
import net.technic.snow_update.init.SnowUpdateFoliagePlacerRegistry;

public class FrostwoodFoliagePlacer extends FoliagePlacer{
    public static final Codec<FrostwoodFoliagePlacer> CODEC = RecordCodecBuilder.create(pInstance -> foliagePlacerParts(pInstance)
    .and(Codec.intRange(0, 16).fieldOf("height").forGetter(fp -> fp.height)).apply(pInstance, FrostwoodFoliagePlacer::new));
    protected final int height;

    public FrostwoodFoliagePlacer(IntProvider pRadius, IntProvider pOffset, int pHeight) {
        super(pRadius, pOffset);
        this.height = pHeight;
    }

    @Override
    protected FoliagePlacerType<?> type() {
       return SnowUpdateFoliagePlacerRegistry.FROSTWOOD_FOLIAGE_PALCER.get();
    }

    @Override
    
    protected void createFoliage(LevelSimulatedReader pLevel, FoliageSetter pBlockSetter, RandomSource pRandom, TreeConfiguration pConfig, int pMaxFreeTreeHeight, FoliageAttachment pAttachment, int pFoliageHeight, int pFoliageRadius, int pOffset) {
        
        placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos().above(0), 1, 0, pAttachment.doubleTrunk());
        pBlockSetter.set(pAttachment.pos(), SnowBlockRegistry.FROSTED_LOG.get().defaultBlockState());
        placeLeavesRow2(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos().above(1), 2, 2, 0, pAttachment.doubleTrunk());
        placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos().above(2), 2, 0, pAttachment.doubleTrunk());
        placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pAttachment.pos().above(3), 1, 0, pAttachment.doubleTrunk());
    }

    @Override
    public int foliageHeight(RandomSource pRandom, int pHeight, TreeConfiguration pConfig) {
        return this.height;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange,
            boolean pLarge) {
        return false;
    }

    protected void placeLeavesRow2(LevelSimulatedReader pLevel, FoliagePlacer.FoliageSetter pFoliageSetter, RandomSource pRandom, TreeConfiguration pTreeConfiguration, BlockPos pPos, int pRangeX, int pRangeZ, int pLocalY, boolean pLarge) {
        int i = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
  
        for(int j = -pRangeX; j < pRangeX + i; ++j) {
           for(int k = -pRangeZ; k < pRangeZ + i; ++k) {
              if (!this.shouldSkipLocationSigned(pRandom, j, pLocalY, k, pRangeX, pLarge)) {
                 blockpos$mutableblockpos.setWithOffset(pPos, j, pLocalY, k);
                 tryPlaceLeaf(pLevel, pFoliageSetter, pRandom, pTreeConfiguration, blockpos$mutableblockpos);
              }
           }
        }
  
     }

}
