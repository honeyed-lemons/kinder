package honeyedlemons.kinder.blocks;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.blocks.entities.IncubatorBlockEntity;
import honeyedlemons.kinder.init.KinderBlocks;
import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class IncubatorBlock extends BaseEntityBlock {

    public static final BooleanProperty COOKED = BooleanProperty.create("cooked");
    public static final BooleanProperty COOKING = BooleanProperty.create("cooking");
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    protected static final VoxelShape BOTTOM_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    protected static final VoxelShape TOP_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 13.0, 12.0);

    public IncubatorBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(COOKED, false).setValue(COOKING, false).setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER).setValue(LIT, false));
    }

    public static HashMap<Integer, Item> essenceHash() {
        HashMap<Integer, Item> hash = new HashMap<>();
        hash.put(1, KinderItems.WHITE_ESSENCE_BOTTLE);
        hash.put(2, KinderItems.YELLOW_ESSENCE_BOTTLE);
        hash.put(3, KinderItems.BLUE_ESSENCE_BOTTLE);
        hash.put(4, KinderItems.PINK_ESSENCE_BOTTLE);
        return hash;
    }

    protected static void onBreakInCreative(Level world, BlockPos pos, net.minecraft.world.level.block.state.BlockState state, Player player) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
            BlockPos blockPos = pos.below();
            net.minecraft.world.level.block.state.BlockState blockState = world.getBlockState(blockPos);
            if (blockState.is(state.getBlock()) && blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                net.minecraft.world.level.block.state.BlockState blockState2 = blockState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                world.setBlock(blockPos, blockState2, Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS);
                world.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, Block.getId(blockState));
            }
        }

    }

    public static void explode(Level world, BlockPos pos) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        world.explode(null, x, y, z, 3.0F, Level.ExplosionInteraction.BLOCK);
    }

    public static int combineEssences(int essence1, int essence2) {
        if (essence1 == essence2) {
            return essence1;
        } else if ((essence1 == 2 && essence2 == 3) || (essence1 == 3 && essence2 == 2)) {
            return 5; // Yellow Blue
        } else if ((essence1 == 2 && essence2 == 4) || (essence1 == 4 && essence2 == 2)) {
            return 6; // Yellow Pink
        } else if ((essence1 == 4 && essence2 == 3) || (essence1 == 3 && essence2 == 4)) {
            return 7; // Pink Blue
        } else {
            return Integer.max(essence1, essence2);
        }
    }

    public static void FlushEssence(net.minecraft.world.level.block.state.BlockState state, Level world, BlockPos pos) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof IncubatorBlockEntity) {
            ((IncubatorBlockEntity) be).setEssenceSlot(0, 0);
            ((IncubatorBlockEntity) be).setEssenceSlot(1, 0);
        }
        world.setBlockAndUpdate(pos, state.setValue(COOKING, false).setValue(COOKED, false));
    }

    @Override
    @SuppressWarnings ("deprecation")
    public VoxelShape getShape(net.minecraft.world.level.block.state.BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF).equals(DoubleBlockHalf.LOWER)) {
            return BOTTOM_SHAPE;
        } else {
            return TOP_SHAPE;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, net.minecraft.world.level.block.state.BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, COOKED, COOKING, HALF, LIT);
    }

    @Override
    public RenderShape getRenderShape(net.minecraft.world.level.block.state.BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, net.minecraft.world.level.block.state.BlockState state, BlockEntityType<T> type) {
        return world.isClientSide ? createTickerHelper(type, KinderBlocks.INCUBATOR_BLOCK_ENTITY, IncubatorBlockEntity::clientTick) : createTickerHelper(type, KinderBlocks.INCUBATOR_BLOCK_ENTITY, IncubatorBlockEntity::serverTick);
    }

    @Nullable
    public net.minecraft.world.level.block.state.BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos blockPos = ctx.getClickedPos();
        Level world = ctx.getLevel();
        if (blockPos.getY() < world.getMaxBuildHeight() - 1 && world.getBlockState(blockPos.above()).canBeReplaced(ctx)) {
            return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection()).setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    public void setPlacedBy(Level world, BlockPos pos, net.minecraft.world.level.block.state.BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), Block.UPDATE_ALL);
    }

    @SuppressWarnings ("deprecation")
    public boolean canSurvive(net.minecraft.world.level.block.state.BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.below();
        net.minecraft.world.level.block.state.BlockState blockState = world.getBlockState(blockPos);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockState.isFaceSturdy(world, blockPos, Direction.UP) : blockState.is(this);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        if (state.getValue(HALF).equals(DoubleBlockHalf.LOWER)) {
            return new IncubatorBlockEntity(pos, state);
        } else {
            return null;
        }
    }

    public void playerWillDestroy(Level world, BlockPos pos, net.minecraft.world.level.block.state.BlockState state, Player player) {
        if (!world.isClientSide && player.isCreative()) {
            onBreakInCreative(world, pos, state, player);
        }

        super.playerWillDestroy(world, pos, state, player);
    }

    @SuppressWarnings ("deprecation")
    public net.minecraft.world.level.block.state.BlockState updateShape(net.minecraft.world.level.block.state.BlockState state, Direction direction, net.minecraft.world.level.block.state.BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP) && (!neighborState.is(this) || neighborState.getValue(HALF) == doubleBlockHalf)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    @SuppressWarnings ("deprecation")
    public InteractionResult use(net.minecraft.world.level.block.state.BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof IncubatorBlockEntity incubator)) {
            return super.use(state, world, pos, player, hand, hit);
        }

        ItemStack itemStack = player.getItemInHand(hand);

        if (hand != InteractionHand.MAIN_HAND || !state.getValue(HALF).equals(DoubleBlockHalf.LOWER)) {
            return super.use(state, world, pos, player, hand, hit);
        }

        boolean isCooking = state.getValue(COOKING);
        boolean isCooked = state.getValue(COOKED);

        if (!isCooking && !isCooked) {
            return handleNotCookingState(state, world, pos, player, hand, itemStack, incubator);
        }

        if (isCooking && !isCooked && player.isCreative() && player.isDiscrete()) {
            world.setBlockAndUpdate(pos, state.setValue(COOKED, true).setValue(COOKING, false));
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        if (isCooked) {
            world.scheduleTick(pos, this, 1);
            world.setBlockAndUpdate(pos.above(), world.getBlockState(pos.above()).setValue(LIT, false));
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    private InteractionResult handleNotCookingState(net.minecraft.world.level.block.state.BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack itemStack, IncubatorBlockEntity incubator) {
        if (incubator.essenceSlot2 != 0 && incubator.essenceSlot1 != 0 && itemStack.isEmpty()) {
            startCooking(state, world, pos);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        if (itemStack.getItem() == Items.GLASS_BOTTLE && incubator.essenceSlot1 != 0) {
            KinderMod.LOGGER.info("Draining");
            drainEssence(incubator, world, player, hand);
            world.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        for (Map.Entry<Integer, Item> map : essenceHash().entrySet()) {
            if (itemStack.getItem() == map.getValue().asItem()) {
                KinderMod.LOGGER.info("Knows you're holding essence");
                if (incubator.essenceSlot1 == 0 || incubator.essenceSlot2 == 0) {
                    fillEssence(incubator, map.getKey());
                    itemStack.shrink(1);
                    world.gameEvent(null, GameEvent.FLUID_PLACE, pos);
                    world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                    handleEmptyItemStack(player, hand, itemStack);
                    return InteractionResult.sidedSuccess(world.isClientSide);
                }
            }
        }
        return InteractionResult.PASS;
    }

    private void startCooking(net.minecraft.world.level.block.state.BlockState state, Level world, BlockPos pos) {
        world.setBlockAndUpdate(pos, state.setValue(COOKING, true));
        world.setBlockAndUpdate(pos.above(), world.getBlockState(pos.above()).setValue(LIT, true));
    }

    private void handleEmptyItemStack(Player player, InteractionHand hand, ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
        } else if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
            player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
        }
    }

    @SuppressWarnings ("deprecation")
    public void tick(net.minecraft.world.level.block.state.BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof IncubatorBlockEntity) {
            ItemStack is = IncubatorBlockEntity.GemItemStack(world, pos, ((IncubatorBlockEntity) be));
            if (is != null) {
                Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), is);
                FlushEssence(state, world, pos);
            }
        }
    }

    public void fillEssence(IncubatorBlockEntity be, int essencetofill) {
        if (be.essenceSlot1 != 0) {
            be.setEssenceSlot(1, essencetofill);
        } else {
            be.setEssenceSlot(0, essencetofill);
        }
    }

    public void drainEssence(IncubatorBlockEntity be, Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        int essenceSlot = be.essenceSlot2 != 0 ? 2 : (be.essenceSlot1 != 0 ? 1 : 0);

        if (essenceSlot != 0) {
            itemStack.shrink(1);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);

            Item essenceBottle = essenceHash().get(essenceSlot == 2 ? be.essenceSlot2 : be.essenceSlot1);
            ItemStack essenceStack = new ItemStack(essenceBottle);

            if (itemStack.isEmpty()) {
                player.setItemInHand(hand, essenceStack);
            } else if (!player.getInventory().add(essenceStack)) {
                player.drop(essenceStack, false);
            }

            be.setEssenceSlot(essenceSlot - 1, 0);
        }
    }

}
