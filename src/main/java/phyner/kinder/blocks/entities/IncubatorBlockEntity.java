package phyner.kinder.blocks.entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phyner.kinder.KinderMod;
import phyner.kinder.blocks.IncubatorBlock;
import phyner.kinder.init.*;
import phyner.kinder.util.GemColors;
import phyner.kinder.util.GemConditions;

import java.util.Map;
import java.util.Objects;

public class IncubatorBlockEntity extends AbstractIncubatingBlockEntity {
    public int ticksElapsed;
    public int ticksToSound = 0;
    public float defectivityModifier = 3;

    public IncubatorBlockEntity(BlockPos pos,BlockState state){
        super(KinderBlocks.INCUBATOR_BLOCK_ENTITY,pos,state);
    }

    public static void serverTick(World world,BlockPos pos,BlockState state,IncubatorBlockEntity blockEntity){
        BlockState thingyState = world.getBlockState(pos.up());
        if (state.get(IncubatorBlock.COOKING).equals(true)) {
            if (blockEntity.ticksElapsed >= 16000) {
                world.setBlockState(pos,state.with(IncubatorBlock.COOKED,true).with(IncubatorBlock.COOKING,false));
                blockEntity.ticksElapsed = 0;
            } else {
                if (blockEntity.ticksElapsed % 24 == 0) {
                    drainBlock(world,pos,blockEntity);
                }
                if (thingyState.get(IncubatorBlock.LIT).equals(false))
                {
                    world.setBlockState(pos.up(),thingyState.with(IncubatorBlock.LIT,true));
                }
                blockEntity.ticksElapsed++;
            }
        }
    }
    public static void clientTick(World world,BlockPos pos,BlockState state,IncubatorBlockEntity blockEntity){
        if (state.get(IncubatorBlock.COOKING).equals(true)) {
            if (blockEntity.ticksToSound <= 0)
            {
                KinderMod.LOGGER.info("Playing Sound");
                world.playSoundAtBlockCenter(pos,KinderSounds.INCUBATOR_SOUND,SoundCategory.BLOCKS,1f,1f,false);
                blockEntity.ticksToSound = 42;
            }
            blockEntity.ticksToSound--;
        }
    }

    public static ItemStack GemItemStack(World world,BlockPos blockPos,IncubatorBlockEntity blockEntity){
        BlockState bs = world.getBlockState(blockPos);
        int essenceColor = IncubatorBlock.combineEssences(bs.get(IncubatorBlock.ESSENCESLOT1),bs.get(IncubatorBlock.ESSENCESLOT2));
        float highestScore = 0;
        Item highestScorerItem = KinderItems.RUBY_GEM;
        for (GemConditions gemConditions : KinderConditions.conditions()) {
            for (Map.Entry<Item, GemColors> map : gemConditions.gems.entrySet()) {
                float score = scoreGem(gemConditions,world,blockPos,essenceColor,testEssence(map.getValue()));
                if (score >= highestScore) {
                    highestScore = score;
                    highestScorerItem = map.getKey();
                    KinderMod.LOGGER.info(map.getKey().getTranslationKey() + " Score: " + score);
                }
            }
        }
        if (highestScore == 0) {
            blockEntity.explosion(bs,blockPos,world);
            return null;
        }
        ItemStack highestScorerItemStack = highestScorerItem.getDefaultStack();
        KinderMod.LOGGER.info("Essence Color is " + essenceColor);
        int defectivity = blockEntity.getDefectivity();
        if (defectivity == 69) {
            blockEntity.explosion(bs,blockPos,world);
            return null;
        }
        KinderMod.LOGGER.info("Winmer is " + highestScorerItemStack.getTranslationKey() + ", with a defectivity of " + defectivity);
        highestScorerItemStack.getOrCreateNbt().putInt("Perfection",defectivity);
        blockEntity.defectivityModifier = 3;
        return highestScorerItemStack;
    }

