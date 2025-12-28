package net.technic.snow_update.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.init.SnowItemsRegistry;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider{

    float[] values = new float[]{0.1F, 0.3F, 0.4F, 0.5F, 0.6F, 0.7F, 0.8F, 0.85F, 0.9F, 1.0F};
    String[] names = new String[]{"_quartz_trim", "_netherite_trim", "_redstone_trim", "_copper_trim", "_gold_trim", "_emerald_trim", "_diamond_trim", "_glacier_trim", "_lapis_trim", "_amethyst_trim"};
    String[] names2 = new String[]{"_trim_quartz", "_trim_netherite", "_trim_redstone", "_trim_copper", "_trim_gold", "_trim_emerald", "_trim_diamond", "_trim_glacier_gem", "_trim_lapis", "_trim_amethyst"};

    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SnowUpdate.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(SnowItemsRegistry.GLACIER_RAW_YETI_MEAT);
        simpleItem(SnowItemsRegistry.RAW_YETI_MEAT);
        simpleItem(SnowItemsRegistry.COOKED_YETI_MEAT);
        simpleItem(SnowItemsRegistry.YETI_FUR);
        simpleItem(SnowItemsRegistry.GLACIER_FRAGMENT_SHARD);
        simpleItem(SnowItemsRegistry.GLACIER_GEM);
        simpleItem(SnowItemsRegistry.GLACIER_SHARD);
        simpleItem(SnowItemsRegistry.FROSTWOOD_BOAT);
        simpleItem(SnowItemsRegistry.FROSTWOOD_CHESTBOAT);
        withExistingParent(SnowItemsRegistry.JUVENILE_YETI_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        armorTrim(SnowItemsRegistry.YETI_FUR_HELMET);
        armorTrim(SnowItemsRegistry.YETI_FUR_CHESTPLATE);
        armorTrim(SnowItemsRegistry.YETI_FUR_LEGGINGS);
        armorTrim(SnowItemsRegistry.YETI_FUR_BOOTS);
        armorTrim(SnowItemsRegistry.GLACIER_HELMET);
        armorTrim(SnowItemsRegistry.GLACIER_CHESTPLATE);
        armorTrim(SnowItemsRegistry.GLACIER_LEGGINGS);
        armorTrim(SnowItemsRegistry.GLACIER_BOOTS);
        handHeldItem(SnowItemsRegistry.GLACIER_SWORD);
        handHeldItem(SnowItemsRegistry.GLACIER_PICKAXE);
        handHeldItem(SnowItemsRegistry.GLACIER_AXE);
        handHeldItem(SnowItemsRegistry.GLACIER_SHOVEL);
        handHeldItem(SnowItemsRegistry.GLACIER_HOE);

        glacierTrim("chainmail_helmet", "helmet");
        glacierTrim("chainmail_chestplate", "chestplate");
        glacierTrim("chainmail_leggings", "leggings");
        glacierTrim("chainmail_boots", "boots");

        glacierTrim("iron_helmet", "helmet");
        glacierTrim("iron_chestplate", "chestplate");
        glacierTrim("iron_leggings", "leggings");
        glacierTrim("iron_boots", "boots");
        
        glacierTrim("golden_helmet", "helmet");
        glacierTrim("golden_chestplate", "chestplate");
        glacierTrim("golden_leggings", "leggings");
        glacierTrim("golden_boots", "boots");

        glacierTrim("diamond_helmet", "helmet");
        glacierTrim("diamond_chestplate", "chestplate");
        glacierTrim("diamond_leggings", "leggings");
        glacierTrim("diamond_boots", "boots");

        glacierTrim("netherite_helmet", "helmet");
        glacierTrim("netherite_chestplate", "chestplate");
        glacierTrim("netherite_leggings", "leggings");
        glacierTrim("netherite_boots", "boots");

        fenceInventory("frosted_wood_fence", new ResourceLocation(SnowUpdate.MOD_ID, "block/frosted_planks"));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> pItem){
        return withExistingParent(pItem.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
        new ResourceLocation(SnowUpdate.MOD_ID, "item/"+pItem.getId().getPath()));
    }

    private ItemModelBuilder handHeldItem(RegistryObject<Item> pItem){
        return withExistingParent(pItem.getId().getPath(), new ResourceLocation("item/handheld")).texture("layer0",
        new ResourceLocation(SnowUpdate.MOD_ID, "item/"+pItem.getId().getPath()));
    }

    private ItemModelBuilder armorTrim(RegistryObject<Item> pItem){
        ItemModelBuilder model = withExistingParent(pItem.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0", new ResourceLocation(SnowUpdate.MOD_ID, "item/"+pItem.getId().getPath()));
        if (pItem.get() instanceof ArmorItem armorItem){
            String armorType = switch (armorItem.getEquipmentSlot()) {
                case HEAD -> "helmet";
                case CHEST -> "chestplate";
                case LEGS -> "leggings";
                case FEET -> "boots";
                default -> "";
            };
        
            for (int i = 0; i<10 ; i++){
                ItemModelBuilder modelOverride;
                modelOverride = new ItemModelBuilder(new ResourceLocation(SnowUpdate.MOD_ID, "item/"+pItem.getId().getPath()+names[i]), existingFileHelper);
                model.override().predicate(new ResourceLocation("trim_type"), values[i]).model(modelOverride).end();
                existingFileHelper.trackGenerated(new ResourceLocation("trims/items/" + armorType + names2[i]), PackType.CLIENT_RESOURCES, ".png", "textures");
                getBuilder(pItem.getId().getPath()+names[i])
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(SnowUpdate.MOD_ID, "item/"+pItem.getId().getPath()))
                .texture("layer1", new ResourceLocation("trims/items/" + armorType + names2[i]));
            }

            this.withExistingParent(pItem.getId().getPath(), mcLoc("item/generated"))
            .texture("layer0", new ResourceLocation(SnowUpdate.MOD_ID, "item/"+pItem.getId().getPath()));
        }
        return model;
    }

    public void glacierTrim(String pName, String pType){
        existingFileHelper.trackGenerated(new ResourceLocation("trims/items/"+pType+"_trim_glacier_gem"), PackType.CLIENT_RESOURCES, ".png", "textures");
        getBuilder(pName+"_glacier_trim")
        .parent(new ModelFile.UncheckedModelFile("item/generated"))
        .texture("layer0", new ResourceLocation("item/"+pName))
        .texture("layer1", new ResourceLocation("trims/items/"+pType+"_trim_glacier_gem"));
    }

    

}
