package honeyedlemons.kinder.blocks;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.blocks.entities.OysterBlockEntity;
import honeyedlemons.kinder.init.KinderBlocks;
import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class OysterBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty COOKED = BooleanProperty.create("cooked");
    public static final BooleanProperty COOKING = BooleanProperty.create("cooking");


    protected static final VoxelShape BOTTOM_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public OysterBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(COOKED, false).setValue(COOKING, false));
    }

    @SuppressWarnings ("deprecation")
    @Override
    public VoxelShape getShape(net.minecraft.world.level.block.state.BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return BOTTOM_SHAPE;
    }

    @Override
    public RenderShape getRenderShape(net.minecraft.world.level.block.state.BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, net.minecraft.world.level.block.state.BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, WATERLOGGED, COOKED, COOKING);
    }

    @Override
    public net.minecraft.world.level.block.state.BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8);
    }

    @SuppressWarnings ("deprecation")
    public FluidState getFluidState(net.minecraft.world.level.block.state.BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @SuppressWarnings ("deprecation")
    public net.minecraft.world.level.block.state.BlockState updateShape(net.minecraft.world.level.block.state.BlockState state, Direction direction, net.minecraft.world.level.block.state.BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        return new OysterBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, net.minecraft.world.level.block.state.BlockState state, BlockEntityType<T> type) {
        return world.isClientSide ? null : createTickerHelper(type, KinderBlocks.OYSTER_BLOCK_ENTITY, OysterBlockEntity::tick);
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, net.minecraft.world.level.block.state.BlockState state, Player player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof OysterBlockEntity && state.getValue(WATERLOGGED) && state.getValue(COOKED) && !world.isClientSide) {
            Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), ((OysterBlockEntity) blockEntity).getPearl(world, pos));
        }
        super.playerWillDestroy(world, pos, state, player);
    }

    @SuppressWarnings ("deprecation")
    @Override
    public InteractionResult use(net.minecraft.world.level.block.state.BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (!(blockEntity instanceof OysterBlockEntity oyster)) {
            return super.use(state, world, pos, player, hand, hit);
        }

        boolean cooked = state.getValue(COOKED);
        boolean cooking = state.getValue(COOKING);
        boolean waterlogged = state.getValue(WATERLOGGED);

        if (!cooked && !cooking && waterlogged) {
            return handleWaterloggedState(world, pos, player, hand, itemStack, state);
        }

        if (cooked && itemStack.getItem() == KinderItems.PEARL_SHUCK) {
            return handlePearlShuck(world, pos, player, hand, oyster, state, itemStack);
        }

        if (player.isShiftKeyDown() && player.isCreative() && !cooked && cooking) {
            world.setBlockAndUpdate(pos, state.setValue(COOKING, false).setValue(COOKED, true));
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    private InteractionResult handleWaterloggedState(Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack itemStack, net.minecraft.world.level.block.state.BlockState state) {
        Item item = itemStack.getItem();
        if (item == KinderItems.WHITE_ESSENCE_BOTTLE) {
            world.setBlock(pos, state.setValue(COOKING, true), Block.UPDATE_ALL);
            itemStack.shrink(1);
            world.gameEvent(null, GameEvent.FLUID_PLACE, pos);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);

            handleEmptyItemStack(player, hand, itemStack);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        return InteractionResult.PASS;
    }

    private InteractionResult handlePearlShuck(Level world, BlockPos pos, Player player, InteractionHand hand, OysterBlockEntity oyster, net.minecraft.world.level.block.state.BlockState state, ItemStack itemStack) {
        float randomWeight = world.random.nextFloat();
        float luckWeight = player.getEffect(MobEffects.LUCK) != null ? 0.2f : 0f;
        float breakChance = oyster.getBreakChance();

        KinderMod.LOGGER.info("Random Weight is " + randomWeight + ", luck weight is " + luckWeight + ", break chance is " + breakChance);

        if (randomWeight <= (luckWeight + breakChance)) {
            oyster.setBreakChance(breakChance - 0.2f);
            world.setBlockAndUpdate(pos, state.setValue(COOKED, false));
        } else {
            world.destroyBlock(pos, true);
        }

        itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), oyster.getPearl(world, pos));
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    private void handleEmptyItemStack(Player player, InteractionHand hand, ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
        } else if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
            player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
        }
    }


    public void animateTick(net.minecraft.world.level.block.state.BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);
        if (state.getValue(COOKED).equals(true)) {
            world.addParticle(ParticleTypes.ELECTRIC_SPARK, pos.getX() + world.random.nextFloat(), pos.getY() + world.random.nextFloat() + 0.2, pos.getZ() + world.random.nextFloat(), world.random.nextFloat() / 3, world.random.nextFloat() / 3, world.random.nextFloat() / 3);
        }
    }
}