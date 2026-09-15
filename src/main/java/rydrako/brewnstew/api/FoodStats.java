package rydrako.brewnstew.api;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jspecify.annotations.NonNull;
import rydrako.brewnstew.tags.ModTags;

import java.util.Map;

public class FoodStats {

//    static Map<TagKey<Item>, IntegerProperty> tagsToStates = Map.of(
//            ModTags.Items.STRENGTH_FOOD, STRENGTH,
//            ModTags.Items.NIGHT_VISION_FOOD, NIGHT_VISION
//            ModTags.Items.LEVITATION_FOOD, LEVITATION,
//            ModTags.Items.WATER_BREATHING_FOOD, WATER_BREATHING,
//            ModTags.Items.JUMP_BOOST_FOOD, JUMP_BOOST
//    );

//    private static BlockState updateFoodStats(BlockState state, ItemStack itemStack, Player player) {
//
//        for(TagKey<Item> tag : itemStack.tags().toList())
//        {
//            var property = tagsToStates.get(tag);
//            if(property != null)
//            {
//                player.sendSystemMessage(Component.literal("Found Stat " + tag));
//                state = state.setValue(property, state.getValue(property) + 1);
//            }
//
//        }
//        return state;
//    }
}
