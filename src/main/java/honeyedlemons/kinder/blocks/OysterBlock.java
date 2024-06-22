package honeyedlemons.kinder.blocks;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.blocks.entities.OysterBlockEntity;
import honeyedlemons.kinder.init.KinderBlocks;
import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class OysterBlock extends BlockWithEntity implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty COOKED = BooleanProperty.of ("cooked");
    public static final BooleanProperty COOKING = BooleanProperty.of ("cooking");


    protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape (0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public OysterBlock (Settings settings){
        super (settings);
        setDefaultState (getDefaultState ().with (Properties.HORIZONTAL_FACING, Direction.NORTH).with (WATERLOGGED, false).with (COOKED, false).with (COOKING, false));
    }

    @SuppressWarnings("deprecation") @Override
    public VoxelShape getOutlineShape (BlockState state, BlockView world, BlockPos pos, ShapeContext context){
        return BOTTOM_SHAPE;
    }

    @Override public BlockRenderType getRenderType (BlockState state){
        return BlockRenderType.MODEL;
    }

    @Override protected void appendProperties (StateManager.Builder<Block, BlockState> builder){
        builder.add (Properties.HORIZONTAL_FACING, WATERLOGGED, COOKED, COOKING);
    }

    @Override public BlockState getPlacementState (ItemPlacementContext ctx){
        FluidState fluidState = ctx.getWorld ().getFluidState (ctx.getBlockPos ());
        return this.getDefaultState ().with (Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing ().getOpposite ()).with (WATERLOGGED, fluidState.isIn (FluidTags.WATER) && fluidState.getLevel () == 8);
    }

    @SuppressWarnings("deprecation") public FluidState getFluidState (BlockState state){
        return state.get (WATERLOGGED) ? Fluids.WATER.getStill (false) : super.getFluidState (state);
    }

    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate (BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos){
        if (state.get (WATERLOGGED)) {
            world.scheduleFluidTick (pos, Fluids.WATER, Fluids.WATER.getTickRate (world));
        }
        return super.getStateForNeighborUpdate (state, direction, neighborState, world, pos, neighborPos);
    }

    @Nullable @Override public BlockEntity createBlockEntity (BlockPos pos, BlockState state){
        return new OysterBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker (World world, BlockState state, BlockEntityType<T> type){
        return world.isClient ? null : checkType (type, KinderBlocks.OYSTER_BLOCK_ENTITY, OysterBlockEntity::tick);
    }

    @Override public void onBreak (World world, BlockPos pos, BlockState state, PlayerEntity player){
        BlockEntity blockEntity = world.getBlockEntity (pos);
        if (blockEntity instanceof OysterBlockEntity && state.get (WATERLOGGED) && state.get (COOKED) && !world.isClient) {
            ItemScatterer.spawn (world, pos.getX (), pos.getY (), pos.getZ (), ((OysterBlockEntity) blockEntity).getPearl (world, pos));
        }
        super.onBreak (world, pos, state, player);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (!(blockEntity instanceof OysterBlockEntity oyster)) {
            return super.onUse(state, world, pos, player, hand, hit);
        }

        boolean cooked = state.get(COOKED);
        boolean cooking = state.get(COOKING);
        boolean waterlogged = state.get(WATERLOGGED);

        if (!cooked && !cooking && waterlogged) {
            return handleWaterloggedState(world, pos, player, hand, itemStack, state);
        }

        if (cooked && itemStack.getItem() == KinderItems.PEARL_SHUCK) {
            return handlePearlShuck(world, pos, player, hand, oyster, state, itemStack);
        }

        if (player.isSneaking() && player.isCreative() && !cooked && cooking) {
            world.setBlockState(pos, state.with(COOKING, false).with(COOKED, true));
            return ActionResult.success(world.isClient);
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    private ActionResult handleWaterloggedState(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack itemStack, BlockState state) {
        Item item = itemStack.getItem();
        if (item == KinderItems.WHITE_ESSENCE_BOTTLE) {
            world.setBlockState(pos, state.with(COOKING, true), Block.NOTIFY_ALL);
            itemStack.decrement(1);
            world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);

            handleEmptyItemStack(player, hand, itemStack);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    private ActionResult handlePearlShuck(World world, BlockPos pos, PlayerEntity player, Hand hand, OysterBlockEntity oyster, BlockState state, ItemStack itemStack) {
        float randomWeight = world.random.nextFloat();
        float luckWeight = player.getStatusEffect(StatusEffects.LUCK) != null ? 0.2f : 0f;
        float breakChance = oyster.getBreakChance();

        KinderMod.LOGGER.info("Random Weight is " + randomWeight + ", luck weight is " + luckWeight + ", break chance is " + breakChance);

        if (randomWeight <= (luckWeight + breakChance)) {
            oyster.setBreakChance(breakChance - 0.2f);
            world.setBlockState(pos, state.with(COOKED, false));
        } else {
            world.breakBlock(pos, true);
        }

        itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), oyster.getPearl(world, pos));
        return ActionResult.success(world.isClient);
    }

    private void handleEmptyItemStack(PlayerEntity player, Hand hand, ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            player.setStackInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
        } else if (!player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE))) {
            player.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
        }
    }


    public void randomDisplayTick (BlockState state, World world, BlockPos pos, Random random){
        super.randomDisplayTick (state, world, pos, random);
        if (state.get (COOKED).equals (true)) {
            world.addParticle (ParticleTypes.ELECTRIC_SPARK, pos.getX () + world.random.nextFloat (), pos.getY () + world.random.nextFloat () + 0.2, pos.getZ () + world.random.nextFloat (), world.random.nextFloat () / 3, world.random.nextFloat () / 3, world.random.nextFloat () / 3);
        }
    }
}