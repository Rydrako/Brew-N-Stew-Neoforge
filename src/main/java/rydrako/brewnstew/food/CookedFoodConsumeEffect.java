package rydrako.brewnstew.food;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.api.CookedConsumableEffects;
import rydrako.brewnstew.api.FoodStats;
import rydrako.brewnstew.api.StatPoint;
import rydrako.brewnstew.datacomponent.ModDataComponents;

public class CookedFoodConsumeEffect implements ConsumeEffect {
    public static final MapCodec<CookedFoodConsumeEffect> CODEC = MapCodec.unit(CookedFoodConsumeEffect::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, CookedFoodConsumeEffect> STREAM_CODEC =
            StreamCodec.unit(new CookedFoodConsumeEffect());

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return ModConsumeEffects.CUSTOM_CONSUME_EFFECT.get();
    }

    @Override
    public boolean apply(Level level, ItemStack itemStack, LivingEntity livingEntity) {
        if(!level.isClientSide() && livingEntity instanceof Player player) {

            for(var dataComponent : itemStack.getComponents().stream()
                    .filter(data -> data.type().toString().startsWith(BrewNStew.MOD_ID))
                    .toList())
            {
                String path = dataComponent.type().toString().replace(BrewNStew.MOD_ID + ":", "");
//                var effect = CookedConsumableEffects.EFFECT_REGISTRY.getValue(Identifier.fromNamespaceAndPath(BrewNStew.MOD_ID,
//                        path));

                var effect = BuiltInRegistries.MOB_EFFECT.get(Identifier.fromNamespaceAndPath("minecraft",path));

                if(effect.isPresent())
                {
                    var stat = (StatPoint)dataComponent.value();
                    livingEntity.addEffect(new MobEffectInstance(effect.get(), 100, stat.points()-1));
                }
//
//                if(effect != null)
//                {
//                    var stat = itemStack.get((DataComponentType<StatPoint>)dataComponent.value());
////                    effect.getEffect(stat.points());
//
//                }

                player.sendSystemMessage(Component.literal(path));
            }


        }
        return true;
    }

}
