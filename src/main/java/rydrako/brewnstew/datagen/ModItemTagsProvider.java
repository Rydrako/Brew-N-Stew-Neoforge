package rydrako.brewnstew.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.references.ItemIds;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BrewNStew.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.COOKABLE_FOOD)
                .addTag(Tags.Items.FOODS_RAW_MEAT)
                .addTag(Tags.Items.FOODS_RAW_FISH)
                .addTag(Tags.Items.FOODS_BERRY)
                .addTag(Tags.Items.FOODS_BREAD)
                .addTag(Tags.Items.FOODS_FRUIT)
                .addTag(Tags.Items.FOODS_VEGETABLE)
        ;

        tag(ModTags.Items.ATTACK_BUFF_FOOD)
                .add(ItemIds.BEEF)
                .add(ItemIds.PUFFERFISH)
        ;
    }
}
