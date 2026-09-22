package rydrako.brewnstew.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.block.ModBlocks;
import rydrako.brewnstew.item.ModItems;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, BrewNStew.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.COOKING_POT.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.BEEF_SKEWER.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHICKEN_SKEWER.asItem(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModBlocks.CAMPFIRE_COOKING_POT.asItem(), ModelTemplates.FLAT_ITEM);
//        blockModels.createTrivialCube(ModBlocks.CAMPFIRE_COOKING_POT.asItem())
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of();
    }
}
