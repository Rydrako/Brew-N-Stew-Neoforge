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
        public static final TagKey<Item> MONSTER_PARTS =createTag("monster_parts");
        public static final TagKey<Item> SPEED_FOOD = createTag("speed_food");
        public static final TagKey<Item> SLOWNESS_FOOD = createTag("slowness_food");
        public static final TagKey<Item> HASTE_FOOD = createTag("haste_food");
        public static final TagKey<Item> MINING_FATIGUE_FOOD = createTag("mining_fatigue_food");
        public static final TagKey<Item> STRENGTH_FOOD = createTag("strength_food");
        public static final TagKey<Item> INSTANT_HEALTH_FOOD = createTag("instant_health_food");
        public static final TagKey<Item> INSTANT_DAMAGE_FOOD = createTag("instant_damage_food");
        public static final TagKey<Item> JUMP_BOOST_FOOD = createTag("jump_boost_food");
        public static final TagKey<Item> NAUSEA_FOOD = createTag("nausea_food");
        public static final TagKey<Item> REGENERATION_FOOD = createTag("regeneration_food");
        public static final TagKey<Item> RESISTANCE_FOOD = createTag("resistance_food");
        public static final TagKey<Item> FIRE_RESISTANCE_FOOD = createTag("fire_resistance_food");
        public static final TagKey<Item> WATER_BREATHING_FOOD = createTag("water_breathing_food");
        public static final TagKey<Item> INVISIBILITY_FOOD = createTag("invisibility_food");
        public static final TagKey<Item> BLINDNESS_FOOD = createTag("blindness_food");
        public static final TagKey<Item> NIGHT_VISION_FOOD = createTag("night_vision_food");
        public static final TagKey<Item> HUNGER_FOOD = createTag("hunger_food");
        public static final TagKey<Item> WEAKNESS_FOOD = createTag("weakness_food");
        public static final TagKey<Item> POISON_FOOD = createTag("poison_food");
        public static final TagKey<Item> WITHER_FOOD = createTag("wither_food");
        public static final TagKey<Item> HEALTH_BOOST_FOOD = createTag("health_boost_food");
        public static final TagKey<Item> ABSORPTION_FOOD = createTag("absorption_food");
        public static final TagKey<Item> GLOWING_FOOD = createTag("glowing_food");
        public static final TagKey<Item> LEVITATION_FOOD = createTag("levitation_food");
        public static final TagKey<Item> LUCK_FOOD = createTag("luck_food");
        public static final TagKey<Item> UNLUCK_FOOD = createTag("unluck_food");
        public static final TagKey<Item> SLOW_FALLING_FOOD = createTag("slow_falling_food");

        private static TagKey<Item> createTag (String name) {
            return ItemTags.create(Identifier.fromNamespaceAndPath(BrewNStew.MOD_ID, name));
        }
    }
}