    public static float scoreGem(GemConditions conditions,World world,BlockPos blockPos,int essenceColor,int gemColor){
        float score = conditions.baseRarity;
        if (conditions.biome != null) {
            for (Map.Entry<String, Float> map : conditions.biome.entrySet()) {
                if (Objects.equals(getBiomeName(world,blockPos),map.getKey())) {
                    score += map.getValue();
                }
            }
        }
        if (getBiomeTemp(world,blockPos) >= conditions.tempMin && getBiomeTemp(world,blockPos) <= conditions.tempMax) {
            score += 1f;
            if (getBiomeTemp(world,blockPos) == conditions.tempIdeal) {
                score += 0.5f;
            }
        }
        if (score != 0) {
            if (blockPos.getY() >= conditions.depthMax && blockPos.getY() <= conditions.depthMin) {
                score += 1f;
            } else {
                score = 0;
            }
            if (score != 0) {
                score += world.random.nextFloat();
            }
        }
        if (essenceColor == gemColor) {
            return score;
        }
        return 0;
    }

    public static void drainBlock(World world,BlockPos pos,IncubatorBlockEntity blockEntity){
        int randx = pos.getX() + world.random.nextBetween(-3,3);
        int randy = pos.getY() + world.random.nextBetween(-3,3);
        int randz = pos.getZ() + world.random.nextBetween(-3,3);
        BlockPos newBlockPos = new BlockPos(randx,randy,randz);
        BlockState blockState = world.getBlockState(newBlockPos);
        if (blockState.isIn(KinderBlockTags.DRAINABLE)) {
            BlockState drainedBlockState;
            float temp = getBiomeTemp(world,pos);
            if (temp >= 0.95f)
            {
                drainedBlockState = KinderBlocks.HOT_DRAINED_BLOCK.getDefaultState();
            } else if (temp >= 0.45f) {
                drainedBlockState = KinderBlocks.TEMP_DRAINED_BLOCK.getDefaultState();
            } else if (temp >= -1) {
                drainedBlockState = KinderBlocks.COLD_DRAINED_BLOCK.getDefaultState();
            }
            else
            {
                drainedBlockState = KinderBlocks.TEMP_DRAINED_BLOCK.getDefaultState();
            }
            KinderMod.LOGGER.info(world.getDimension().toString());
            world.setBlockState(newBlockPos,drainedBlockState);
            if (world.random.nextFloat() > 0.3) {
                for (Map.Entry<Block, Float> map1 : KinderConditions.cruxblocks().entrySet()) {
                    if (blockState.isOf(map1.getKey())) {
                        blockEntity.defectivityModifier += map1.getValue();
                    }
                }
                for (Map.Entry<TagKey<Block>, Float> map2 : KinderConditions.cruxtags().entrySet()) {
                    if (blockState.isIn(map2.getKey())) {
                        blockEntity.defectivityModifier += map2.getValue();
                    }
                }
            }
        }
    }

    public static int testEssence(GemColors gemcolor){
        int color = gemcolor.getId();
        switch (color) {
            //White
            case 0,7,8,15 -> {
                return 1;
            }
            //Yellow
            case 4 -> {
                return 2;
            }
            //Blue
            case 3,11 -> {
                return 3;
            }
            //Pink
            case 6,14 -> {
                return 4;
            }
            //Yellow Blue
            case 5,9,13 -> {
                return 5;
            }
            // Yellow Pink
            case 1,12 -> {
                return 6;
            }
            // Pink Blue
            case 2,10 -> {
                return 7;
            }
        }
        return 1;
    }
    /*
    white = 1
    yellow = 2
    blue = 3
    pink = 4
    yellow blue = 5
    yellow pink = 6
    pink blue = 7
    */
    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        nbt.putInt("te",ticksElapsed);
        nbt.putFloat("dm",defectivityModifier);
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        ticksElapsed = nbt.getInt("te");
        defectivityModifier = nbt.getFloat("dm");
    }

    public void explosion(BlockState blockState,BlockPos blockPos,World world){
        IncubatorBlock.FlushEssence(blockState,world,blockPos);
        IncubatorBlock.explode(world,blockPos);
        if (blockState.get(IncubatorBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
            world.removeBlock(blockPos,false);
            world.removeBlock(blockPos.up(),false);
        }
    }
    /*
    white = 1
    yellow = 2
    blue = 3
    pink = 4
    yellow blue = 5
    yellow pink = 6
    pink blue = 7
    */

    public int getDefectivity(){
        int defectiveness = Math.round(defectivityModifier);
        if (defectiveness < -1) {
            return 69;
        }
        if (defectiveness > 6) {
            return 6;
        } else if (defectiveness < 1) {
            return 1;
        }
        return defectiveness;
    }
}
