package rydrako.brewnstew.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import rydrako.brewnstew.init.ModRecipes;

import java.util.List;

public record CookingPotRecipe(List<Ingredient> ingredients, ItemStackTemplate output) implements Recipe<CookingPotRecipeInput> {
    public static final MapCodec<CookingPotRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(CookingPotRecipe::getIngredients),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(CookingPotRecipe::output)
    ).apply(inst, CookingPotRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CookingPotRecipe> STREAM_CODEC =
            StreamCodec.composite(Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), CookingPotRecipe::ingredients,
                    ItemStackTemplate.STREAM_CODEC, CookingPotRecipe::output,
                    CookingPotRecipe::new);

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean matches(CookingPotRecipeInput input, Level level) {
        if(level.isClientSide())
            return false;

        for(var ingredient : ingredients)
        {
            ingredient.test(input.inputItems().get(0));
        }

        var ing = new java.util.ArrayList<>(ingredients.stream().toList());

        int matching = 0;

        for(var item : input.inputItems())
        {
            if(!item.isEmpty() && ing.contains(Ingredient.of(item.getItem())))
            {
                matching++;
                ing.remove(Ingredient.of(item.getItem()));
            }
        }

        System.out.println("ingredients: " + ing.size());
        return matching == ingredients.size();
    }

    @Override
    public ItemStack assemble(CookingPotRecipeInput cookingPotRecipeInput) {
        return output.create().copy();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "Cooking";
    }

    @Override
    public RecipeSerializer<? extends Recipe<CookingPotRecipeInput>> getSerializer() {
        return ModRecipes.COOKING_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<CookingPotRecipeInput>> getType() {
        return ModRecipes.COOKING_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
