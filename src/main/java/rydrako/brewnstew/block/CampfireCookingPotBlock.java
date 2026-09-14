package rydrako.brewnstew.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import rydrako.brewnstew.block.entity.CookingPotBlockEntity;
import rydrako.brewnstew.tags.ModTags;

import javax.annotation.Nullable;

public class CampfireCookingPotBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final VoxelShape SHAPE_INSIDE = Block.column((double)12.0F, (double)8.0F, (double)16.0F);
    protected static final VoxelShape SHAPE = Util.make(() ->
            Shapes.join(Shapes.block(), Shapes.or(Block.column((double)16.0F, (double)14.0F, (double)16.0F), SHAPE_INSIDE), BooleanOp.ONLY_FIRST));

    public static final MapCodec<CampfireCookingPotBlock> CODEC = simpleCodec(CampfireCookingPotBlock::new);

    public CampfireCookingPotBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.WEST)
                .setValue(LIT, true)
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(LIT);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            if (random.nextInt(10) == 0) {
                level.playLocalSound((double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
            }

            if (random.nextInt(5) == 0) {
                for(int i = 0; i < random.nextInt(1) + 1; ++i) {
                    level.addParticle(ParticleTypes.LAVA, (double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, (double)(random.nextFloat() / 2.0F), 5.0E-5, (double)(random.nextFloat() / 2.0F));
                }
            }

            if (random.nextFloat() < 0.5F) {
                for(int i = 0; i < random.nextInt(2) + 2; ++i) {
                    makeParticles(level, pos, false, true, 0.001);
                }
            }
        }

    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
//        BlockEntity var9 = level.getBlockEntity(pos);
//        if (var9 instanceof CampfireBlockEntity campfire) {
//            ItemStack itemInHand = player.getItemInHand(hand);
//            if (itemInHand.getItem().getClass().isAssignableFrom(ShovelItem.class)) {
//                if (level instanceof ServerLevel) {
//                    ServerLevel serverLevel = (ServerLevel)level;
//                    if (campfire.placeFood(serverLevel, player, itemInHand)) {
//                        player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
//                        return InteractionResult.SUCCESS_SERVER;
//                    }
//                }
//
//                return InteractionResult.CONSUME;
//            }
//        }

        ItemStack itemInHand = player.getItemInHand(hand);
        if (itemInHand.getItem() instanceof ShovelItem) {
//            player.sendSystemMessage(Component.literal("test"));
            if(state.getValue(LIT))
            {
                dowse(player, level, pos, state);
                itemInHand.hurtAndBreak(1, player, hand);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.CONSUME;
        }
        else if(itemInHand.getItem() instanceof FlintAndSteelItem)
        {
            if(!state.getValue(LIT))
            {
                light(player, level, pos, state);
                itemInHand.hurtAndBreak(1, player, hand);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.CONSUME;
        }

        if(level.getBlockEntity(pos) instanceof  CookingPotBlockEntity cookingPotBlockEntity)
        {
            boolean isEmpty = cookingPotBlockEntity.inventory.getResource(0).isEmpty();

            //insert
            if(isEmpty && !itemStack.isEmpty() && itemStack.is(ModTags.Items.COOKABLE_FOOD))
            {
//                player.addEffect(new Mob)
                cookingPotBlockEntity.inventory.set(0, ItemResource.of(itemStack), 1);
                itemStack.shrink(1);
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            }
            else if(!isEmpty) {
                ItemStack stack = cookingPotBlockEntity.inventory.getResource(0).toStack();
                cookingPotBlockEntity.clearContents();

                if(!player.getInventory().add(stack))
                {
                    player.drop(stack, false);
                }

                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
//        if(state.getValue(LIT))
//        {
//            effectApplier.apply(InsideBlockEffectType.CLEAR_FREEZE);
//            effectApplier.apply(InsideBlockEffectType.LAVA_IGNITE);
//            effectApplier.runAfter(InsideBlockEffectType.LAVA_IGNITE, Entity::lavaHurt);
//        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState onState, Entity entity) {
        if (onState.getValue(LIT) && entity instanceof LivingEntity) {
            entity.hurt(level.damageSources().hotFloor(), 1.0F);
        }

        super.stepOn(level, pos, onState, entity);
    }

    public static void dowse(@Nullable Entity source, Level level, BlockPos pos, BlockState state) {

        if (level.isClientSide()) {
            for(int j = 0; j < 20; ++j) {
                makeParticles(level, pos,false, true, 0.005);
            }
        }
        else {
            level.setBlockAndUpdate(pos, state.setValue(LIT, false));
            level.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        level.gameEvent(source, GameEvent.BLOCK_CHANGE, pos);
    }

    public static void light (@Nullable Entity source, Level level, BlockPos pos, BlockState state) {

        if(!level.isClientSide())
        {
            level.setBlockAndUpdate(pos, state.setValue(LIT, true));
            level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        level.gameEvent(source, GameEvent.BLOCK_CHANGE, pos);

    }

    public static void makeParticles(Level level, BlockPos pos, boolean isSignalFire, boolean smoking, double speed) {
        RandomSource random = level.getRandom();
        SimpleParticleType smokeParticle = isSignalFire ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        level.addAlwaysVisibleParticle(smokeParticle, true, (double)pos.getX() + (double)0.5F + random.nextDouble() / (double)3.0F * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + (double)0.5F + random.nextDouble() / (double)3.0F * (double)(random.nextBoolean() ? 1 : -1), (double)0.0F, 0.07, (double)0.0F);
        if (smoking) {
            level.addParticle(ParticleTypes.SMOKE,
                    (double)pos.getX() + (double)0.5F + random.nextDouble() / (double)4.0F * (double)(random.nextBoolean() ? 1 : -1),
                    (double)pos.getY() + 0.4,
                    (double)pos.getZ() + (double)0.5F + random.nextDouble() / (double)4.0F * (double)(random.nextBoolean() ? 1 : -1),
                    (double)0.0F,
                    speed,
                    (double)0.0F);
        }

    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        boolean replacedWater = level.getFluidState(pos).is(Fluids.WATER);
        return this.defaultBlockState().setValue(LIT, !replacedWater).setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getInteractionShape(final BlockState state, final BlockGetter level, final BlockPos pos) {
        return SHAPE_INSIDE;
    }

    private boolean isValidItem (ItemStack item) {
        return true;
    }

    @Override
    public @org.jspecify.annotations.Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CookingPotBlockEntity(blockPos, blockState);
    }

//    @Override
//    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, ItemStack toolStack, boolean willHarvest, FluidState fluid) {
//        return super.onDestroyedByPlayer(state, level, pos, player, toolStack, willHarvest, fluid);
//    }
}
