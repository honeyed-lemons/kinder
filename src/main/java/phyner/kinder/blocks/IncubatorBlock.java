package phyner.kinder.blocks;

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
import net.minecraft.state.property.IntProperty;
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
import org.jetbrains.annotations.Nullable;
import phyner.kinder.KinderMod;
import phyner.kinder.blocks.entities.IncubatorBlockEntity;
import phyner.kinder.init.KinderBlocks;
import phyner.kinder.init.KinderItems;

import java.util.HashMap;
import java.util.Map;

public class IncubatorBlock extends BlockWithEntity {
    public static final IntProperty ESSENCESLOT1 = IntProperty.of("essence_slot_1",0,4);
    public static final IntProperty ESSENCESLOT2 = IntProperty.of("essence_slot_2",0,4);
    public static final BooleanProperty COOKED = BooleanProperty.of("cooked");
    public static final BooleanProperty COOKING = BooleanProperty.of("cooking");
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(4.0,0.0,4.0,12.0,16.0,12.0);
    protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(4.0,0.0,4.0,12.0,13.0,12.0);

    public IncubatorBlock(Settings settings){
        super(settings);
        setDefaultState(getDefaultState().with(ESSENCESLOT1,0).with(ESSENCESLOT2,0).with(COOKED,false).with(COOKING,false).with(Properties.HORIZONTAL_FACING,Direction.NORTH).with(HALF,DoubleBlockHalf.LOWER));
    }

    public static HashMap<Integer, Item> essenceHash(){
        HashMap<Integer, Item> hash = new HashMap<>();
        hash.put(1,KinderItems.WHITE_ESSENCE);
        hash.put(2,KinderItems.YELLOW_ESSENCE);
        hash.put(3,KinderItems.BLUE_ESSENCE);
        hash.put(4,KinderItems.PINK_ESSENCE);
        return hash;
    }

