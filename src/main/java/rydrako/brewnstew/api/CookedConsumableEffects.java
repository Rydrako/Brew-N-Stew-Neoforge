package rydrako.brewnstew.api;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.tags.ModTags;

import java.util.function.Supplier;

public class CookedConsumableEffects {
//    public static final Holder<MobEffect> SPEED;
//    public static final Holder<MobEffect> HASTE;
//    public static final Holder<MobEffect> STRENGTH;
//    public static final Holder<MobEffect> INSTANT_HEALTH;
//    public static final Holder<MobEffect> JUMP_BOOST;
//    public static final Holder<MobEffect> REGENERATION;
//    public static final Holder<MobEffect> RESISTANCE;
//    public static final Holder<MobEffect> FIRE_RESISTANCE;
//    public static final Holder<MobEffect> WATER_BREATHING;
//    public static final Holder<MobEffect> INVISIBILITY;
//    public static final Holder<MobEffect> NIGHT_VISION;
//    public static final Holder<MobEffect> HEALTH_BOOST;
//    public static final Holder<MobEffect> ABSORPTION;
//    public static final Holder<MobEffect> SATURATION;
//    public static final Holder<MobEffect> GLOWING;
//    public static final Holder<MobEffect> LEVITATION;
//    public static final Holder<MobEffect> LUCK;
//    public static final Holder<MobEffect> SLOW_FALLING;

    public static final ResourceKey<Registry<CookedConsumableEffect>> EFFECTS_REGISTRY_KEY = ResourceKey
            .createRegistryKey(Identifier.fromNamespaceAndPath(BrewNStew.MOD_ID, "effects"));

    public static final Registry<CookedConsumableEffect> EFFECT_REGISTRY = new RegistryBuilder<>(EFFECTS_REGISTRY_KEY).create();

    public static final DeferredRegister<CookedConsumableEffect> EFFECTS = DeferredRegister.create(EFFECTS_REGISTRY_KEY, BrewNStew.MOD_ID);

    public static final Supplier<CookedConsumableEffect> SPEED = EFFECTS.register("speed",
            () -> new CookedConsumableEffect(ModTags.Items.SPEED_FOOD, MobEffects.SPEED, 100));

    public static final Supplier<CookedConsumableEffect> STRENGTH = EFFECTS.register("strength",
            () -> new CookedConsumableEffect(ModTags.Items.STRENGTH_FOOD, MobEffects.STRENGTH, 100));

    @SubscribeEvent // on the mod event bus
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(EFFECT_REGISTRY);
//        var speed = EFFECT_REGISTRY.get(Identifier.fromNamespaceAndPath(BrewNStew.MOD_ID, "speed"));
    }
}
