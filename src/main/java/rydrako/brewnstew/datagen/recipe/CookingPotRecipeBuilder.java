package rydrako.brewnstew.datagen.recipe;

import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;
import rydrako.brewnstew.recipe.CookingPotRecipe;

import java.util.List;

public class CookingPotRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ItemStackTemplate result;
    private final List<Ingredient> ingredients;
    private final Ingredient holderItem;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private @Nullable String group;

    private CookingPotRecipeBuilder (RecipeCategory category, List<Ingredient> ingredients, Ingredient holderItem, ItemStackTemplate result) {
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
        this.holderItem = holderItem;
    }

    public static CookingPotRecipeBuilder cookingPotRecipe (RecipeCategory category, List<Ingredient> ingredients, Ingredient holderItem, ItemLike result) {
        return new CookingPotRecipeBuilder(category, ingredients, holderItem, new ItemStackTemplate(result.asItem()));
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> id) {
        CookingPotRecipe recipe = new CookingPotRecipe(this.ingredients, this.holderItem, this.result);
        recipeOutput.accept(id, recipe, this.advancementBuilder.build(recipeOutput, id, this.category));
    }
}
