package honeyedlemons.kinder.blocks;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.blocks.entities.IncubatorBlockEntity;
import honeyedlemons.kinder.init.KinderBlocks;
import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class IncubatorBlock extends BlockWithEntity {

    public static final BooleanProperty COOKED = BooleanProperty.of("cooked");
    public static final BooleanProperty COOKING = BooleanProperty.of("cooking");
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty LIT = BooleanProperty.of("lit");
    protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 13.0, 12.0);

    public IncubatorBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(COOKED, false).with(COOKING, false).with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.LOWER).with(LIT, false));
    }

    public static HashMap<Integer, Item> essenceHash() {
        HashMap<Integer, Item> hash = new HashMap<>();
        hash.put(1, KinderItems.WHITE_ESSENCE_BOTTLE);
        hash.put(2, KinderItems.YELLOW_ESSENCE_BOTTLE);
        hash.put(3, KinderItems.BLUE_ESSENCE_BOTTLE);
        hash.put(4, KinderItems.PINK_ESSENCE_BOTTLE);
        return hash;
    }

    protected static void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
            BlockPos blockPos = pos.down();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(state.getBlock()) && blockState.get(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockState2 = blockState.getFluidState().isOf(Fluids.WATER) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
                world.setBlockState(blockPos, blockState2, Block.NOTIFY_ALL | Block.SKIP_DROPS);
                world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, blockPos, Block.getRawIdFromState(blockState));
            }
        }

    }

    public static void explode(World world, BlockPos pos) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        world.createExplosion(null, x, y, z, 3.0F, World.ExplosionSourceType.BLOCK);
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

    public static void FlushEssence(BlockState state, World world, BlockPos pos) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof IncubatorBlockEntity) {
            ((IncubatorBlockEntity) be).setEssenceSlot(0, 0);
            ((IncubatorBlockEntity) be).setEssenceSlot(1, 0);
        }
        world.setBlockState(pos, state.with(COOKING, false).with(COOKED, false));
    }

    @Override
    @SuppressWarnings ("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(HALF).equals(DoubleBlockHalf.LOWER)) {
            return BOTTOM_SHAPE;
        } else {
            return TOP_SHAPE;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING, COOKED, COOKING, HALF, LIT);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? checkType(type, KinderBlocks.INCUBATOR_BLOCK_ENTITY, IncubatorBlockEntity::clientTick) : checkType(type, KinderBlocks.INCUBATOR_BLOCK_ENTITY, IncubatorBlockEntity::serverTick);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        if (blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx)) {
            return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing()).with(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
    }

    @SuppressWarnings ("deprecation")
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        return state.get(HALF) == DoubleBlockHalf.LOWER ? blockState.isSideSolidFullSquare(world, blockPos, Direction.UP) : blockState.isOf(this);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        if (state.get(HALF).equals(DoubleBlockHalf.LOWER)) {
            return new IncubatorBlockEntity(pos, state);
        } else {
            return null;
        }
    }

    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.isCreative()) {
            onBreakInCreative(world, pos, state, player);
        }

        super.onBreak(world, pos, state, player);
    }

    @SuppressWarnings ("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP) && (!neighborState.isOf(this) || neighborState.get(HALF) == doubleBlockHalf)) {
            return Blocks.AIR.getDefaultState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    @SuppressWarnings ("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof IncubatorBlockEntity incubator)) {
            return super.onUse(state, world, pos, player, hand, hit);
        }

        ItemStack itemStack = player.getStackInHand(hand);

        if (hand != Hand.MAIN_HAND || !state.get(HALF).equals(DoubleBlockHalf.LOWER)) {
            return super.onUse(state, world, pos, player, hand, hit);
        }

        boolean isCooking = state.get(COOKING);
        boolean isCooked = state.get(COOKED);

        if (!isCooking && !isCooked) {
            return handleNotCookingState(state, world, pos, player, hand, itemStack, incubator);
        }

        if (isCooking && !isCooked && player.isCreative() && player.isSneaky()) {
            world.setBlockState(pos, state.with(COOKED, true).with(COOKING, false));
            return ActionResult.success(world.isClient);
        }

        if (isCooked) {
            world.scheduleBlockTick(pos, this, 1);
            world.setBlockState(pos.up(), world.getBlockState(pos.up()).with(LIT, false));
            return ActionResult.success(world.isClient);
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    private ActionResult handleNotCookingState(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack itemStack, IncubatorBlockEntity incubator) {
        if (incubator.essenceSlot2 != 0 && incubator.essenceSlot1 != 0 && itemStack.isEmpty()) {
            startCooking(state, world, pos);
            return ActionResult.success(world.isClient);
        }

        if (itemStack.getItem() == Items.GLASS_BOTTLE && incubator.essenceSlot1 != 0) {
            KinderMod.LOGGER.info("Draining");
            drainEssence(incubator, state, world, pos, player, hand);
            world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
            return ActionResult.success(world.isClient);
        }

        for (Map.Entry<Integer, Item> map : essenceHash().entrySet()) {
            if (itemStack.getItem() == map.getValue().asItem()) {
                KinderMod.LOGGER.info("Knows you're holding essence");
                if (incubator.essenceSlot1 == 0 || incubator.essenceSlot2 == 0) {
                    fillEssence(incubator, map.getKey());
                    itemStack.decrement(1);
                    world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
                    world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    handleEmptyItemStack(player, hand, itemStack);
                    return ActionResult.success(world.isClient);
                }
            }
        }
        return ActionResult.PASS;
    }

    private void startCooking(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(COOKING, true));
        world.setBlockState(pos.up(), world.getBlockState(pos.up()).with(LIT, true));
    }

    private void handleEmptyItemStack(PlayerEntity player, Hand hand, ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            player.setStackInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
        } else if (!player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE))) {
            player.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
        }
    }

    @SuppressWarnings ("deprecation")
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof IncubatorBlockEntity) {
            ItemStack is = IncubatorBlockEntity.GemItemStack(world, pos, ((IncubatorBlockEntity) be));
            if (is != null) {
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), is);
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

    public void drainEssence(IncubatorBlockEntity be, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        int essenceSlot = be.essenceSlot2 != 0 ? 2 : (be.essenceSlot1 != 0 ? 1 : 0);

        if (essenceSlot != 0) {
            itemStack.decrement(1);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

            Item essenceBottle = essenceHash().get(essenceSlot == 2 ? be.essenceSlot2 : be.essenceSlot1);
            ItemStack essenceStack = new ItemStack(essenceBottle);

            if (itemStack.isEmpty()) {
                player.setStackInHand(hand, essenceStack);
            } else if (!player.getInventory().insertStack(essenceStack)) {
                player.dropItem(essenceStack, false);
            }

            be.setEssenceSlot(essenceSlot - 1, 0);
        }
    }

}
