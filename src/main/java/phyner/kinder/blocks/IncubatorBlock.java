package phyner.kinder.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phyner.kinder.KinderMod;
import phyner.kinder.blocks.entities.IncubatorBlockEntity;
import phyner.kinder.blocks.entities.OysterBlockEntity;
import phyner.kinder.entities.AbstractGemEntity;
import phyner.kinder.init.KinderBlocks;
import phyner.kinder.init.KinderItems;

import java.util.HashMap;
import java.util.Map;

public class IncubatorBlock extends BlockWithEntity {
    public IncubatorBlock(Settings settings){
        super(settings);
        setDefaultState(getDefaultState().with(ESSENCESLOT1,0).with(ESSENCESLOT2,0).with(COOKED,false).with(COOKING,false));
    }
    public static final IntProperty ESSENCESLOT1 = IntProperty.of("essence_slot_1",0,4);
    public static final IntProperty ESSENCESLOT2 = IntProperty.of("essence_slot_2",0,4);
    public static final BooleanProperty COOKED = BooleanProperty.of("cooked");
    public static final BooleanProperty COOKING = BooleanProperty.of("cooking");

    public static HashMap<Integer,Item> essenceHash()
    {
        HashMap<Integer,Item> hash = new HashMap<>();
        hash.put(1,KinderItems.WHITE_ESSENCE);
        hash.put(2,KinderItems.YELLOW_ESSENCE);
        hash.put(3,KinderItems.BLUE_ESSENCE);
        hash.put(4,KinderItems.PINK_ESSENCE);
        return hash;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(ESSENCESLOT1,ESSENCESLOT2,
                COOKED,
                COOKING);
    }
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, KinderBlocks.INCUBATOR_BLOCK_ENTITY, IncubatorBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos,BlockState state){
        return new IncubatorBlockEntity(pos,state);
    }

    @Override
    public ActionResult onUse(BlockState state,World world,BlockPos pos,PlayerEntity player,Hand hand,BlockHitResult hit){
        ItemStack itemStack = player.getStackInHand(hand);
        BlockEntity be = world.getBlockEntity(pos);
        boolean cooking = state.get(COOKING);
        boolean cooked = state.get(COOKED);
        if (hand == player.preferredHand) {
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
                                    }
                                    else if (!player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE))) {
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

    public static void explode(World world,BlockPos pos) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        world.createExplosion(null, x, y, z, 3.0F, World.ExplosionSourceType.BLOCK);
    }
    public void scheduledTick(BlockState state,ServerWorld world,BlockPos pos,Random random) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof IncubatorBlockEntity) {
            ItemStack is = ((IncubatorBlockEntity) be).GemItemStack(world,pos).getDefaultStack();
            if (is != null)
            {
                ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),is);
                FlushEssence(state,world,pos);
            }
        }
    }
    public void fillEssence(BlockState state,int essencetofill,World world,BlockPos pos)
    {
        if (!state.get(ESSENCESLOT1).equals(0))
        {
            world.setBlockState(pos,state.with(ESSENCESLOT2,essencetofill));
        }
        else
        {
            world.setBlockState(pos,state.with(ESSENCESLOT1,essencetofill));
        }
    }

    public void drainEssence(BlockState state,World world,BlockPos pos,PlayerEntity player,Hand hand)
    {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!state.get(ESSENCESLOT1).equals(0) && !state.get(ESSENCESLOT2).equals(0))
        {
            itemStack.decrement(1);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            Item essencebottle = essenceHash().get(state.get(ESSENCESLOT2));
            if (itemStack.isEmpty()) {
                player.setStackInHand(hand, new ItemStack(essencebottle));
            } else if (!player.getInventory().insertStack(new ItemStack(essencebottle))) {
                player.dropItem(new ItemStack(essencebottle), false);
            }
            world.setBlockState(pos,state.with(ESSENCESLOT2,0));
        }
        else
        {
            itemStack.decrement(1);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            Item essencebottle = essenceHash().get(state.get(ESSENCESLOT1));
            if (itemStack.isEmpty()) {
                player.setStackInHand(hand, new ItemStack(essencebottle));
            } else if (!player.getInventory().insertStack(new ItemStack(essencebottle))) {
                player.dropItem(new ItemStack(essencebottle), false);
            }
            world.setBlockState(pos,state.with(ESSENCESLOT1,0));
        }
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
        }
        else {
            return Integer.max(essence1,essence2);
        }
    }

    public static void FlushEssence(BlockState state,World world,BlockPos pos)
    {
        world.setBlockState(pos,state.with(ESSENCESLOT1,0).with(ESSENCESLOT2,0).with(COOKING,false).with(COOKED,false));
    }
}
