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

public class YetiFurArmor extends ArmorItem{

    public YetiFurArmor(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String itemName = ForgeRegistries.ITEMS.getKey(YetiFurArmor.this).getPath();
        if (itemName.equals("yeti_fur_helmet") || itemName.equals("yeti_fur_chestplate") || itemName.equals("yeti_fur_boots")){
            return new ResourceLocation(SnowUpdate.MOD_ID, "textures/armor/armoryeti1.png").toString();
        }
        else {
            return new ResourceLocation(SnowUpdate.MOD_ID, "textures/armor/armoryeti2.png").toString();
        }

    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        int i = 0;
        if(!level.isClientSide()){
            if(hasFullArmor(player)){
                if(sameArmorMaterial(player, SnowUpdateArmorMaterials.YETI_FUR)){
                    player.setTicksFrozen(0);
                    if (i == 20){
                        i = 0;
                        damageAllArmors(player);
                    }

                }
            }
        }
    }

    private void damageAllArmors(Player player) {
        for (ItemStack armorStack : player.getInventory().armor){
            damageItem(armorStack, 1, player, (pPlayer)-> {
                pPlayer.broadcastBreakEvent(armorStack.getEquipmentSlot());
            });
        }
    }

    private boolean sameArmorMaterial(Player player, ArmorMaterial pMaterial) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem)player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem)player.getInventory().getArmor(1).getItem());
        ArmorItem breastplate = ((ArmorItem)player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem)player.getInventory().getArmor(3).getItem());

        return helmet.getMaterial() == material && breastplate.getMaterial() == material && leggings.getMaterial() == material && boots.getMaterial() == material;
    }

    private boolean hasFullArmor(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !helmet.isEmpty() || !chestplate.isEmpty() ||!leggings.isEmpty() || !boots.isEmpty();

    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                EntityModelSet entityModelSet = Minecraft.getInstance().getEntityModels();
                return switch (ForgeRegistries.ITEMS.getKey(YetiFurArmor.this).getPath()){

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
