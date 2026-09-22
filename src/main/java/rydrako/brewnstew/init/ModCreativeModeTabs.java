package rydrako.brewnstew.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import rydrako.brewnstew.BrewNStew;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BrewNStew.MOD_ID);

    public static final Supplier<CreativeModeTab> BREW_N_STEW_TAB = CREATIVE_MODE_TABS.register("brewnstew_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.COOKING_POT.get()))
                    .title(Component.translatable("itemGroup.brewnstew"))
                    .displayItems((itemDisplayParameters, output) ->
                            BuiltInRegistries.ITEM.entrySet().stream()
                                    .filter(entry -> Objects.equals(entry.getKey().identifier().getNamespace(), BrewNStew.MOD_ID))
                                    .map(Map.Entry::getValue)
                                    .forEach(output::accept)
                    )
                    .build());

    public static final Supplier<CreativeModeTab> BREW_N_STEW_FOOD_TAB = CREATIVE_MODE_TABS.register("brewnstew_food_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.COOKING_POT.get()))
                    .title(Component.translatable("itemGroup.brewnstew.food"))
                    .withTabsBefore(Identifier.fromNamespaceAndPath(BrewNStew.MOD_ID, "brewnstew_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
//                        output.accept(ModItems.COOKING_POT);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
