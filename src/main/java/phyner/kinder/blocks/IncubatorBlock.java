package phyner.kinder.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phyner.kinder.KinderMod;
import phyner.kinder.blocks.entities.IncubatorBlockEntity;
import phyner.kinder.blocks.entities.OysterBlockEntity;
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
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world,BlockState state,BlockEntityType<T> type){
        return super.getTicker(world,state,type);
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
        if (be instanceof IncubatorBlockEntity) {
            if (state.get(COOKING).equals(false) && state.get(COOKED).equals(false) && player.isSneaking()) {
                if (itemStack.getItem() == Items.GLASS_BOTTLE && !state.get(ESSENCESLOT1).equals(0)) {
                    KinderMod.LOGGER.info("Draining");
                    drainEssence(state,world,pos,player,hand);
                } else {
                    for (Map.Entry<Integer, Item> map : essenceHash().entrySet()) {
                        if (itemStack.getItem() == map.getValue()) {
                            if (state.get(ESSENCESLOT1).equals(0) || state.get(ESSENCESLOT2).equals(0)) {
                                fillEssence(state,
                                        map.getKey(),
                                        world,
                                        pos);
                                itemStack.decrement(1);
                                world.playSound(player,
                                        player.getX(),
                                        player.getY(),
                                        player.getZ(),
                                        SoundEvents.ITEM_BOTTLE_EMPTY,
                                        SoundCategory.BLOCKS,
                                        1.0f,
                                        1.0f);
                                if (itemStack.isEmpty()) {
                                    player.setStackInHand(hand,
                                            new ItemStack(Items.GLASS_BOTTLE));
                                } else if (!player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE))) {
                                    player.dropItem(new ItemStack(Items.GLASS_BOTTLE),
                                            false);
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.onUse(state,world,pos,player,hand,hit);
    }

    public void neighborUpdate(BlockState state,World world,BlockPos pos,Block sourceBlock,BlockPos sourcePos,boolean notify){
        if (!world.isClient) {
            if (world.isReceivingRedstonePower(pos)) {
                BlockEntity be = world.getBlockEntity(pos);
                if (be instanceof IncubatorBlockEntity) {
                    ((IncubatorBlockEntity) be).help(world,
                            pos);
                }
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
}
