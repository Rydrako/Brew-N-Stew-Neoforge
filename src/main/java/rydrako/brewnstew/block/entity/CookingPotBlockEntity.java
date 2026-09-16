package rydrako.brewnstew.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import org.jspecify.annotations.Nullable;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.api.FoodStats;
import rydrako.brewnstew.tags.ModTags;

import java.util.*;

public class CookingPotBlockEntity extends BlockEntity {
    public final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(9) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            super.onContentsChanged(index, previousContents);
            CookingPotBlockEntity.this.setChanged();

            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        protected int getCapacity(int index, ItemResource resource) {
            return 1;
        }

        @Override
        public boolean isValid(int index, ItemResource resource) {
            return resource.is(ModTags.Items.COOKABLE_FOOD);
        }
    };

    public CookingPotBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.COOKING_POT_BE.get(), worldPosition, blockState);
    }

    public void clearContents () {
        for(int i = 0; i < inventory.size(); i++)
        {
            inventory.set(i, ItemResource.EMPTY, 0);
        }
    }

    public boolean isFull () {
        for(int i = 0; i < inventory.size(); i++)
        {
            if(inventory.getResource(i).isEmpty())
                return false;
        }
        return true;
    }

    public int getEmptySlot () {
        for(int i = 0; i < inventory.size(); i++)
        {
            if(inventory.getResource(i).isEmpty())
                return i;
        }
        return -1;
    }

    static Map<TagKey<Item>, String> stat = Map.of(
            ModTags.Items.STRENGTH_FOOD, "atk",
            ModTags.Items.NIGHT_VISION_FOOD, "nvsn",
            ModTags.Items.LEVITATION_FOOD, "levi",
            ModTags.Items.WATER_BREATHING_FOOD, "wtrb",
            ModTags.Items.JUMP_BOOST_FOOD, "jmp"
    );

    public String printFoodStats () {
        StringBuilder output = new StringBuilder();

        Map<TagKey<Item>, Integer> stats = new Hashtable<>();

        for(int i = 0; i < inventory.size(); i++)
        {
            inventory.getResource(i).tags()
                    .filter(tag -> Objects.equals(tag.location().getNamespace(), BrewNStew.MOD_ID))
                    .forEach(tag -> stats.merge(tag, 1, Integer::sum));
        }

        var keyList = stats.keySet().toArray();

        for (var key : keyList) {
            output.append(((TagKey<Item>)key).location().getPath().replace("_food", "") + ": " + stats.get(key) + "\n");
        }

        return output.toString();
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putChild("inventory", inventory);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        input.child("inventory").ifPresent(inventory::deserialize);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
