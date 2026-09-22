package rydrako.brewnstew.food;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import rydrako.brewnstew.BrewNStew;

import java.util.function.Supplier;

public class ModConsumeEffects {

    public static final DeferredRegister<ConsumeEffect.Type<?>> CONSUME_EFFECT_TYPES =
            DeferredRegister.create(BuiltInRegistries.CONSUME_EFFECT_TYPE, BrewNStew.MOD_ID);

    public static final Supplier<ConsumeEffect.Type<CookedFoodConsumeEffect>> CUSTOM_CONSUME_EFFECT =
            CONSUME_EFFECT_TYPES.register("custom_effect", () -> new ConsumeEffect.Type<>(CookedFoodConsumeEffect.CODEC,
                    CookedFoodConsumeEffect.STREAM_CODEC));


    public static void register(IEventBus eventBus) {
        CONSUME_EFFECT_TYPES.register(eventBus);
    }
}
