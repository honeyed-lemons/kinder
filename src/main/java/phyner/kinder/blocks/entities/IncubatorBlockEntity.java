package phyner.kinder.blocks.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phyner.kinder.KinderMod;
import phyner.kinder.init.KinderBlocks;
import phyner.kinder.init.KinderConditions;
import phyner.kinder.util.GemConditions;

import java.util.Map;
import java.util.Objects;

public class IncubatorBlockEntity extends AbstractIncubatingBlockEntity{
    public IncubatorBlockEntity(BlockPos pos,BlockState state){
        super(KinderBlocks.INCUBATOR_BLOCK_ENTITY,pos,state);
    }

    public EntityType help(World world, BlockPos blockPos)
    {
        float highestScore = 0;
        EntityType highestScorer = null;
        for (Map.Entry<EntityType, GemConditions> map : KinderConditions.GemConditions().entrySet())
        {
            float score = scoreGem(map.getValue(),world,blockPos);
            if (score >= highestScore)
            {
                highestScore = score;
                highestScorer = map.getKey();
            }
            KinderMod.LOGGER.info(map.getKey().getTranslationKey()+" "+score);
        }
        assert highestScorer != null;
        KinderMod.LOGGER.info("Winder is "+highestScorer.getTranslationKey());
        return highestScorer;
    }
    public static float scoreGem(GemConditions conditions,World world, BlockPos blockPos)
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
        return score;
    }
}
