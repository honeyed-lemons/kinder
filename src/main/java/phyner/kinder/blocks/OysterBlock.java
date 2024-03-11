package phyner.kinder.blocks;

import com.mojang.serialization.MapCodec;
import jdk.jshell.Snippet;
import net.minecraft.block.*;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
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
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import phyner.kinder.KinderMod;
import phyner.kinder.blocks.entities.OysterBlockEntity;
import phyner.kinder.init.KinderBlocks;
import phyner.kinder.init.KinderItems;

public class OysterBlock extends BlockWithEntity implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty COOKED = BooleanProperty.of("cooked");
    public static final BooleanProperty COOKING = BooleanProperty.of("cooking");


    protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0.0,
            0.0,
            0.0,
            16.0,
            8.0,
            16.0);

    public OysterBlock(Settings settings){
        super(settings);
        setDefaultState(getDefaultState()
                .with(Properties.HORIZONTAL_FACING,
                        Direction.NORTH)
                .with(WATERLOGGED,
                        false).with(COOKED,
                        false).with(COOKING,
                        false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state,BlockView world,BlockPos pos,ShapeContext context){
        return BOTTOM_SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state){
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(Properties.HORIZONTAL_FACING,
                WATERLOGGED,
                COOKED,
                COOKING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx){
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState()
                .with(Properties.HORIZONTAL_FACING,
                        ctx.getHorizontalPlayerFacing().getOpposite())
                .with(WATERLOGGED,
                        fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8);
    }

    public FluidState getFluidState(BlockState state){
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state,Direction direction,BlockState neighborState,WorldAccess world,BlockPos pos,BlockPos neighborPos){
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos,
                    Fluids.WATER,
                    Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state,
                direction,
                neighborState,
                world,
                pos,
                neighborPos);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos,BlockState state){
        return new OysterBlockEntity(pos,
                state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, KinderBlocks.OYSTER_BLOCK_ENTITY, OysterBlockEntity::tick);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof OysterBlockEntity && state.get(WATERLOGGED) && state.get(COOKED) && !world.isClient) {
            ItemScatterer.spawn(world,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    ((OysterBlockEntity) blockEntity).getPearl(world,
                            pos));
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public ActionResult onUse(BlockState state,World world,BlockPos pos,PlayerEntity player,Hand hand,BlockHitResult hit){
        ItemStack itemStack = player.getStackInHand(hand);
        boolean cooked = state.get(COOKED);
        boolean cooking = state.get(COOKING);
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof OysterBlockEntity) {
            if (!cooked && !cooking && state.get(WATERLOGGED).equals(true)) {
                Item item = itemStack.getItem();
                if (item == KinderItems.WHITE_ESSENCE) {
                    world.setBlockState(pos,
                            state.with(COOKING,
                                    true),
                            Block.NOTIFY_ALL);
                    itemStack.decrement(1);
                    world.playSound(player,player.getX(),player.getY(),player.getZ(),SoundEvents.ITEM_BOTTLE_EMPTY,SoundCategory.BLOCKS,1.0f,1.0f);
                    if (itemStack.isEmpty()) {
                        player.setStackInHand(hand,new ItemStack(Items.GLASS_BOTTLE));
                    } else if (!player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE))) {
                        player.dropItem(new ItemStack(Items.GLASS_BOTTLE),false);
                    }
                    return ActionResult.success(world.isClient);
                }
            } else if (itemStack.getItem() == KinderItems.PEARL_SHUCK && state.get(COOKED)) {
                float randomWeight = world.random.nextFloat();
                float luckWeight = 0f;
                float breakChance = ((OysterBlockEntity) be).getBreakChance();
                if (player.getStatusEffect(StatusEffects.LUCK) != null) {
                    luckWeight = 0.2f;
                }
                KinderMod.LOGGER.info("Random Weight is "+ randomWeight+", luck weight is "+luckWeight+", break chance is "+breakChance);
                if (randomWeight <= (luckWeight + breakChance)) {
                    ((OysterBlockEntity) be).setBreakChance(breakChance - 0.2f);
                    world.setBlockState(pos,state.with(COOKED,false));
                } else {
                    world.breakBlock(pos,true);
                }
                itemStack.damage(1,player,playerx -> playerx.sendToolBreakStatus(hand));
                ItemScatterer.spawn(world,
                        pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        ((OysterBlockEntity) be).getPearl(world,pos));
                return ActionResult.success(world.isClient);
            }
            else if (player.isSneaking() && player.isCreative() && !cooked && cooking) {
                world.setBlockState(pos,state.with(COOKING,false).with(COOKED,true));
                return ActionResult.success(world.isClient);
            }
        }
        return super.onUse(state,
                world,
                pos,
                player,
                hand,
                hit);
    }
}