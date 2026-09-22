package rydrako.brewnstew.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.block.entity.CookingPotBlockEntity;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BrewNStew.MOD_ID);

    public static final Supplier<BlockEntityType<CookingPotBlockEntity>> COOKING_POT_BE =
            BLOCK_ENTITIES.register("campfire_cooking_pot_be", () -> new BlockEntityType<>(
               CookingPotBlockEntity::new, ModBlocks.CAMPFIRE_COOKING_POT.get()));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
