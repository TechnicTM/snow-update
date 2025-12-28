package net.technic.snow_update.item;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.technic.snow_update.entity.TitanYetiEntity;
import net.technic.snow_update.init.SnowSoundsRegistry;

public class YetiHornItem extends Item{
    private List<AvoidEntityGoal<?>> goals = new LinkedList<>();
    private List<Mob> mobs = new LinkedList<>();

    public YetiHornItem(Properties pProperties) {
        super(pProperties);
    }
    

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pLivingEntity instanceof Player pPlayer){
            AABB range = pPlayer.getBoundingBox().inflate(10);
            List<Entity> entities = pLevel.getEntities(pPlayer, range);
            for (Entity entity : entities){
                if (entity instanceof Monster mob && !(entity instanceof WitherBoss || entity instanceof Warden || entity instanceof TitanYetiEntity)){
                    AvoidEntityGoal<?> goal = new AvoidEntityGoal<>(mob, pPlayer.getClass(), 12.0F, 1.5F, 1.5F);
                    this.goals.add(goal);
                    this.mobs.add(mob);
                    mob.goalSelector.addGoal(1, goal);
                    
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);
        pLevel.playSound(pPlayer, pPlayer, SnowSoundsRegistry.YETI_HORN.get(), SoundSource.RECORDS, 14, 1.0F);
        pLevel.gameEvent(GameEvent.INSTRUMENT_PLAY, pPlayer.position(), GameEvent.Context.of(pPlayer));
        pPlayer.getCooldowns().addCooldown(itemStack.getItem(), 140);
        itemStack.hurtAndBreak(10, pPlayer, (pEntity)-> {
            pEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }
    

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.TOOT_HORN;
    }
    
    @Override
    public int getUseDuration(ItemStack pStack) {
        return 100;
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        Iterator<AvoidEntityGoal<?>> goalIterator = this.goals.iterator();
        Iterator<Mob> mobIterator = this.mobs.iterator();

        while (goalIterator.hasNext() && mobIterator.hasNext()){
            AvoidEntityGoal<?> goal = goalIterator.next();
            Mob mob = mobIterator.next();
            mob.goalSelector.removeGoal(goal);
            goalIterator.remove();
            mobIterator.remove();
        }
        super.onStopUsing(stack, entity, count);
    }
    
    
}
