package honeyedlemons.kinder.blocks.entities;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.blocks.IncubatorBlock;
import honeyedlemons.kinder.init.*;
import honeyedlemons.kinder.util.GemColors;
import honeyedlemons.kinder.util.GemConditions;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;

public class IncubatorBlockEntity extends AbstractIncubatingBlockEntity {
    public int ticksElapsed;
    public int ticksToSound = 0;
    public float defectivityModifier = 3;
    public int essenceSlot1;
    public int essenceSlot2;

    public IncubatorBlockEntity(BlockPos pos, BlockState state) {
        super(KinderBlocks.INCUBATOR_BLOCK_ENTITY, pos, state);
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, IncubatorBlockEntity blockEntity) {
        if (state.getValue(IncubatorBlock.COOKING).equals(true)) {
            if (blockEntity.ticksElapsed >= KinderMod.config.incubationtime) {
                world.setBlockAndUpdate(pos, state.setValue(IncubatorBlock.COOKED, true).setValue(IncubatorBlock.COOKING, false));
                blockEntity.ticksElapsed = 0;
            }
            if (blockEntity.ticksToSound <= 0) {
                world.gameEvent(null, GameEvent.BLOCK_ACTIVATE, pos);
            }
            if (blockEntity.ticksElapsed % 24 == 0) {
                drainBlock(world, pos, blockEntity);
            }
            blockEntity.ticksElapsed++;

        }
    }

    public static void clientTick(Level world, BlockPos pos, BlockState state, IncubatorBlockEntity blockEntity) {
        if (state.getValue(IncubatorBlock.COOKING).equals(true)) {
            if (blockEntity.ticksToSound <= 0) {
                KinderMod.LOGGER.info("Playing Sound");
                world.playLocalSound(pos, KinderSounds.INCUBATOR_SOUND, SoundSource.BLOCKS, 1f, 1f, false);
                blockEntity.ticksToSound = 42;
            }
            blockEntity.ticksToSound--;
        }
    }

    public static ItemStack GemItemStack(Level world, BlockPos blockPos, IncubatorBlockEntity blockEntity) {
        BlockState bs = world.getBlockState(blockPos);
        int essenceColor = IncubatorBlock.combineEssences(blockEntity.essenceSlot1, blockEntity.essenceSlot2);
        float highestScore = 0;
        Item highestScorerItem = KinderItems.RUBY_GEM;
        for (GemConditions gemConditions : KinderConditions.conditions()) {
            for (Map.Entry<Item, GemColors> map : gemConditions.gems.entrySet()) {
                float score = scoreGem(gemConditions, world, blockPos, essenceColor, testEssence(map.getValue()));
                if (score >= highestScore) {
                    highestScore = score;
                    highestScorerItem = map.getKey();
                    KinderMod.LOGGER.info(map.getKey().getDescriptionId() + " Score: " + score);
                }
            }
        }
        if (highestScore == 0) {
            blockEntity.explosion(bs, blockPos, world);
            return null;
        }
        ItemStack highestScorerItemStack = highestScorerItem.getDefaultInstance();
        KinderMod.LOGGER.info("Essence Color is " + essenceColor);
        int defectivity = blockEntity.getDefectivity();
        if (defectivity == 69) {
            blockEntity.explosion(bs, blockPos, world);
            return null;
        }
        KinderMod.LOGGER.info("Winmer is " + highestScorerItemStack.getDescriptionId() + ", with a defectivity of " + defectivity);
        highestScorerItemStack.getOrCreateTag().putInt("Perfection", defectivity);
        blockEntity.defectivityModifier = 3;
        return highestScorerItemStack;
    }

