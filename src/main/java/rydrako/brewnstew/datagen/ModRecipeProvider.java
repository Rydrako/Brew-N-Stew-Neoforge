package rydrako.brewnstew.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import rydrako.brewnstew.datagen.recipe.CookingPotRecipeBuilder;
import rydrako.brewnstew.init.ModItems;
import rydrako.brewnstew.tags.ModTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "Brew N' Stew Recipes";
        }
    }

    @Override
    protected void buildRecipes() {
        shaped(RecipeCategory.TOOLS, ModItems.COOKING_POT.get())
                .pattern("I I")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .group("cooking_tools")
                .save(output);

        CookingPotRecipeBuilder.cookingPotRecipe(RecipeCategory.FOOD,
                        List.of(Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ModTags.Items.MONSTER_PARTS))),
                        Ingredient.of(Items.BOWL),
                        ModItems.DUBIOUS_FOOD)
                .unlockedBy("has_cooking_pot", has(ModItems.COOKING_POT))
                .save(output, "brewnstew:dubious_food_cooking");


        CookingPotRecipeBuilder.cookingPotRecipe(RecipeCategory.FOOD,
                List.of(Ingredient.of(Items.MILK_BUCKET),
                        Ingredient.of(Items.SUGAR),
                        Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.EGGS)),
                        Ingredient.of(Items.WHEAT)
                ), Ingredient.of(Items.BOWL), Items.CAKE)
                .unlockedBy("has_cooking_pot", has(ModItems.COOKING_POT))
                .save(output, "brewnstew:cake_cooking");

    }
}
