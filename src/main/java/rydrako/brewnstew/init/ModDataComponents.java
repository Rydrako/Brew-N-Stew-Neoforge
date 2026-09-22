package rydrako.brewnstew.init;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.datacomponent.StatPoint;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, BrewNStew.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> SPEED = registerStat("speed");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> SLOWNESS = registerStat("slowness");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> HASTE = registerStat("haste");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> MINING_FATIGUE = registerStat("mining_fatigue");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> STRENGTH = registerStat("strength");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> INSTANT_HEALTH = registerStat("instant_health");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> INSTANT_DAMAGE = registerStat("instant_damage");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> JUMP_BOOST = registerStat("jump_boost");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> NAUSEA = registerStat("nausea");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> REGENERATION = registerStat("regeneration");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> RESISTANCE = registerStat("resistance");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> FIRE_RESISTANCE = registerStat("fire_resistance");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> WATER_BREATHING = registerStat("water_breathing");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> INVISIBILITY = registerStat("invisibility");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> BLINDNESS = registerStat("blindness");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> NIGHT_VISION = registerStat("night_vision");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> HUNGER = registerStat("hunger");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> WEAKNESS = registerStat("weakness");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> POISON = registerStat("poison");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> WITHER = registerStat("wither");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> HEALTH_BOOST = registerStat("health_boost");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> ABSORPTION = registerStat("absorption");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> GLOWING = registerStat("glowing");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> LEVITATION = registerStat("levitation");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> LUCK = registerStat("luck");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> UNLUCK = registerStat("unluck");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> SLOW_FALLING = registerStat("slow_falling");

    private static DeferredHolder<DataComponentType<?>, DataComponentType<StatPoint>> registerStat(String name) {

        return register(name, effectStatBuilder ->
                effectStatBuilder.persistent(StatPoint.CODEC).networkSynchronized(StatPoint.STREAM_CODEC));
    }

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,
                                                                                          UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register (IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }

}
