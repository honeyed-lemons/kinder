package phyner.kinder.blocks.entities;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phyner.kinder.KinderMod;
import phyner.kinder.blocks.IncubatorBlock;
import phyner.kinder.init.KinderBlocks;
import phyner.kinder.init.KinderConditions;
import phyner.kinder.init.KinderItems;
import phyner.kinder.util.GemColors;
import phyner.kinder.util.GemConditions;

import java.util.Map;
import java.util.Objects;

public class IncubatorBlockEntity extends AbstractIncubatingBlockEntity{
    public static int ticksElapsed;

    public IncubatorBlockEntity(BlockPos pos,BlockState state){
        super(KinderBlocks.INCUBATOR_BLOCK_ENTITY,pos,state);
    }
    public static void tick(World world,BlockPos pos,BlockState state,IncubatorBlockEntity blockEntity){
        {
            if (state.get(IncubatorBlock.COOKING).equals(true))
            {
                if (ticksElapsed >= 24000)
                {
                    world.setBlockState(pos,state.with(IncubatorBlock.COOKED,true).with(IncubatorBlock.COOKING,false));
                }
                else
                {
                    ticksElapsed++;
                }
            }
            if (state.get(IncubatorBlock.COOKING).equals(false) && ticksElapsed > 0)
            {
                ticksElapsed = 0;
            }
        }
    }
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("te", ticksElapsed);
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        ticksElapsed = nbt.getInt("te");
    }
    public Item GemItemStack(World world,BlockPos blockPos)
    {
        BlockState bs = world.getBlockState(blockPos);
        int essenceColor = IncubatorBlock.combineEssences(bs.get(IncubatorBlock.ESSENCESLOT1),bs.get(IncubatorBlock.ESSENCESLOT2));
        float highestScore = 0;
        Item highestScorerItemStack = KinderItems.RUBY_GEM;
        for (GemConditions gemConditions : KinderConditions.conditions())
        {
            for (Map.Entry<Item,GemColors> map : gemConditions.gems.entrySet())
            {
                float score = scoreGem(gemConditions,world,blockPos,essenceColor, testEssence(map.getValue()));
                if (score >= highestScore)
                {
                    highestScore = score;
                    highestScorerItemStack = map.getKey();
                    KinderMod.LOGGER.info(map.getKey().getTranslationKey()+" Score: "+score);
                }
            }
        }
        if (highestScore == 0)
        {
            IncubatorBlock.FlushEssence(bs,world,blockPos);
            IncubatorBlock.explode(world,blockPos);
            world.removeBlock(pos,false);
            return null;
        }
        KinderMod.LOGGER.info("Essence Color is "+essenceColor);
        KinderMod.LOGGER.info("Winmer is "+highestScorerItemStack.getTranslationKey());
        return highestScorerItemStack;
    }
    public static float scoreGem(GemConditions conditions,World world, BlockPos blockPos,int essenceColor, int gemColor)
    {
        float score = 0;
        if (conditions.biome!=null)
        {
            for(Map.Entry<String,Float> map : conditions.biome.entrySet()) {
                if (Objects.equals(getBiomeName(world,blockPos),map.getKey())) {
                    score += map.getValue();
                }
            }
        }
        if (getBiomeTemp(world,blockPos) >= conditions.tempMin && getBiomeTemp(world,blockPos) <= conditions.tempMax)
        {
            score += 1f;
            if (getBiomeTemp(world,blockPos) == conditions.tempIdeal)
            {
                score += 0.5f;
            }
        }
        if (score != 0)
        {
            if (blockPos.getY() >= conditions.depthMax && blockPos.getY() <= conditions.depthMin)
            {
                score += 1f;
            }
            else
            {
                score = 0;
            }
            if (score != 0)
            {
                score += world.random.nextFloat();
            }
        }
        if (essenceColor == gemColor)
        {
            return score;
        }
        return 0;
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

    /*
        gems.put(KinderItems.QUARTZ_GEM_0,GemColors.WHITE);
        gems.put(KinderItems.QUARTZ_GEM_1,GemColors.ORANGE);
        gems.put(KinderItems.QUARTZ_GEM_2,GemColors.MAGENTA);
        gems.put(KinderItems.QUARTZ_GEM_3,GemColors.LIGHT_BLUE);
        gems.put(KinderItems.QUARTZ_GEM_4,GemColors.YELLOW);
        gems.put(KinderItems.QUARTZ_GEM_5,GemColors.LIME);
        gems.put(KinderItems.QUARTZ_GEM_6,GemColors.PINK);
        gems.put(KinderItems.QUARTZ_GEM_7,GemColors.GRAY);
        gems.put(KinderItems.QUARTZ_GEM_8,GemColors.LIGHT_GRAY);
        gems.put(KinderItems.QUARTZ_GEM_9,GemColors.CYAN);
        gems.put(KinderItems.QUARTZ_GEM_10,GemColors.PURPLE);
        gems.put(KinderItems.QUARTZ_GEM_11,GemColors.BLUE);
        gems.put(KinderItems.QUARTZ_GEM_12,GemColors.BROWN);
        gems.put(KinderItems.QUARTZ_GEM_13,GemColors.GREEN);
        gems.put(KinderItems.QUARTZ_GEM_14,GemColors.RED);
        gems.put(KinderItems.QUARTZ_GEM_15,GemColors.BLACK);
    */
    public int testEssence(GemColors gemcolor)
    {
        int color = gemcolor.getId();
        switch (color) {
            case 0,7,8,15 -> {
                return 1;
            }
            case 1,12 -> {
                return 6;
            }
            case 2,10 -> {
                return 7;
            }
            case 3,11 -> {
                return 3;
            }
            case 4 -> {
                return 2;
            }
            case 5,9,13 -> {
                return 5;
            }
            case 6,14 -> {
                return 4;
            }
        }
        return 1;
    }
}
