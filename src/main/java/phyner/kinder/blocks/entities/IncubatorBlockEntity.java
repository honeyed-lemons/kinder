package phyner.kinder.blocks.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phyner.kinder.KinderMod;
import phyner.kinder.blocks.IncubatorBlock;
import phyner.kinder.blocks.OysterBlock;
import phyner.kinder.init.KinderBlocks;
import phyner.kinder.init.KinderConditions;
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
    public EntityType gemEntityType(World world, BlockPos blockPos)
    {
        BlockState bs = world.getBlockState(blockPos);
        int essenceColor = IncubatorBlock.combineEssences(bs.get(IncubatorBlock.ESSENCESLOT1),bs.get(IncubatorBlock.ESSENCESLOT2));
        float highestScore = 0;
        EntityType highestScorer = null;
        for (Map.Entry<EntityType, GemConditions> map : KinderConditions.GemConditions().entrySet())
        {
            float score = scoreGem(map.getValue(),world,blockPos,essenceColor);
            if (score >= highestScore)
            {
                highestScore = score;
                highestScorer = map.getKey();
            }
            KinderMod.LOGGER.info(map.getKey().getTranslationKey()+" "+score);
        }
        assert highestScorer != null;
        KinderMod.LOGGER.info("Essence Color is "+essenceColor);
        KinderMod.LOGGER.info("Winmer is "+highestScorer.getTranslationKey());
        IncubatorBlock.FlushEssence(bs,world,pos);
        return highestScorer;
    }
    public static float scoreGem(GemConditions conditions,World world, BlockPos blockPos, int essenceColor)
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
        if (blockPos.getY() >= conditions.depthMax && blockPos.getY() <= conditions.depthMin)
        {
            score += 1f;
        }
        if (getBiomeTemp(world,blockPos) >= conditions.tempMin && getBiomeTemp(world,blockPos) <= conditions.tempMax)
        {
            score += 1f;
            if (getBiomeTemp(world,blockPos) == conditions.tempIdeal)
            {
                score += 0.5f;
            }
        }
        score += world.random.nextFloat();

        if (essenceColor == conditions.essenceColor)
        {
            return score;
        }
        return 0;
    }

}
