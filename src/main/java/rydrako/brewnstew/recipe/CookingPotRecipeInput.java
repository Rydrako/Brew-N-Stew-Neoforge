package rydrako.brewnstew.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record CookingPotRecipeInput(List<ItemStack> inputIngredients, ItemStack holderItem) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        return inputIngredients.get(i);
    }

    @Override
    public int size() {
        return 9;
    }
}
