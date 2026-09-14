package rydrako.brewnstew.tags;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import rydrako.brewnstew.BrewNStew;

public class ModTags {
//    public static class Blocks {
//        public static final TagKey<Block> TEST = createTag("test");
//
//        private static TagKey<Block> createTag (String name) {
//            return BlockTags.create(Identifier.fromNamespaceAndPath(BrewNStew.MOD_ID, name));
//        }
//    }

    public static class Items {
        public static final TagKey<Item> COOKABLE_FOOD =createTag("cookable_food");
        public static final TagKey<Item> ATTACK_BUFF_FOOD =createTag("attack_buff_food");

        private static TagKey<Item> createTag (String name) {
            return ItemTags.create(Identifier.fromNamespaceAndPath(BrewNStew.MOD_ID, name));
        }
    }
}
