package net.technic.snow_update.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;

import net.technic.snow_update.init.SnowBlockEntitiesRegistry;

import java.util.List;

public class IcebudBlockEntity extends BlockEntity {

    private static final int CHECK_INTERVAL_TICKS = 10; // 0.5s
    private static final int RADIUS = 10;

    // Tags you will define in data packs
    private static final TagKey<Item> GLACIER_ITEMS =
            TagKey.create(Registries.ITEM, new ResourceLocation("snow_update", "glacier_items"));
    private static final TagKey<Block> GLACIER_BLOCKS =
            TagKey.create(Registries.BLOCK, new ResourceLocation("snow_update", "glacier_blocks"));

    private int tickCounter = 0;

    public IcebudBlockEntity(BlockPos pos, BlockState state) {
        super(SnowBlockEntitiesRegistry.ICEBUD.get(), pos, state);
    }

    public void tickServer() {
        if (this.level == null) return;

        // Safety: only run on lower half
        BlockState current = this.getBlockState();
        if (!(current.getBlock() instanceof IcebudBlock)) return;
        if (current.getValue(IcebudBlock.HALF) != DoubleBlockHalf.LOWER) return;

        tickCounter++;
        if (tickCounter < CHECK_INTERVAL_TICKS) return;
        tickCounter = 0;

        int targetLight = computeTargetLight(this.level, this.worldPosition);
        setLightBothHalves(this.level, this.worldPosition, targetLight);
    }

    private int computeTargetLight(Level level, BlockPos origin) {
        double bestDistSq = Double.POSITIVE_INFINITY;

        // 1) Scan dropped items
        AABB box = new AABB(
                origin.getX() - RADIUS, origin.getY() - RADIUS, origin.getZ() - RADIUS,
                origin.getX() + RADIUS + 1, origin.getY() + RADIUS + 1, origin.getZ() + RADIUS + 1
        );

        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, box);
        double ox = origin.getX() + 0.5;
        double oy = origin.getY() + 0.5;
        double oz = origin.getZ() + 0.5;

        for (ItemEntity ent : items) {
            if (ent.getItem().is(GLACIER_ITEMS)) {
                double d = ent.distanceToSqr(ox, oy, oz);
                if (d < bestDistSq) bestDistSq = d;
            }
        }

        // 2) Scan blocks in a cube around the flower
        for (int dx = -RADIUS; dx <= RADIUS; dx++) {
            for (int dy = -RADIUS; dy <= RADIUS; dy++) {
                for (int dz = -RADIUS; dz <= RADIUS; dz++) {
                    BlockPos p = origin.offset(dx, dy, dz);
                    if (level.getBlockState(p).is(GLACIER_BLOCKS)) {
                        double d = p.distSqr(origin);
                        if (d < bestDistSq) bestDistSq = d;
                    }
                }
            }
        }

        if (bestDistSq == Double.POSITIVE_INFINITY) return 0;

        double dist = Math.sqrt(bestDistSq);

        // dist = 0 => 15, dist >= RADIUS => 0-ish
        double t = 1.0 - (dist / (double) RADIUS);
        if (t < 0) t = 0;

        // Curve so it's noticeably brighter when very close
        double curved = t * t;

        int light = (int) Math.round(curved * 15.0);
        if (light < 0) light = 0;
        if (light > 15) light = 15;
        return light;
    }

    private void setLightBothHalves(Level level, BlockPos lowerPos, int light) {
        BlockState lower = level.getBlockState(lowerPos);
        if (!(lower.getBlock() instanceof IcebudBlock)) return;

        // If somehow called on upper, normalize to lower
        if (lower.getValue(IcebudBlock.HALF) != DoubleBlockHalf.LOWER) {
            BlockPos below = lowerPos.below();
            lowerPos = below;
            lower = level.getBlockState(lowerPos);
            if (!(lower.getBlock() instanceof IcebudBlock)) return;
            if (lower.getValue(IcebudBlock.HALF) != DoubleBlockHalf.LOWER) return;
        }

        int currentLowerLight = lower.getValue(IcebudBlock.LIGHT);
        if (currentLowerLight != light) {
            level.setBlock(lowerPos, lower.setValue(IcebudBlock.LIGHT, light), 3);
        }

        BlockPos upperPos = lowerPos.above();
        BlockState upper = level.getBlockState(upperPos);
        if (upper.getBlock() instanceof IcebudBlock) {
            // Ensure upper half exists and mirrors light
            if (upper.getValue(IcebudBlock.LIGHT) != light) {
                level.setBlock(upperPos, upper.setValue(IcebudBlock.LIGHT, light), 3);
            }
        }
    }
}
