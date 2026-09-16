package rydrako.brewnstew.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.transfer.item.ItemResource;
import rydrako.brewnstew.api.FoodStats;
import rydrako.brewnstew.block.entity.CookingPotBlockEntity;
import rydrako.brewnstew.tags.ModTags;

import javax.annotation.Nullable;
import java.util.*;

public class CampfireCookingPotBlock extends BaseEntityBlock {
    public static final int MAX_FOOD = 9;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    //TODO: FoodStats implementation in BlockEntity
    public static final IntegerProperty FOOD = IntegerProperty.create("food", 0, MAX_FOOD);
    public static final IntegerProperty STRENGTH = IntegerProperty.create("strength", 0, MAX_FOOD);
    public static final IntegerProperty NIGHT_VISION = IntegerProperty.create("night_vision", 0, MAX_FOOD);

    private static final VoxelShape SHAPE_INSIDE = Block.column((double)12.0F, (double)8.0F, (double)16.0F);
    protected static final VoxelShape SHAPE = Util.make(() ->
            Shapes.join(Shapes.block(), Shapes.or(Block.column((double)16.0F, (double)14.0F, (double)16.0F), SHAPE_INSIDE), BooleanOp.ONLY_FIRST));

    public static final MapCodec<CampfireCookingPotBlock> CODEC = simpleCodec(CampfireCookingPotBlock::new);

    public CampfireCookingPotBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.WEST)
                .setValue(LIT, true)
                .setValue(FOOD, 0)
                .setValue(STRENGTH, 0)
                .setValue(NIGHT_VISION, 0)
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
        builder.add(FOOD);
        builder.add(STRENGTH);
        builder.add(NIGHT_VISION);
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

        ItemStack itemInHand = player.getItemInHand(hand);
        if (itemInHand.getItem() instanceof ShovelItem) {
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

        if(level.getBlockEntity(pos) instanceof  CookingPotBlockEntity cookingPotBlockEntity && !level.isClientSide())
        {
            if(itemStack.isEmpty() && player.isCrouching())
            {
                player.sendSystemMessage(Component.literal(cookingPotBlockEntity.printFoodStats()));
            }
            else
            {
                insertItem(itemStack, state, level, pos, cookingPotBlockEntity);
            }
        }

        return InteractionResult.SUCCESS;
    }

    private static boolean insertItem(ItemStack itemStack, BlockState state, Level level, BlockPos pos, CookingPotBlockEntity cookingPotBlockEntity) {
        var inv = cookingPotBlockEntity.inventory;


        if(!cookingPotBlockEntity.isFull() && inv.isValid(0, ItemResource.of(itemStack)) && state.getValue(FOOD) < MAX_FOOD)
        {
            inv.set(cookingPotBlockEntity.getEmptySlot(), ItemResource.of(itemStack), 1);
            itemStack.shrink(1);

            if(!level.isClientSide()) {
                BlockState newState = state.setValue(FOOD, state.getValue(FOOD) + 1);

                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
                level.setBlockAndUpdate(pos, newState);
            }
            return true;
        }
        return false;
    }

    static Map<TagKey<Item>, IntegerProperty> tagsToStates = Map.of(
            ModTags.Items.STRENGTH_FOOD, STRENGTH,
            ModTags.Items.NIGHT_VISION_FOOD, NIGHT_VISION
//            ModTags.Items.LEVITATION_FOOD, LEVITATION,
//            ModTags.Items.WATER_BREATHING_FOOD, WATER_BREATHING,
//            ModTags.Items.JUMP_BOOST_FOOD, JUMP_BOOST
    );

    private static BlockState updateFoodStats(BlockState state, ItemStack itemStack, Player player) {

        for(TagKey<Item> tag : itemStack.tags().toList())
        {
            var property = tagsToStates.get(tag);
            if(property != null)
            {
//                player.sendSystemMessage(Component.literal("Found Stat " + tag));
                state = state.setValue(property, state.getValue(property) + 1);
            }

        }
        return state;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
//        if(state.getValue(LIT))
//        {
//            effectApplier.apply(InsideBlockEffectType.CLEAR_FREEZE);
//            effectApplier.apply(InsideBlockEffectType.LAVA_IGNITE);
//            effectApplier.runAfter(InsideBlockEffectType.LAVA_IGNITE, Entity::lavaHurt);
//        }
        if(state.getValue(LIT) && entity instanceof ItemEntity itemEntity)
        {
            if(insertItem(itemEntity.getItem(), state, level, pos, (CookingPotBlockEntity) level.getBlockEntity(pos)))
                effectApplier.runAfter(InsideBlockEffectType.LAVA_IGNITE, Entity::lavaHurt);
        }
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

    @Override
    public @org.jspecify.annotations.Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CookingPotBlockEntity(blockPos, blockState);
    }

//    @Override
//    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, ItemStack toolStack, boolean willHarvest, FluidState fluid) {
//        return super.onDestroyedByPlayer(state, level, pos, player, toolStack, willHarvest, fluid);
//    }
}
