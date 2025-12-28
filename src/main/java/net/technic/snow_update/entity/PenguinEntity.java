package net.technic.snow_update.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;

import java.util.List;

public class PenguinEntity extends PathfinderMob implements GeoEntity {

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    private static final Ingredient TEMPT_ITEMS = Ingredient.of(
            Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH
    );

    private static final int PREGNANCY_DURATION_TICKS = 20 * 60 * 3;
    private static final double WATER_BUFF_RADIUS = 8.0;

    private static final int FETCH_FAIL_TIMEOUT = 20 * 20;
    private static final double TO_WATER_SPEED = 1.35;
    private static final double RETURN_SPEED = 1.25;

    private static final RawAnimation ANIM_IDLE = RawAnimation.begin().thenLoop("animation.idle");
    private static final RawAnimation ANIM_WADDLE = RawAnimation.begin().thenLoop("animation.waddle");
    private static final RawAnimation ANIM_SLIDE = RawAnimation.begin().thenLoop("animation.slide");
    private static final RawAnimation ANIM_SWIM = RawAnimation.begin().thenLoop("animation.swim");

    private static final ResourceLocation PENGUIN_LOOT = new ResourceLocation("snow_update", "gameplay/penguin_treasure");

    private boolean pregnant = false;
    private int pregnancyTicks = 0;

    private int fetchState = 0;
    private int fetchTimeout = 0;

