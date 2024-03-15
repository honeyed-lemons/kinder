package phyner.kinder.blocks.entities;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phyner.kinder.KinderMod;
import phyner.kinder.blocks.OysterBlock;
import phyner.kinder.init.KinderBlocks;
import phyner.kinder.init.KinderItems;

import java.util.ArrayList;
import java.util.Arrays;

public class OysterBlockEntity extends AbstractIncubatingBlockEntity {

    public int ticksElapsed;
    public float breakChance = 1f;

    public OysterBlockEntity(BlockPos pos,BlockState state){
        super(KinderBlocks.OYSTER_BLOCK_ENTITY,pos,state);
    }

    public static void tick(World world,BlockPos pos,BlockState state,OysterBlockEntity blockEntity){
        if (state.get(OysterBlock.COOKING).equals(true) && state.get(OysterBlock.WATERLOGGED)) {
            if (blockEntity.ticksElapsed >= (24000 / (getDownfall(world,pos) + 0.5))) {
                world.setBlockState(pos,state.with(OysterBlock.COOKED,true).with(OysterBlock.COOKING,false));
                blockEntity.ticksElapsed = 0;
            } else {
                blockEntity.ticksElapsed++;
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        nbt.putInt("te",ticksElapsed);
        nbt.putFloat("breakChance",breakChance);
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        ticksElapsed = nbt.getInt("te");
        breakChance = nbt.getFloat("breakChance");
    }

    public ItemStack getPearl(World world,BlockPos blockPos){
        float biomeTemp = getBiomeTemp(world,blockPos);
        int pearlSet;
        float minVal;
        float maxVal;

        if (biomeTemp >= 1.4) {
            pearlSet = 4;
            minVal = 1.4f;
            maxVal = 2f;
        } else if (biomeTemp >= 0.9) {
            pearlSet = 3;
            minVal = 0.9f;
            maxVal = 1.3f;
        } else if (biomeTemp >= 0.5) {
            pearlSet = 2;
            minVal = 0.5f;
            maxVal = 0.8f;
        } else {
            pearlSet = 1;
            minVal = -1f;
            maxVal = 0.5f;
        }

        ArrayList<Item> pearls = genPearlSet(pearlSet);

        float weighting = (biomeTemp >= (maxVal + minVal) / 2) ? 0.15f : -0.15f;

        ArrayList<Item> finalTwo = new ArrayList<>();
        if (world.random.nextFloat() + weighting >= 0.5) {
            finalTwo.add(pearls.get(0));
            finalTwo.add(pearls.get(1));
        } else {
            finalTwo.add(pearls.get(2));
            finalTwo.add(pearls.get(3));
        }
        Item finalPearlItem = (world.random.nextFloat() >= 0.5) ? finalTwo.get(0) : finalTwo.get(1);
        ItemStack finalPearlItemStack = new ItemStack(finalPearlItem);
        finalPearlItemStack.getOrCreateNbt().putInt("Perfection",getPerfection(blockPos));
        return finalPearlItemStack;
    }

    public int getPerfection(BlockPos blockPos){
        int ylevel = blockPos.getY();
        int val;
        if (ylevel >= 70) {
            val = 1;
        } else if (ylevel >= 65) {
            val = 2;
        } else if (ylevel >= 50) {
            val = 3;
        } else if (ylevel >= 40) {
            val = 4;
        } else if (ylevel >= 30) {
            val = 5;
        } else {
            val = 6;
        }
        val = (int) (val * (getDownfall(world,blockPos) + 0.2));
        val = Math.min(6, Math.max(1, val));
        KinderMod.LOGGER.info("Pearl perfection is " + val);
        return val;
    }

    public ArrayList<Item> genPearlSet(int pearlSet){
        ArrayList<Item> pearls = new ArrayList<>();

        switch (pearlSet) {
            case 4 ->
                    pearls.addAll(Arrays.asList(KinderItems.PEARL_GEM_0,KinderItems.PEARL_GEM_14,KinderItems.PEARL_GEM_2,KinderItems.PEARL_GEM_6));
            case 3 ->
                    pearls.addAll(Arrays.asList(KinderItems.PEARL_GEM_1,KinderItems.PEARL_GEM_2,KinderItems.PEARL_GEM_7,KinderItems.PEARL_GEM_8));
            case 2 ->
                    pearls.addAll(Arrays.asList(KinderItems.PEARL_GEM_4,KinderItems.PEARL_GEM_5,KinderItems.PEARL_GEM_13,KinderItems.PEARL_GEM_3));
            case 1 ->
                    pearls.addAll(Arrays.asList(KinderItems.PEARL_GEM_11,KinderItems.PEARL_GEM_9,KinderItems.PEARL_GEM_15,KinderItems.PEARL_GEM_10));
        }

        return pearls;
    }

    public float getBreakChance(){
        return breakChance;
    }

    public void setBreakChance(float nbreakChance){
        breakChance = nbreakChance;
    }
}
