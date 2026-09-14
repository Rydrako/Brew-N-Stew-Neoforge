package rydrako.brewnstew.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import rydrako.brewnstew.block.CampfireCookingPotBlock;
import rydrako.brewnstew.block.ModBlocks;

public class CookingPotItem extends Item {
    public CookingPotItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos posClicked = context.getClickedPos();
        Player player = context.getPlayer();

        if(!level.isClientSide())
        {
            BlockState clickedState = level.getBlockState(posClicked);
            if(clickedState.getBlock() instanceof CampfireBlock)
            {
                BlockState newState = ModBlocks.CAMPFIRE_COOKING_POT.get().defaultBlockState()
                        .setValue(CampfireCookingPotBlock.FACING, clickedState.getValue(CampfireBlock.FACING))
                        .setValue(CampfireCookingPotBlock.LIT, clickedState.getValue(CampfireBlock.LIT));

                clickedState.setValue(CampfireBlock.FACING, Direction.SOUTH);
                level.setBlockAndUpdate(posClicked, newState);
                level.playSound(null, posClicked, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);

                context.getItemInHand().consume(1, player);
                return InteractionResult.SUCCESS_SERVER;
            }
        }

        return super.useOn(context);
    }
}
