package rydrako.brewnstew.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.references.BlockIds;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.init.ModBlocks;
import rydrako.brewnstew.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BrewNStew.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.getRK(ModBlocks.CAMPFIRE_COOKING_POT.get()));

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.getRK(ModBlocks.CAMPFIRE_COOKING_POT.get()));

        tag(ModTags.Blocks.COOKABLE_FOOD)
                .add(BlockItemIds.PUMPKIN.block());
    }
}
