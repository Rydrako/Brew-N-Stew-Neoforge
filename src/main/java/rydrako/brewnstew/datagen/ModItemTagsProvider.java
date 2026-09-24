package rydrako.brewnstew.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.references.BlockIds;
import net.minecraft.references.BlockItemIds;
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
        tag(ModTags.Items.FOOD_HOLDER)
                .add(ItemIds.BOWL)
                .add(ItemIds.STICK)
        ;

        tag(ModTags.Items.COOKABLE_FOOD)
                .addTag(Tags.Items.FOODS_RAW_MEAT)
                .addTag(Tags.Items.FOODS_RAW_FISH)
                .addTag(Tags.Items.FOODS_BERRY)
                .addTag(Tags.Items.FOODS_BREAD)
                .addTag(Tags.Items.FOODS_FRUIT)
                .addTag(Tags.Items.FOODS_VEGETABLE)
                .addTag(Tags.Items.FOODS_GOLDEN)
                .addTag(Tags.Items.EGGS)
                .addTag(Tags.Items.DRINKS_HONEY)
                .addTag(Tags.Items.SEEDS)
                .addTag(Tags.Items.MUSHROOMS)
                .add(ItemIds.MILK_BUCKET)
                .add(ItemIds.COOKIE)
                .add(ItemIds.SUGAR)
                .add(ItemIds.WHEAT)
                .add(ItemIds.HONEYCOMB)
                .add(ItemIds.ROTTEN_FLESH)
                .add(ItemIds.BONE)
                .add(ItemIds.SPIDER_EYE)
                .add(ItemIds.FERMENTED_SPIDER_EYE)
                .add(ItemIds.GHAST_TEAR)
                .add(ItemIds.INK_SAC)
                .add(ItemIds.GLOW_INK_SAC)
                .add(ItemIds.BLAZE_POWDER)
        ;

        tag(ModTags.Items.MONSTER_PARTS)
                .add(ItemIds.ROTTEN_FLESH)
                .add(ItemIds.BONE)
                .add(ItemIds.SPIDER_EYE)
                .add(ItemIds.FERMENTED_SPIDER_EYE)
                .add(ItemIds.GHAST_TEAR)
                .add(ItemIds.INK_SAC)
                .add(ItemIds.GLOW_INK_SAC)
                .add(ItemIds.BLAZE_POWDER)
                .add(ItemIds.RABBIT_FOOT)
        ;

        tag(ModTags.Items.SPEED_FOOD)
                .add(ItemIds.SUGAR)
        ;

        tag(ModTags.Items.STRENGTH_FOOD)
                .add(ItemIds.COD)
        ;

        tag(ModTags.Items.JUMP_BOOST_FOOD)
                .add(ItemIds.RABBIT)
        ;

        tag(ModTags.Items.WATER_BREATHING_FOOD)
                .add(ItemIds.PUFFERFISH)
        ;

        tag(ModTags.Items.NIGHT_VISION_FOOD)
                .add(BuiltInRegistries.ITEM.getResourceKey(Items.CARROT).get())
                .add(ItemIds.GOLDEN_CARROT)
                ;

        tag(ModTags.Items.LEVITATION_FOOD)
                .add(ItemIds.CHORUS_FRUIT)
                ;

        tag(ModTags.Items.GLOWING_FOOD)
                .add(ItemIds.GLOW_INK_SAC)
                ;

    }
}
