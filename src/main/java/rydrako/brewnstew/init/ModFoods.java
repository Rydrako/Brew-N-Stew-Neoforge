package rydrako.brewnstew.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import rydrako.brewnstew.food.CookedFoodConsumeEffect;
import rydrako.brewnstew.food.FailedFoodConsumeEffect;

public class ModFoods {

    public static final FoodProperties ROCK_HARD_FOOD = new FoodProperties.Builder().nutrition(0).saturationModifier(0).build();
    public static final FoodProperties DUBIOUS_FOOD = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build();
    public static final FoodProperties STUFFED_PUMPKIN = new FoodProperties.Builder().nutrition(8).saturationModifier(0.5f).build();

    public static final Consumable FAILED_FOOD_CONSUMABLE = Consumables.defaultFood()
            .consumeSeconds(4.0f).onConsume(new FailedFoodConsumeEffect()).build();

    public static final Consumable COOKED_FOOD_CONSUMABLE = Consumables.defaultFood()
            .consumeSeconds(2.0f).onConsume(new CookedFoodConsumeEffect()).build();
}
