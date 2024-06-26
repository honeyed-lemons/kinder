package honeyedlemons.kinder.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractIncubatingBlockEntity extends BlockEntity {
    public AbstractIncubatingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static float getBiomeTemp(Level world, BlockPos blockPos) {
        Biome biome = getBiome(world, blockPos);
        float temp = biome.getBaseTemperature();
        String biomeName = getBiomeName(world, blockPos);
        switch (biomeName) {
            case "minecraft:warm_ocean" -> temp = 1.8f;
            case "minecraft:lukewarm_ocean" -> temp = 1.6f;
            case "minecraft:deep_lukewarm_ocean" -> temp = 1.3f;
            case "minecraft:deep_ocean" -> temp = 0.9f;
            case "minecraft:ocean" -> temp = 1.1f;
            case "minecraft:cold_ocean" -> temp = 0.7f;
            case "minecraft:deep_cold_ocean" -> temp = 0.6f;
            case "minecraft:frozen_ocean" -> temp = 0.4f;
            case "minecraft:deep_frozen_ocean" -> temp = -0.7f;
        }
        return temp;
    }

    public static Biome getBiome(Level world, BlockPos blockPos) {
        return world.getBiome(blockPos).value();
    }

    public static String getBiomeName(Level world, BlockPos blockPos) {
        return world.getBiome(blockPos).unwrap().map(biomeKey -> biomeKey.location().toString(), biome_ -> "[unregistered " + biome_ + "]");
    }

    public static float getDownfall(Level world, BlockPos blockPos) {
        String biomeName = getBiomeName(world, blockPos);
        float downfall;
        downfall = getBiome(world, blockPos).climateSettings.downfall();
        switch (biomeName) {
            case "minecraft:warm_ocean", "minecraft:lukewarm_ocean", "minecraft:deep_lukewarm_ocean", "minecraft:deep_ocean", "minecraft:ocean", "minecraft:cold_ocean", "minecraft:deep_cold_ocean", "minecraft:frozen_ocean", "minecraft:deep_frozen_ocean", "minecraft:river", "minecraft:frozen_river" ->
                    downfall = 1.2f;
        }
        return downfall;
    }
}
