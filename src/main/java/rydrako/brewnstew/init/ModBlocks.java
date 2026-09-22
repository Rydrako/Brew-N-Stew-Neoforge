package rydrako.brewnstew.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.block.CampfireCookingPotBlock;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BrewNStew.MOD_ID);

    public static final DeferredBlock<Block> CAMPFIRE_COOKING_POT = registerBlock("campfire_cooking_pot",
            properties -> new CampfireCookingPotBlock(properties
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(5.0F)
                    .sound(SoundType.ANVIL)
                    .lightLevel(litBlockEmission(15))
                    .noOcclusion()
                    .ignitedByLava())
    );

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    public static void register (IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public static ResourceKey<Block> getRK(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).get();
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightEmission) {
        return state -> state.getValue(BlockStateProperties.LIT) ? lightEmission : 0;
    }
}
