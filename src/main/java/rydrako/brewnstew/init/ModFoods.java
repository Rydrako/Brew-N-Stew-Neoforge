package rydrako.brewnstew.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import rydrako.brewnstew.food.CookedFoodConsumeEffect;
import rydrako.brewnstew.food.FailedFoodConsumeEffect;

public class ModFoods {

    public static final FoodProperties ROCK_HARD_FOOD = new FoodProperties.Builder().nutrition(0).saturationModifier(0).build();
    public static final FoodProperties BEEF_SKEWER = new FoodProperties.Builder().nutrition(24).saturationModifier(2.4F).build();
    public static final FoodProperties CHICKEN_SKEWER = new FoodProperties.Builder().nutrition(18).saturationModifier(1.8F).build();

    public static final Consumable ROCK_HARD_FOOD_CONSUMABLE = Consumables.defaultFood()
            .consumeSeconds(4.0f).onConsume(new FailedFoodConsumeEffect()).build();

    public static final Consumable CUSTOM_DISH_CONSUMABLE = Consumables.defaultFood()
            .consumeSeconds(2.0f).onConsume(new CookedFoodConsumeEffect()).build();
}
