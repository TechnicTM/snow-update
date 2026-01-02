package net.technic.snow_update.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.shaders.Effect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.technic.snow_update.effect.FreezeEffect;
import net.technic.snow_update.entity.IceChunkEntity;
import net.technic.snow_update.init.SnowSoundsRegistry;

import java.util.Iterator;
import java.util.List;

public class IceWand extends Item {
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    private static final Ingredient GLACIER_GEM = Ingredient.of(/* ModItems.GLACIER_GEM.get() */);

    public static final TagKey<EntityType<?>> ICE_WAND_IMMUNE =
            TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("snow_update", "ice_wand_immune"));

    private static final int COOLDOWN_TICKS = 40;
    private static final int RANGE = 35;
    private static final double AIM_CONE_THRESHOLD = 0.5;

    public IceWand(Properties properties) {
        super(properties);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon Modifier", 0, AttributeModifier.Operation.ADDITION)
        );
        this.defaultModifiers = builder.build();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack wand = player.getItemInHand(hand);

        LivingEntity target = null;
        if (!level.isClientSide()) {
            target = findTargetCone(player, level, RANGE, AIM_CONE_THRESHOLD);
            if (target == null) return InteractionResultHolder.fail(wand);

            if (target.getType().is(ICE_WAND_IMMUNE)) {
                return InteractionResultHolder.fail(wand);
            }
        }

        int ammoSlot = player.getAbilities().instabuild ? 0 : findAmmoSlot(player);
        if (!player.getAbilities().instabuild && ammoSlot < 0) {
            return InteractionResultHolder.fail(wand);
        }

        if (!level.isClientSide()) {
            if (!player.getAbilities().instabuild) {
                consumeOneAmmo(player, ammoSlot);

            }

            level.addFreshEntity(new IceChunkEntity(level, player, target));
        }

        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        level.playSound(player, player.blockPosition(), SnowSoundsRegistry.ICE_WAND_WAVE.get(), SoundSource.PLAYERS);

        if (!level.isClientSide() && !player.getAbilities().instabuild) {
            wand.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        }

        return InteractionResultHolder.sidedSuccess(wand, level.isClientSide());
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairCandidate) {
        return GLACIER_GEM.test(repairCandidate) || super.isValidRepairItem(toRepair, repairCandidate);
    }

    private int findAmmoSlot(Player player) {
        for (int i = 0; i < 9; i++) {
            ItemStack s = player.getInventory().getItem(i);
            if (!s.isEmpty() && GLACIER_GEM.test(s)) return i;
        }
        for (int i = 9; i < player.getInventory().getContainerSize(); i++) {
            ItemStack s = player.getInventory().getItem(i);
            if (!s.isEmpty() && GLACIER_GEM.test(s)) return i;
        }
        return -1;
    }

    private void consumeOneAmmo(Player player, int slot) {
        ItemStack ammo = player.getInventory().getItem(slot);
        ammo.shrink(1);
        if (ammo.isEmpty()) player.getInventory().setItem(slot, ItemStack.EMPTY);
    }

    private LivingEntity findTargetCone(Player player, Level level, int range, double coneThreshold) {
        Vec3 look = player.getLookAngle();
        AABB searchArea = player.getBoundingBox().inflate(range);
        List<Entity> entities = level.getEntities(player, searchArea);

        LivingEntity closest = null;
        double closestDist = Double.MAX_VALUE;

        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity e = it.next();
            if (!(e instanceof LivingEntity living)) continue;
            if (living == player) continue;

            if (!player.hasLineOfSight(living)) continue;

            Vec3 toEntity = living.position().subtract(player.position());
            double denom = (toEntity.length() * look.length());
            if (denom <= 0.00001) continue;

            double angleCos = toEntity.dot(look) / denom;
            if (angleCos > coneThreshold) {
                double distSqr = player.distanceToSqr(living);
                if (distSqr < closestDist) {
                    closestDist = distSqr;
                    closest = living;
                }
            }
        }

        return closest;
    }
}
