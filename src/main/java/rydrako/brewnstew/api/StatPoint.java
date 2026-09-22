package rydrako.brewnstew.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record StatPoint(int points) {

    public static final Codec<StatPoint> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("points").forGetter(StatPoint::points)
            ).apply(instance, StatPoint::new)
    );
    public static final StreamCodec<ByteBuf, StatPoint> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, StatPoint::points,
            StatPoint::new
    );

    public StatPoint addPoints (int points) {
        return new StatPoint(points() + points);
    }

    public String getEffectLevel () {
        return Component.translatable("enchantment.level."+points).getString();
    }
}