    public static float scoreGem(GemConditions conditions, Level world, BlockPos blockPos, int essenceColor, int gemColor) {
        float score = conditions.baseRarity;
        if (conditions.biome != null) {
            String biomeName = getBiomeName(world, blockPos);
            Float biomeScore = conditions.biome.get(biomeName);
            if (biomeScore != null) {
                score += biomeScore;
            }
        }
        float biomeTemp = getBiomeTemp(world, blockPos);
        if (biomeTemp >= conditions.tempMin && biomeTemp <= conditions.tempMax) {
            score += 1f;
            if (biomeTemp == conditions.tempIdeal) {
                score += 0.5f;
            }
        }
        int blockY = blockPos.getY();
        if (score != 0 && blockY >= conditions.depthMax && blockY <= conditions.depthMin) {
            score += 1f;
            score += world.random.nextFloat();
        } else {
            score = 0;
        }
        return essenceColor == gemColor ? score : 0;
    }


    public static void drainBlock(Level world, BlockPos pos, IncubatorBlockEntity blockEntity) {
        int randx = pos.getX() + world.random.nextIntBetweenInclusive(-3, 3);
        int randy = pos.getY() + world.random.nextIntBetweenInclusive(-3, 3);
        int randz = pos.getZ() + world.random.nextIntBetweenInclusive(-3, 3);
        BlockPos newBlockPos = new BlockPos(randx, randy, randz);
        BlockState blockState = world.getBlockState(newBlockPos);
        if (blockState.is(KinderBlockTags.DRAINABLE)) {
            BlockState drainedBlockState;
            float temp = getBiomeTemp(world, pos);
            if (temp >= 0.95f) {
                drainedBlockState = KinderBlocks.HOT_DRAINED_BLOCK.defaultBlockState();
            } else if (temp >= 0.45f) {
                drainedBlockState = KinderBlocks.TEMP_DRAINED_BLOCK.defaultBlockState();
            } else if (temp >= -1) {
                drainedBlockState = KinderBlocks.COLD_DRAINED_BLOCK.defaultBlockState();
            } else {
                drainedBlockState = KinderBlocks.TEMP_DRAINED_BLOCK.defaultBlockState();
            }
            KinderMod.LOGGER.info(world.dimensionType().toString());
            world.setBlockAndUpdate(newBlockPos, drainedBlockState);
            if (world.random.nextFloat() > 0.3) {
                for (Map.Entry<Block, Float> map1 : KinderConditions.cruxblocks().entrySet()) {
                    if (blockState.is(map1.getKey())) {
                        blockEntity.defectivityModifier += map1.getValue();
                    }
                }
                for (Map.Entry<TagKey<Block>, Float> map2 : KinderConditions.cruxtags().entrySet()) {
                    if (blockState.is(map2.getKey())) {
                        blockEntity.defectivityModifier += map2.getValue();
                    }
                }
            }
        }
    }

    public static int testEssence(GemColors gemcolor) {
        int color = gemcolor.getId();
        switch (color) {
            //White
            case 0, 7, 8, 15 -> {
                return 1;
            }
            //Yellow
            case 4 -> {
                return 2;
            }
            //Blue
            case 3, 11 -> {
                return 3;
            }
            //Pink
            case 6, 14 -> {
                return 4;
            }
            //Yellow Blue
            case 5, 9, 13 -> {
                return 5;
            }
            // Yellow Pink
            case 1, 12 -> {
                return 6;
            }
            // Pink Blue
            case 2, 10 -> {
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
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("te", ticksElapsed);
        nbt.putInt("es1", essenceSlot1);
        nbt.putInt("es2", essenceSlot2);
        nbt.putFloat("dm", defectivityModifier);
    }

    public void setEssenceSlot(int essenceSlot, int value) {
        if (essenceSlot == 0) {
            essenceSlot1 = value;
        } else {
            essenceSlot2 = value;
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        ticksElapsed = nbt.getInt("te");
        essenceSlot1 = nbt.getInt("es1");
        essenceSlot2 = nbt.getInt("es2");
        defectivityModifier = nbt.getFloat("dm");
    }

    public void explosion(BlockState blockState, BlockPos blockPos, Level world) {
        IncubatorBlock.FlushEssence(blockState, world, blockPos);
        IncubatorBlock.explode(world, blockPos);
        if (blockState.getValue(IncubatorBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
            world.removeBlock(blockPos, false);
            world.removeBlock(blockPos.above(), false);
        }
        world.gameEvent(null, GameEvent.EXPLODE, worldPosition);
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

    public int getDefectivity() {
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
