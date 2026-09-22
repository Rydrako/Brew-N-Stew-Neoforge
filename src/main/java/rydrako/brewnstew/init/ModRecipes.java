package rydrako.brewnstew.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.recipe.CookingPotRecipe;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, BrewNStew.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, BrewNStew.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CookingPotRecipe>> COOKING_SERIALIZER =
            SERIALIZERS.register("cooking", () -> new RecipeSerializer<>(CookingPotRecipe.CODEC, CookingPotRecipe.STREAM_CODEC));

    public static final DeferredHolder<RecipeType<?>, RecipeType<CookingPotRecipe>> COOKING_TYPE =
            TYPES.register("cooking", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "cooking";
                }
            });


    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
