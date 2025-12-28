package net.technic.snow_update.item;

import java.util.List;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import java.util.Iterator;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.technic.snow_update.entity.IceChunkEntity;
import net.technic.snow_update.init.SnowSoundsRegistry;


public class IceWand extends Item{
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    

    public IceWand(Properties pProperties) {
        super(pProperties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon Modifier", 0, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }



    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.getCooldowns().addCooldown(itemStack.getItem(), 40);
        
        if (!pLevel.isClientSide()) {
            LivingEntity target = findTarget2(pPlayer, pLevel, 35, 2);
            if (target != null){
                pLevel.addFreshEntity(new IceChunkEntity(pLevel, pPlayer, target));
            }
        }
        
        
        if (!pPlayer.getAbilities().instabuild) {
            itemStack.hurtAndBreak(1, pPlayer, p -> p.broadcastBreakEvent(pUsedHand));
        }
        pLevel.playSound(pPlayer, pPlayer.blockPosition(), SnowSoundsRegistry.ICE_WAND_WAVE.get(), SoundSource.PLAYERS);
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

    private LivingEntity findTarget(Player pPlayer, Level pLevel, int pRange, float pAssist) {
        LivingEntity target = null;
        AABB searchBox = new AABB(pPlayer.getEyePosition(), pPlayer.getLookAngle().scale(pRange)).inflate(1 + pAssist);
        List<Entity> entities = pLevel.getEntities(pPlayer, searchBox);
        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()){
            Entity suposedTarget = iterator.next();
            if (suposedTarget instanceof LivingEntity suposedTarget1){
                double dist0 = pRange * pRange;
                if (pPlayer.distanceToSqr(suposedTarget) <= dist0) {
                    dist0 = pPlayer.distanceToSqr(suposedTarget);
                    target = suposedTarget1;
                }

            }
        }
        return target;
    }

    private LivingEntity findTarget2(Player pPlayer, Level pLevel, int pRange, float pAssist) {
        Vec3 look = pPlayer.getLookAngle();
        AABB searchArea = pPlayer.getBoundingBox().inflate(pRange);
        List<Entity> entities = pLevel.getEntities(pPlayer, searchArea);
        LivingEntity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;
        Iterator<Entity> iterator = entities.iterator();

        while (iterator.hasNext()) {
            if (iterator.next() instanceof LivingEntity livingEntity) {
                Vec3 entityVec3 = livingEntity.position().subtract(pPlayer.position());
                double angle = entityVec3.dot(look) / (entityVec3.length() * look.length());

                if (angle > 0.5) {
                    double distanceToSqr = pPlayer.distanceToSqr(livingEntity);
                    if (distanceToSqr < closestDistance) {
                        closestDistance = distanceToSqr;
                        closestEntity = livingEntity;
                    }
                }
            }
        }

        return closestEntity;
        
    }
    
    
}
