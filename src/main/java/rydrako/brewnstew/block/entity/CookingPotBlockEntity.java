package rydrako.brewnstew.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import org.jspecify.annotations.Nullable;
import rydrako.brewnstew.BrewNStew;
import rydrako.brewnstew.datacomponent.StatPoint;
import rydrako.brewnstew.init.ModBlockEntities;
import rydrako.brewnstew.init.ModItems;
import rydrako.brewnstew.init.ModRecipes;
import rydrako.brewnstew.recipe.CookingPotRecipe;
import rydrako.brewnstew.recipe.CookingPotRecipeInput;
import rydrako.brewnstew.tags.ModTags;

import java.util.Optional;

public class CookingPotBlockEntity extends BlockEntity {

    public final Item DEFAULT_FOOD = ModItems.ROCK_HARD_FOOD.get();
    public enum RecipeStatus
    {
        Invalid,
        InvalidHolder,
        Valid
    }

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

    public boolean hasContents () {
        for(int i = 0; i < inventory.size(); i++)
        {
            if(!inventory.getResource(i).isEmpty())
                return true;
        }
        return false;
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

    private Optional<RecipeHolder<CookingPotRecipe>> getCurrentRecipe(ItemStack inputItem) {
        return ((ServerLevel) level).recipeAccess()
                .getRecipeFor(ModRecipes.COOKING_TYPE.get(),
                        new CookingPotRecipeInput(inventory.copyToList(), inputItem), level);
    }

    public boolean hasValidRecipe () {
        Optional<RecipeHolder<CookingPotRecipe>> recipe = getCurrentRecipe(null);
        return !recipe.isEmpty();
    }

    public boolean isValidHolderItem (ItemStack holderItem) {
        Optional<RecipeHolder<CookingPotRecipe>> recipe = getCurrentRecipe(holderItem);
        if(!recipe.isEmpty())
            return recipe.get().value().holderMatches(holderItem);

        return holderItem.is(ModTags.Items.FOOD_HOLDER);
    }

    public Component getHolderItemNameForCurrentRecipe () {
        Optional<RecipeHolder<CookingPotRecipe>> recipe = getCurrentRecipe(null);
        if(recipe.isEmpty() || recipe.get().value().holderItem() == null)
            return null;

        var item = recipe.get().value().holderItem().getValues().get(0).value().asItem();
        return item.getName(new ItemStack(item));
    }

    public ItemStack createCookedFoodItem (ItemStack inputItem) {
        Optional<RecipeHolder<CookingPotRecipe>> recipe = getCurrentRecipe(inputItem);
        if(recipe.isEmpty())
            return new ItemStack(DEFAULT_FOOD);

        ItemStack output = recipe.get().value().assemble(new CookingPotRecipeInput(inventory.copyToList(), inputItem));

        for(int i = 0; i < inventory.size(); i++)
        {
            for(var tag : inventory.getResource(i).tags().filter(data -> data.location().getNamespace().equals(BrewNStew.MOD_ID)).toList())
            {
                String path = tag.location().getPath().replace("_food", "");

                var dataComponent = BuiltInRegistries.DATA_COMPONENT_TYPE.getValue(Identifier.fromNamespaceAndPath(BrewNStew.MOD_ID, path));

                if(dataComponent != null)
                {
                    output.update((DataComponentType<StatPoint>)dataComponent,
                            new StatPoint(0),
                            stat -> stat.addPoints(1));
                }
            }
        }

        return output;
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
