package rydrako.brewnstew.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import rydrako.brewnstew.tags.ModTags;

import java.util.Hashtable;
import java.util.Map;

public record FoodStats (int nutrition, int saturation, int strength, int speed, int nightVision, int jumpBoost,
                         int levitation, int waterBreathing){

    public static final Codec<FoodStats> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("nutrition").forGetter(FoodStats::nutrition),
                    Codec.INT.fieldOf("saturation").forGetter(FoodStats::saturation),
                    Codec.INT.fieldOf("strength").forGetter(FoodStats::strength),
                    Codec.INT.fieldOf("speed").forGetter(FoodStats::speed),
                    Codec.INT.fieldOf("nightVision").forGetter(FoodStats::nightVision),
                    Codec.INT.fieldOf("jumpBoost").forGetter(FoodStats::jumpBoost),
                    Codec.INT.fieldOf("levitation").forGetter(FoodStats::levitation),
                    Codec.INT.fieldOf("waterBreathing").forGetter(FoodStats::waterBreathing)
            ).apply(instance, FoodStats::new)
    );
    public static final StreamCodec<ByteBuf, FoodStats> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, FoodStats::nutrition,
            ByteBufCodecs.INT, FoodStats::saturation,
            ByteBufCodecs.INT, FoodStats::strength,
            ByteBufCodecs.INT, FoodStats::speed,
            ByteBufCodecs.INT, FoodStats::nightVision,
            ByteBufCodecs.INT, FoodStats::jumpBoost,
            ByteBufCodecs.INT, FoodStats::levitation,
            ByteBufCodecs.INT, FoodStats::waterBreathing,
            FoodStats::new
    );

    public static final class Builder {
        int nutrition;
        int saturation;
        int strength;
        int speed;
        int nightVision;
        int jumpBoost;
        int levitation;
        int waterBreathing;

        public Builder () {
        }

        public Builder nutrition (int nutrition) {
            this.nutrition = nutrition;
            return this;
        }

        public Builder saturation (int saturation) {
            this.saturation = saturation;
            return this;
        }

        public Builder addStat (TagKey<Item> tag, int amount) {

            if(tag == ModTags.Items.STRENGTH_FOOD) {
                this.strength += amount;
            }
            if(tag == ModTags.Items.SPEED_FOOD) {
                this.speed += amount;
            }
            if(tag == ModTags.Items.NIGHT_VISION_FOOD) {
                this.nightVision += amount;
            }
            if(tag == ModTags.Items.JUMP_BOOST_FOOD) {
                this.jumpBoost += amount;
            }
            if(tag == ModTags.Items.LEVITATION_FOOD) {
                this.levitation += amount;
            }
            if(tag == ModTags.Items.WATER_BREATHING_FOOD) {
                this.waterBreathing += amount;
            }
            return this;
        }

        public FoodStats build () {
            return new FoodStats(nutrition, saturation, strength, speed, nightVision, jumpBoost, levitation, waterBreathing);
        }
    }

}
