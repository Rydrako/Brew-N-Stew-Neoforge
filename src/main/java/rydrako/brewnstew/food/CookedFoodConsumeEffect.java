package rydrako.brewnstew.food;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.datacomponent.StatPoint;

public class CookedFoodConsumeEffect implements ConsumeEffect {
    public static final MapCodec<CookedFoodConsumeEffect> CODEC = MapCodec.unit(CookedFoodConsumeEffect::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, CookedFoodConsumeEffect> STREAM_CODEC =
            StreamCodec.unit(new CookedFoodConsumeEffect());

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return ModConsumeEffects.COOKED_FOOD_CONSUME_EFFECT.get();
    }

    @Override
    public boolean apply(Level level, ItemStack itemStack, LivingEntity livingEntity) {
        if(!level.isClientSide()) {
            for(var dataComponent : itemStack.getComponents().stream()
                    .filter(data -> data.type().toString().startsWith(BrewNStew.MOD_ID))
                    .toList())
            {
                //TODO: support for custom effects
                String path = dataComponent.type().toString().replace(BrewNStew.MOD_ID + ":", "");
                var effect = BuiltInRegistries.MOB_EFFECT.get(Identifier.fromNamespaceAndPath("minecraft",path));

                if(effect.isPresent())
                {
                    var stat = (StatPoint)dataComponent.value();
                    livingEntity.addEffect(new MobEffectInstance(effect.get(), 100, stat.points()-1));
                }
            }
        }
        return true;
    }
}
