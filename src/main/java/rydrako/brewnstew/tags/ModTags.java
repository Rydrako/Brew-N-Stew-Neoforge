package rydrako.brewnstew.tags;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import rydrako.brewnstew.BrewNStew;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> COOKABLE_FOOD =createTag("cookable_food");

        private static TagKey<Block> createTag (String name) {
            return BlockTags.create(Identifier.fromNamespaceAndPath(BrewNStew.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> COOKABLE_FOOD =createTag("cookable_food");
        public static final TagKey<Item> STRENGTH_FOOD =createTag("strength_food");
        public static final TagKey<Item> SPEED_FOOD =createTag("speed_food");
        public static final TagKey<Item> NIGHT_VISION_FOOD =createTag("night_vision_food");
        public static final TagKey<Item> LEVITATION_FOOD =createTag("levitation_food");
        public static final TagKey<Item> WATER_BREATHING_FOOD =createTag("water_breathing_food");
        public static final TagKey<Item> JUMP_BOOST_FOOD =createTag("jump_boost_food");

        private static TagKey<Item> createTag (String name) {
            return ItemTags.create(Identifier.fromNamespaceAndPath(BrewNStew.MOD_ID, name));
        }
    }
}
