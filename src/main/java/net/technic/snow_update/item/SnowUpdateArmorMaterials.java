package net.technic.snow_update.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.init.SnowItemsRegistry;

import java.util.function.Supplier;

public enum SnowUpdateArmorMaterials implements ArmorMaterial{
    YETI_FUR("yeti_fur", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, ()-> Ingredient.of(SnowItemsRegistry.YETI_FUR.get())),
    GLACIER("glacier", 15 , new int[]{2, 5, 6, 2}, 9, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.0F, 0.0F, ()-> Ingredient.of(SnowItemsRegistry.GLACIER_SHARD.get()));
    
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;
    private static final int[] BASE_DURABILITY = {11, 16, 16, 13};

    private SnowUpdateArmorMaterials(String name, int durabilityMultiplier, int[] protectionFunctionForType, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
                this.name = name;
                this.durabilityMultiplier = durabilityMultiplier;
                this.protectionFunctionForType = protectionFunctionForType;
                this.enchantmentValue = enchantmentValue;
                this.sound = sound;
                this.toughness = toughness;
                this.knockbackResistance = knockbackResistance;
                this.repairIngredient = repairIngredient;
        }


    @Override
    public int getDurabilityForType(Type pType) {
        return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(Type pType) {
        return this.protectionFunctionForType[pType.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
       return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return SnowUpdate.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

}
