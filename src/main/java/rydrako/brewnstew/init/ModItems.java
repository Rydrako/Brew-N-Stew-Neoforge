package rydrako.brewnstew.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.item.CookedFoodItem;
import rydrako.brewnstew.item.CookingPotItem;
import rydrako.brewnstew.item.FailedFoodItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BrewNStew.MOD_ID);

    public static final DeferredItem<Item> COOKING_POT = ITEMS.registerItem("cooking_pot",
            properties -> new CookingPotItem(properties.stacksTo(1)));

    public static final DeferredItem<Item> ROCK_HARD_FOOD = ITEMS.registerItem("rock_hard_food",
            properties -> new FailedFoodItem(properties.food(ModFoods.ROCK_HARD_FOOD, ModFoods.ROCK_HARD_FOOD_CONSUMABLE)));

    public static final DeferredItem<Item> DUBIOUS_FOOD = ITEMS.registerItem("dubious_food",
            properties -> new CookedFoodItem(properties.food(ModFoods.BEEF_SKEWER, ModFoods.CUSTOM_DISH_CONSUMABLE)));

    public static final DeferredItem<Item> BEEF_SKEWER = ITEMS.registerItem("beef_skewer",
            properties -> new CookedFoodItem(properties.food(ModFoods.BEEF_SKEWER, ModFoods.CUSTOM_DISH_CONSUMABLE)));

    public static final DeferredItem<Item> CHICKEN_SKEWER = ITEMS.registerItem("chicken_skewer",
            properties -> new CookedFoodItem(properties.food(ModFoods.CHICKEN_SKEWER, ModFoods.CUSTOM_DISH_CONSUMABLE)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static ResourceKey<Item> getRK(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).get();
    }
}
