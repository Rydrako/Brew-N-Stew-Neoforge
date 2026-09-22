package rydrako.brewnstew.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.datacomponent.StatPoint;

import java.util.function.Consumer;

public class CookedFoodItem extends Item {
    public CookedFoodItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {

        for(var data : itemStack.getComponents().stream().filter(data -> data.type().toString().startsWith(BrewNStew.MOD_ID)).toList())
        {
            var effectName = "effect.minecraft." + data.type().toString().replace(BrewNStew.MOD_ID+":", "");
            StatPoint statPoint = (StatPoint) data.value();

            builder.accept(Component.literal("§8"+Component.translatable(effectName).getString()+" "+statPoint.getEffectLevel()));
        }

        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }
}
