package rydrako.brewnstew.food;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

public class FailedFoodConsumeEffect implements ConsumeEffect {
    public static final MapCodec<CookedFoodConsumeEffect> CODEC = MapCodec.unit(CookedFoodConsumeEffect::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, CookedFoodConsumeEffect> STREAM_CODEC =
            StreamCodec.unit(new CookedFoodConsumeEffect());

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return ModConsumeEffects.FAILED_FOOD_CONSUME_EFFECT.get();
    }

    @Override
    public boolean apply(Level level, ItemStack itemStack, LivingEntity livingEntity) {
        if(!level.isClientSide()) {
            var effect = BuiltInRegistries.MOB_EFFECT.getRandom(RandomSource.create());
            livingEntity.addEffect(new MobEffectInstance(effect.get(), 5000));
        }
        return true;
    }
}
