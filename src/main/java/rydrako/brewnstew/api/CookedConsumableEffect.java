package rydrako.brewnstew.api;

import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;

public record CookedConsumableEffect(TagKey<Item> tag, Holder<MobEffect> effect, int durationPerPoint){

    public MobEffectInstance getEffect (int statPoints)
    {
        return new MobEffectInstance(effect, statPoints * durationPerPoint);
    }
}
