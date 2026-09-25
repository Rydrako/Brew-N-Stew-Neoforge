package rydrako.brewnstew.datagen.recipe;

import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
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
    private final int holdersConsumed;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private @Nullable String group;

    private CookingPotRecipeBuilder (RecipeCategory category, List<Ingredient> ingredients, Ingredient holderItem, int holdersConsumed, ItemStackTemplate result) {
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
        this.holderItem = holderItem;
        this.holdersConsumed = holdersConsumed;
    }

    public static CookingPotRecipeBuilder cookingPotRecipe (RecipeCategory category, List<Ingredient> ingredients, Ingredient holderItem, int holdersConsumed, ItemLike result) {
        return new CookingPotRecipeBuilder(category, ingredients, holderItem, holdersConsumed, new ItemStackTemplate(result.asItem()));
    }

    public static CookingPotRecipeBuilder cookingPotRecipe (RecipeCategory category, List<Ingredient> ingredients, Ingredient holderItem, ItemLike result) {
        return new CookingPotRecipeBuilder(category, ingredients, holderItem, 1, new ItemStackTemplate(result.asItem()));
    }

    public static CookingPotRecipeBuilder cookingPotRecipe (RecipeCategory category, List<Ingredient> ingredients, ItemLike result) {
        return new CookingPotRecipeBuilder(category, ingredients, Ingredient.of(Items.BOWL), 0, new ItemStackTemplate(result.asItem()));
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        group = group;
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(result);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> id) {
        CookingPotRecipe recipe = new CookingPotRecipe(ingredients, holderItem, holdersConsumed, result);
        recipeOutput.accept(id, recipe, advancementBuilder.build(recipeOutput, id, category));
    }
}