    public PenguinEntity(EntityType<? extends PenguinEntity> type, Level level) {
        super(type, level);
        this.setCanPickUpLoot(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.15D, TEMPT_ITEMS, false));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED, 0.7D)
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH, 20.0D)
                .add(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE, 0.2D)
                .add(net.minecraft.world.entity.ai.attributes.Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "main", 5, state -> {
            if (this.isInWaterOrBubble()) return state.setAndContinue(ANIM_SWIM);
            if (this.isSliding()) return state.setAndContinue(ANIM_SLIDE);
            if (state.isMoving()) return state.setAndContinue(ANIM_WADDLE);
            return state.setAndContinue(ANIM_IDLE);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide) {
            tickPregnancy();
            tickFetch();
            applyWaterBuffToNearbyPlayers();
        }
    }

    private void tickPregnancy() {
        if (!pregnant) return;

        if (pregnancyTicks > 0) {
            pregnancyTicks--;
        } else {
            pregnant = false;
            this.level().playSound(null, this.blockPosition(), SoundEvents.CHICKEN_EGG, SoundSource.NEUTRAL, 0.8F, 1.0F);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (TEMPT_ITEMS.test(stack) && fetchState == 0) {
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) stack.shrink(1);

                setMouthItem(new ItemStack(Items.COD));

                if (!pregnant && this.random.nextFloat() < 0.20F) {
                    pregnant = true;
                    pregnancyTicks = PREGNANCY_DURATION_TICKS;
                }

                startFetch(player);
                spawnFeedParticles();

                this.level().playSound(null, this.blockPosition(), SoundEvents.FOX_EAT, SoundSource.NEUTRAL, 0.8F, 1.1F);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    private void startFetch(Player player) {
        CompoundTag tag = this.getPersistentData();
        tag.putUUID("TreasureTarget", player.getUUID());
        tag.remove("TreasureItem");
        tag.remove("FetchWaterPos");

        BlockPos water = findNearbyWater(this.blockPosition(), 24);
        if (water == null) {
            endFetchFail();
            return;
        }

        tag.put("FetchWaterPos", NbtUtils.writeBlockPos(water));

        this.fetchState = 1;
        this.fetchTimeout = FETCH_FAIL_TIMEOUT;

        this.getNavigation().moveTo(water.getX() + 0.5, water.getY(), water.getZ() + 0.5, TO_WATER_SPEED);
    }

    private void tickFetch() {
        if (fetchState == 0) return;

        if (fetchTimeout-- <= 0) {
            endFetchFail();
            return;
        }

        Player target = getTargetPlayer();
        if (target == null) {
            endFetchFail();
            return;
        }

        if (fetchState == 1) {
            if (this.isInWaterOrBubble()) {
                diveIfShallow();

                if (!isDeepEnoughWater()) {
                    if (this.tickCount % 10 == 0) {
                        BlockPos water = getStoredWaterPos();
                        if (water != null) this.getNavigation().moveTo(water.getX() + 0.5, water.getY(), water.getZ() + 0.5, TO_WATER_SPEED);
                    }
                    return;
                }

                ItemStack loot = rollPenguinTreasure();
                if (loot.isEmpty()) {
                    endFetchFail();
                    return;
                }

                this.getPersistentData().put("TreasureItem", loot.save(new CompoundTag()));
                setMouthItem(loot.copy());

                this.level().playSound(null, this.blockPosition(), SoundEvents.PLAYER_SPLASH, SoundSource.NEUTRAL, 0.6F, 1.0F);

                this.fetchState = 2;
                this.getNavigation().moveTo(target, RETURN_SPEED);
                return;
            }

            BlockPos water = getStoredWaterPos();
            if (water != null && this.tickCount % 10 == 0) {
                this.getNavigation().moveTo(water.getX() + 0.5, water.getY(), water.getZ() + 0.5, TO_WATER_SPEED);
            }
            return;
        }

        if (fetchState == 2) {
            this.getNavigation().moveTo(target, RETURN_SPEED);

            if (this.distanceToSqr(target) <= 2.5 * 2.5) {
                giveTreasureTo(target);
                endFetchSuccess();
            }
        }
    }

    private void giveTreasureTo(Player target) {
        CompoundTag tag = this.getPersistentData();
        if (!tag.contains("TreasureItem")) return;

        ItemStack loot = ItemStack.of(tag.getCompound("TreasureItem"));
        if (loot.isEmpty()) return;

        boolean inserted = target.getInventory().add(loot.copy());
        if (!inserted) target.drop(loot.copy(), false);

        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, target.getX(), target.getY() + 1.0, target.getZ(), 10, 0.35, 0.35, 0.35, 0.02);
        }

        this.level().playSound(null, target.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.NEUTRAL, 0.35F, 1.7F);
    }

    private ItemStack rollPenguinTreasure() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return ItemStack.EMPTY;

        LootTable table = serverLevel.getServer().getLootData().getLootTable(PENGUIN_LOOT);

        LootParams params = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, this.position())
                .withParameter(LootContextParams.THIS_ENTITY, this)
                .create(LootContextParamSets.GIFT);

        List<ItemStack> rolls = table.getRandomItems(params);
        for (ItemStack s : rolls) {
            if (!s.isEmpty()) return s;
        }
        return ItemStack.EMPTY;
    }


    private Player getTargetPlayer() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return null;
        CompoundTag tag = this.getPersistentData();
        if (!tag.hasUUID("TreasureTarget")) return null;
        return serverLevel.getPlayerByUUID(tag.getUUID("TreasureTarget"));
    }

    private BlockPos getStoredWaterPos() {
        CompoundTag tag = this.getPersistentData();
        if (!tag.contains("FetchWaterPos")) return null;
        return NbtUtils.readBlockPos(tag.getCompound("FetchWaterPos"));
    }

    private void endFetchFail() {
        this.fetchState = 0;
        this.fetchTimeout = 0;
        this.getNavigation().stop();
        setMouthItem(ItemStack.EMPTY);
        CompoundTag tag = this.getPersistentData();
        tag.remove("TreasureItem");
        tag.remove("FetchWaterPos");
        tag.remove("TreasureTarget");
        this.setNoGravity(false);
    }

    private void endFetchSuccess() {
        this.fetchState = 0;
        this.fetchTimeout = 0;
        this.getNavigation().stop();
        setMouthItem(ItemStack.EMPTY);
        CompoundTag tag = this.getPersistentData();
        tag.remove("TreasureItem");
        tag.remove("FetchWaterPos");
        tag.remove("TreasureTarget");
        this.setNoGravity(false);
    }

    private void spawnFeedParticles() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        serverLevel.sendParticles(ParticleTypes.HEART, this.getX(), this.getY() + 0.8, this.getZ(), 6, 0.25, 0.25, 0.25, 0.02);
        serverLevel.sendParticles(ParticleTypes.SPLASH, this.getX(), this.getY() + 0.2, this.getZ(), 10, 0.35, 0.05, 0.35, 0.08);
    }

    private BlockPos findNearbyWater(BlockPos origin, int radius) {
        for (int i = 0; i < 200; i++) {
            int dx = this.random.nextInt(-radius, radius + 1);
            int dz = this.random.nextInt(-radius, radius + 1);
            BlockPos start = origin.offset(dx, 0, dz);

            for (int dy = -6; dy <= 6; dy++) {
                BlockPos pos = start.offset(0, dy, 0);

                if (!this.level().getFluidState(pos).is(FluidTags.WATER)) continue;

                if (this.level().getFluidState(pos.below()).is(FluidTags.WATER)
                        && this.level().getFluidState(pos.below(2)).is(FluidTags.WATER)) {
                    return pos;
                }
            }
        }
        return null;
    }


    private void applyWaterBuffToNearbyPlayers() {
        List<Player> players = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(WATER_BUFF_RADIUS));
        for (Player p : players) {
            if (!p.isInWaterOrBubble()) continue;
            p.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 40, 0, true, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0, true, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.LUCK, 40, 0, true, false, true));
        }
    }

    public boolean isSliding() {
        if (!this.onGround()) return false;

        BlockPos below = this.blockPosition().below();
        boolean onIce =
                this.level().getBlockState(below).is(net.minecraft.world.level.block.Blocks.ICE)
                        || this.level().getBlockState(below).is(net.minecraft.world.level.block.Blocks.PACKED_ICE)
                        || this.level().getBlockState(below).is(net.minecraft.world.level.block.Blocks.BLUE_ICE)
                        || this.level().getBlockState(below).is(net.minecraft.world.level.block.Blocks.FROSTED_ICE);

        double horizontalSpeed = Mth.sqrt((float) (this.getDeltaMovement().x * this.getDeltaMovement().x + this.getDeltaMovement().z * this.getDeltaMovement().z));
        return onIce && horizontalSpeed > 0.12;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Pregnant", pregnant);
        tag.putInt("PregnancyTicks", pregnancyTicks);
        tag.putInt("FetchState", fetchState);
        tag.putInt("FetchTimeout", fetchTimeout);
        CompoundTag pd = this.getPersistentData();
        if (pd.contains("MouthItem")) tag.put("MouthItem", pd.getCompound("MouthItem"));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        pregnant = tag.getBoolean("Pregnant");
        pregnancyTicks = tag.getInt("PregnancyTicks");
        fetchState = tag.getInt("FetchState");
        fetchTimeout = tag.getInt("FetchTimeout");
        if (tag.contains("MouthItem")) this.getPersistentData().put("MouthItem", tag.getCompound("MouthItem"));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return super.hurt(source, amount);
    }

    public boolean isPesto() {
        if (!this.hasCustomName()) return false;
        String n = this.getName().getString();
        return n != null && n.equalsIgnoreCase("Pesto");
    }

    public ItemStack getMouthItem() {
        CompoundTag tag = this.getPersistentData();
        if (!tag.contains("MouthItem")) return ItemStack.EMPTY;
        return ItemStack.of(tag.getCompound("MouthItem"));
    }

    private void setMouthItem(ItemStack stack) {
        CompoundTag tag = this.getPersistentData();
        if (stack == null || stack.isEmpty()) tag.remove("MouthItem");
        else tag.put("MouthItem", stack.save(new CompoundTag()));
    }

    private boolean isDeepEnoughWater() {
        BlockPos pos = this.blockPosition();
        return this.level().getFluidState(pos).is(FluidTags.WATER)
                && this.level().getFluidState(pos.below()).is(FluidTags.WATER)
                && this.level().getFluidState(pos.below(2)).is(FluidTags.WATER);
    }

    private void diveIfShallow() {
        if (!this.isInWaterOrBubble()) return;

        if (isDeepEnoughWater()) {
            this.setNoGravity(false);
            return;
        }

        this.setNoGravity(true);
        this.setDeltaMovement(this.getDeltaMovement().x, -0.25, this.getDeltaMovement().z);
        this.hasImpulse = true;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new AmphibiousPathNavigation(this, level);
    }

}
