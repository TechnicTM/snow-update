package net.technic.snow_update.item;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.entity.client.armor.YetiArmorModel;
import net.technic.snow_update.entity.layers.SnowUpdateLayers;

public class YetiFurArmor extends ArmorItem {

    public YetiFurArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String itemName = ForgeRegistries.ITEMS.getKey(this).getPath();
        if (itemName.equals("yeti_fur_helmet") || itemName.equals("yeti_fur_chestplate") || itemName.equals("yeti_fur_boots")) {
            return new ResourceLocation(SnowUpdate.MOD_ID, "textures/armor/armoryeti1.png").toString();
        } else {
            return new ResourceLocation(SnowUpdate.MOD_ID, "textures/armor/armoryeti2.png").toString();
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (level.isClientSide()) return;

        // New rule: any piece of Yeti Fur armor protects from freezing
        if (isWearingAnyYetiFur(player)) {
            player.setTicksFrozen(0);

            // Optional: damage only Yeti Fur pieces once per second (20 ticks)
            if (player.tickCount % 20 == 0) {
                damageYetiFurPieces(player, 1);
            }
        }
    }

    private boolean isWearingAnyYetiFur(Player player) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if (armorStack.isEmpty()) continue;

            // Option A (very direct): any instance of this armor class counts
            if (armorStack.getItem() instanceof YetiFurArmor) {
                return true;
            }

            // Option B (more general): check by material
            if (armorStack.getItem() instanceof ArmorItem armorItem) {
                if (armorItem.getMaterial() == SnowUpdateArmorMaterials.YETI_FUR) {
                    return true;
                }
            }
        }
        return false;
    }

    private void damageYetiFurPieces(Player player, int amount) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if (armorStack.isEmpty()) continue;

            boolean isYetiPiece =
                    (armorStack.getItem() instanceof YetiFurArmor) ||
                            (armorStack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial() == SnowUpdateArmorMaterials.YETI_FUR);

            if (!isYetiPiece) continue;

            armorStack.hurtAndBreak(amount, player, p -> p.broadcastBreakEvent(armorStack.getEquipmentSlot()));
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                EntityModelSet entityModelSet = Minecraft.getInstance().getEntityModels();
                return switch (ForgeRegistries.ITEMS.getKey(YetiFurArmor.this).getPath()) {

                    case "yeti_fur_helmet" -> new YetiArmorModel.Helmet(entityModelSet.bakeLayer(SnowUpdateLayers.YETI_FUR_ARMOR_LAYER_1));

                    case "yeti_fur_chestplate" -> new YetiArmorModel.Chestplate(entityModelSet.bakeLayer(SnowUpdateLayers.YETI_FUR_ARMOR_LAYER_2));

                    case "yeti_fur_leggings" -> new YetiArmorModel.Leggings(entityModelSet.bakeLayer(SnowUpdateLayers.YETI_FUR_ARMOR_LAYER_3));

                    case "yeti_fur_boots" -> new YetiArmorModel.Boots(entityModelSet.bakeLayer(SnowUpdateLayers.YETI_FUR_ARMOR_LAYER_4));

                    default -> original;
                };
            }
        });
    }
}
