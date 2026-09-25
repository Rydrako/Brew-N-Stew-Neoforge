package rydrako.brewnstew.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.joml.Vector2i;
import org.jspecify.annotations.Nullable;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.init.ModBlocks;
import rydrako.brewnstew.recipe.CookingPotRecipe;

public class CookingRecipeCategory implements IRecipeCategory<RecipeHolder<CookingPotRecipe>> {
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(BrewNStew.MOD_ID,
            "textures/gui/cooking_pot_recipe.png");
    private final IDrawable icon;
    private final IDrawable overlay;

    public CookingRecipeCategory(IGuiHelper helper) {
        this.overlay = helper.createDrawable(TEXTURE, 0, 0, 116, 54);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CAMPFIRE_COOKING_POT));
    }


    @Override
    public IRecipeType<RecipeHolder<CookingPotRecipe>> getRecipeType() {
        return ModJEIPlugin.COOKING_POT;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.brewnstew.campfire_cooking_pot");
    }

    @Override
    public int getWidth() {
        return 116;
    }

    @Override
    public int getHeight() {
        return 54;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CookingPotRecipe> recipe, IFocusGroup focuses) {

        Vector2i[] ingredientSlots = {
                new Vector2i(1, 1),
                new Vector2i(19, 1),
                new Vector2i(37, 1),
                new Vector2i(1, 19),
                new Vector2i(19, 19),
                new Vector2i(37, 19),
                new Vector2i(1, 37),
                new Vector2i(19, 37),
                new Vector2i(37, 37)
        };

        for(int i = 0; i < recipe.value().getIngredients().size(); i++)
        {
            builder.addSlot(RecipeIngredientRole.INPUT, ingredientSlots[i].x, ingredientSlots[i].y).add(recipe.value().getIngredients().get(i));
        }

        if(!recipe.value().holderItem().isEmpty())
            builder.addSlot(RecipeIngredientRole.INPUT, 64, 1).add(recipe.value().holderItem());

        builder.addSlot(RecipeIngredientRole.CRAFTING_STATION, 64, 28).add(ModBlocks.CAMPFIRE_COOKING_POT.asItem());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 19).add(recipe.value().output());
    }

    @Override
    public void draw(RecipeHolder<CookingPotRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.overlay.draw(guiGraphics, 0, 0);
    }
}
