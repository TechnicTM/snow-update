package net.technic.snow_update.datagen;


import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.technic.snow_update.init.SnowItemsRegistry;

public class SnowUpdateRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public SnowUpdateRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(RecipeOutput pOutput) {
        cookRecipes(pOutput, "smoking", RecipeSerializer.SMOKING_RECIPE, 100);
        cookRecipes(pOutput, "smelting", RecipeSerializer.SMELTING_RECIPE, 200);
        smithingRecipes(pOutput, Items.LEATHER_HELMET, RecipeCategory.COMBAT, SnowItemsRegistry.YETI_FUR_HELMET.get());
        smithingRecipes(pOutput, Items.LEATHER_CHESTPLATE, RecipeCategory.COMBAT, SnowItemsRegistry.YETI_FUR_CHESTPLATE.get());
        smithingRecipes(pOutput, Items.LEATHER_LEGGINGS, RecipeCategory.COMBAT, SnowItemsRegistry.YETI_FUR_LEGGINGS.get());
        smithingRecipes(pOutput, Items.LEATHER_BOOTS, RecipeCategory.COMBAT, SnowItemsRegistry.YETI_FUR_BOOTS.get());
        
    }

    protected static void cookRecipes(RecipeOutput pRecipeOutput, String pCookingMethod, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, int pCookingTime){
        simpleCookingRecipe(pRecipeOutput, pCookingMethod, pCookingSerializer, 
        pCookingTime, SnowItemsRegistry.GLACIER_RAW_YETI_MEAT.get(), SnowItemsRegistry.RAW_YETI_MEAT.get(), 0.35F);
        simpleCookingRecipe(pRecipeOutput, pCookingMethod, pCookingSerializer, 
        pCookingTime, SnowItemsRegistry.RAW_YETI_MEAT.get(), SnowItemsRegistry.COOKED_YETI_MEAT.get(), 0.35F);
    }

    protected static void smithingRecipes(RecipeOutput pRecipeOutput, Item pIngridient, RecipeCategory pCategory, Item pResult){
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(), Ingredient.of(pIngridient), Ingredient.of(SnowItemsRegistry.YETI_FUR.get()), pCategory, pResult).unlocks("has_yeti_fur", has(SnowItemsRegistry.YETI_FUR.get())).save(pRecipeOutput, getItemName(pResult)+"_smithing");;
    }

    private static void simpleCookingRecipe(RecipeOutput pRecipeOutput, String pCookingMethod, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, int pCookingTime, ItemLike pMaterial, ItemLike pResult, float pExperience) {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(pMaterial), RecipeCategory.FOOD, pResult, pExperience, pCookingTime, pCookingSerializer).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pRecipeOutput, getItemName(pResult) + "_from_" + pCookingMethod);
    }
    
}