    protected static void onBreakInCreative(World world,BlockPos pos,BlockState state,PlayerEntity player){
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
            BlockPos blockPos = pos.down();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(state.getBlock()) && blockState.get(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockState2 = blockState.getFluidState().isOf(Fluids.WATER) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
                world.setBlockState(blockPos,blockState2,Block.NOTIFY_ALL | Block.SKIP_DROPS);
                world.syncWorldEvent(player,WorldEvents.BLOCK_BROKEN,blockPos,Block.getRawIdFromState(blockState));
            }
        }

    }

    public static void explode(World world,BlockPos pos){
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        world.createExplosion(null,x,y,z,3.0F,World.ExplosionSourceType.BLOCK);
    }

    public static int combineEssences(int essence1,int essence2){
        if (essence1 == essence2) {
            return essence1;
        } else if ((essence1 == 2 && essence2 == 3) || (essence1 == 3 && essence2 == 2)) {
            return 5; // Yellow Blue
        } else if ((essence1 == 2 && essence2 == 4) || (essence1 == 4 && essence2 == 2)) {
            return 6; // Yellow Pink
        } else if ((essence1 == 4 && essence2 == 3) || (essence1 == 3 && essence2 == 4)) {
            return 7; // Pink Blue
        } else {
            return Integer.max(essence1,essence2);
        }
    }

    public static void FlushEssence(BlockState state,World world,BlockPos pos){
        world.setBlockState(pos,state.with(ESSENCESLOT1,0).with(ESSENCESLOT2,0).with(COOKING,false).with(COOKED,false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state,BlockView world,BlockPos pos,ShapeContext context){
        if (state.get(HALF).equals(DoubleBlockHalf.LOWER)) {
            return BOTTOM_SHAPE;
        } else {
            return TOP_SHAPE;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(Properties.HORIZONTAL_FACING,ESSENCESLOT1,ESSENCESLOT2,COOKED,COOKING,HALF);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state){
        return BlockRenderType.MODEL;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world,BlockState state,BlockEntityType<T> type){
        return world.isClient ? null : checkType(type,KinderBlocks.INCUBATOR_BLOCK_ENTITY,IncubatorBlockEntity::tick);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx){
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        if (blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx)) {
            return this.getDefaultState().with(Properties.HORIZONTAL_FACING,ctx.getHorizontalPlayerFacing()).with(HALF,DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    public void onPlaced(World world,BlockPos pos,BlockState state,LivingEntity placer,ItemStack itemStack){
        world.setBlockState(pos.up(),state.with(HALF,DoubleBlockHalf.UPPER),Block.NOTIFY_ALL);
    }

    public boolean canPlaceAt(BlockState state,WorldView world,BlockPos pos){
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        return state.get(HALF) == DoubleBlockHalf.LOWER ? blockState.isSideSolidFullSquare(world,blockPos,Direction.UP) : blockState.isOf(this);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos,BlockState state){
        return new IncubatorBlockEntity(pos,state);
    }

    public void onBreak(World world,BlockPos pos,BlockState state,PlayerEntity player){
        if (!world.isClient && player.isCreative()) {
            onBreakInCreative(world,pos,state,player);
        }

        super.onBreak(world,pos,state,player);
    }

    public BlockState getStateForNeighborUpdate(BlockState state,Direction direction,BlockState neighborState,WorldAccess world,BlockPos pos,BlockPos neighborPos){
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP) && (!neighborState.isOf(this) || neighborState.get(HALF) == doubleBlockHalf)) {
            return Blocks.AIR.getDefaultState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world,pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state,direction,neighborState,world,pos,neighborPos);
        }
    }

    @Override
    public ActionResult onUse(BlockState state,World world,BlockPos pos,PlayerEntity player,Hand hand,BlockHitResult hit){
        ItemStack itemStack = player.getStackInHand(hand);
        BlockEntity be = world.getBlockEntity(pos);
        boolean cooking = state.get(COOKING);
        boolean cooked = state.get(COOKED);
        if (hand == player.preferredHand && state.get(HALF).equals(DoubleBlockHalf.LOWER)) {
            if (be instanceof IncubatorBlockEntity) {
                if (!cooking && !cooked) {
                    if (!state.get(ESSENCESLOT2).equals(0) && !state.get(ESSENCESLOT1).equals(0) && player.getStackInHand(hand) == ItemStack.EMPTY) {
                        world.setBlockState(pos,state.with(COOKING,true));
                        return ActionResult.success(world.isClient);
                    }
                    if (itemStack.getItem() == Items.GLASS_BOTTLE && !state.get(ESSENCESLOT1).equals(0)) {
                        KinderMod.LOGGER.info("Draining");
                        drainEssence(state,world,pos,player,hand);
                        return ActionResult.success(world.isClient);
                    } else {
                        for (Map.Entry<Integer, Item> map : essenceHash().entrySet()) {
                            if (itemStack.getItem() == map.getValue()) {
                                KinderMod.LOGGER.info("Knows you're holding essence");
                                if (state.get(ESSENCESLOT1).equals(0) || state.get(ESSENCESLOT2).equals(0)) {
                                    fillEssence(state,map.getKey(),world,pos);
                                    itemStack.decrement(1);
                                    world.playSound(player,player.getX(),player.getY(),player.getZ(),SoundEvents.ITEM_BOTTLE_EMPTY,SoundCategory.BLOCKS,1.0f,1.0f);
                                    if (itemStack.isEmpty()) {
                                        player.setStackInHand(hand,new ItemStack(Items.GLASS_BOTTLE));
                                    } else if (!player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE))) {
                                        player.dropItem(new ItemStack(Items.GLASS_BOTTLE),false);
                                    }
                                    return ActionResult.success(world.isClient);
                                }
                            }
                        }
                    }
                } else if (cooking && !cooked && player.isCreative() && player.isSneaky()) {
                    world.setBlockState(pos,state.with(COOKED,true).with(COOKING,false));
                    return ActionResult.success(world.isClient);
                } else if (cooked) {
                    world.scheduleBlockTick(pos,this,1);
                    return ActionResult.success(world.isClient);
                }
            }
        }
        return super.onUse(state,world,pos,player,hand,hit);
    }

    public void scheduledTick(BlockState state,ServerWorld world,BlockPos pos,Random random){
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof IncubatorBlockEntity) {
            ItemStack is = IncubatorBlockEntity.GemItemStack(world,pos,((IncubatorBlockEntity) be));
            if (is != null) {
                ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),is);
                FlushEssence(state,world,pos);
            }
        }
    }

    public void fillEssence(BlockState state,int essencetofill,World world,BlockPos pos){
        if (!state.get(ESSENCESLOT1).equals(0)) {
            world.setBlockState(pos,state.with(ESSENCESLOT2,essencetofill));
        } else {
            world.setBlockState(pos,state.with(ESSENCESLOT1,essencetofill));
        }
    }

    public void drainEssence(BlockState state,World world,BlockPos pos,PlayerEntity player,Hand hand){
        ItemStack itemStack = player.getStackInHand(hand);
        if (!state.get(ESSENCESLOT1).equals(0) && !state.get(ESSENCESLOT2).equals(0)) {
            itemStack.decrement(1);
            world.playSound(player,player.getX(),player.getY(),player.getZ(),SoundEvents.ITEM_BOTTLE_FILL,SoundCategory.BLOCKS,1.0F,1.0F);
            Item essencebottle = essenceHash().get(state.get(ESSENCESLOT2));
            if (itemStack.isEmpty()) {
                player.setStackInHand(hand,new ItemStack(essencebottle));
            } else if (!player.getInventory().insertStack(new ItemStack(essencebottle))) {
                player.dropItem(new ItemStack(essencebottle),false);
            }
            world.setBlockState(pos,state.with(ESSENCESLOT2,0));
        } else {
            itemStack.decrement(1);
            world.playSound(player,player.getX(),player.getY(),player.getZ(),SoundEvents.ITEM_BOTTLE_FILL,SoundCategory.BLOCKS,1.0F,1.0F);
            Item essencebottle = essenceHash().get(state.get(ESSENCESLOT1));
            if (itemStack.isEmpty()) {
                player.setStackInHand(hand,new ItemStack(essencebottle));
            } else if (!player.getInventory().insertStack(new ItemStack(essencebottle))) {
                player.dropItem(new ItemStack(essencebottle),false);
            }
            world.setBlockState(pos,state.with(ESSENCESLOT1,0));
        }
    }
}
