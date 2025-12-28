package net.technic.snow_update.worldgen.surface;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.technic.snow_update.init.SnowBlockRegistry;
import net.technic.snow_update.worldgen.biome.SnowUpdateBiomes;

public class SnowUpdateSurfaceRules {
    private static final  SurfaceRules.RuleSource HOWLITE = makeStateRule(SnowBlockRegistry.HOWLITE.get());

    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(SnowUpdateBiomes.ICE_CAVES), SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, HOWLITE)), 
        SurfaceRules.ifTrue(SurfaceRules.isBiome(SnowUpdateBiomes.ICE_CAVES), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, HOWLITE)), 
        SurfaceRules.ifTrue(SurfaceRules.isBiome(SnowUpdateBiomes.ICE_CAVES), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, HOWLITE)),
        SurfaceRules.ifTrue(SurfaceRules.isBiome(SnowUpdateBiomes.ICE_CAVES), SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, HOWLITE)),
        SurfaceRules.ifTrue(SurfaceRules.isBiome((SnowUpdateBiomes.ICE_CAVES)), HOWLITE));
    }
    
    private static RuleSource makeStateRule(@NotNull Block pBlock) {
        return SurfaceRules.state(pBlock.defaultBlockState());
    }
}
