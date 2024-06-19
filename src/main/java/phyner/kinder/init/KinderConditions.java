package phyner.kinder.init;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import phyner.kinder.util.GemColors;
import phyner.kinder.util.GemConditions;

import java.util.ArrayList;
import java.util.HashMap;

public class KinderConditions {
    public static ArrayList<GemConditions> conditions (){
        ArrayList<GemConditions> map = new ArrayList<> ();
        map.add (RubyConditions ());
        map.add (QuartzConditions ());
        map.add (SapphireConditions());
        return map;
    }

    public static HashMap<TagKey<Block>, Float> cruxtags (){
        HashMap<TagKey<Block>, Float> cruxes = new HashMap<> ();
        cruxes.put (BlockTags.COAL_ORES, 0.1f);
        cruxes.put (BlockTags.IRON_ORES, 0.15f);
        cruxes.put (BlockTags.COPPER_ORES, 0.1f);
        cruxes.put (BlockTags.LAPIS_ORES, 0.175f);
        cruxes.put (BlockTags.DIAMOND_ORES, 0.3f);
        cruxes.put (BlockTags.REDSTONE_ORES, 0.175f);
        cruxes.put (BlockTags.GOLD_ORES, 0.2f);
        cruxes.put (BlockTags.DIRT, -0.4f);
        cruxes.put (BlockTags.TERRACOTTA, 0.1f);
        return cruxes;
    }

    public static HashMap<Block, Float> cruxblocks (){
        HashMap<Block, Float> cruxes = new HashMap<> ();
        cruxes.put (KinderBlocks.HOT_DRAINED_BLOCK, -0.4f);
        cruxes.put (KinderBlocks.TEMP_DRAINED_BLOCK, -0.4f);
        cruxes.put (KinderBlocks.COLD_DRAINED_BLOCK, -0.4f);
        cruxes.put (Blocks.GRAVEL, -0.1f);
        cruxes.put (Blocks.SCULK, 0.25f);
        cruxes.put (Blocks.CLAY, 0.1f);
        cruxes.put (Blocks.PACKED_ICE, 0.1f);
        cruxes.put (Blocks.BLUE_ICE, 0.2f);
        return cruxes;
    }

    public static GemConditions RubyConditions (){
        float baseRarity = 0;
        float tempMin = 0.5f;
        float tempIdeal = 2.0f;
        float tempMax = 2.0f;
        float depthMax = -255;
        float depthMin = 100;
        HashMap<String, Float> biomes = new HashMap<> ();
        biomes.put ("minecraft:nether_wastes", 1f);
        biomes.put ("minecraft:warped_forest", 1f);
        biomes.put ("minecraft:crimson_forest", 1f);
        biomes.put ("minecraft:basalt_deltas", 1f);
        biomes.put ("minecraft:soul_sand_valley", 1f);
        HashMap<Item, GemColors> gem = new HashMap<> ();
        gem.put (KinderItems.RUBY_GEM, GemColors.RED);
        return new GemConditions (baseRarity, tempMin, tempIdeal, tempMax, depthMin, depthMax, biomes, gem);
    }

    public static GemConditions QuartzConditions (){
        float baseRarity = -0.3f;
        float tempMin = 0.0f;
        float tempIdeal = 0.8f;
        float tempMax = 2.0f;
        float depthMax = -255;
        float depthMin = 90;
        HashMap<String, Float> biomes = new HashMap<> ();
        HashMap<Item, GemColors> gems = new HashMap<> () {};
        gems.put (KinderItems.QUARTZ_GEM_0, GemColors.WHITE);
        gems.put (KinderItems.QUARTZ_GEM_1, GemColors.ORANGE);
        gems.put (KinderItems.QUARTZ_GEM_2, GemColors.MAGENTA);
        gems.put (KinderItems.QUARTZ_GEM_3, GemColors.LIGHT_BLUE);
        gems.put (KinderItems.QUARTZ_GEM_4, GemColors.YELLOW);
        gems.put (KinderItems.QUARTZ_GEM_5, GemColors.LIME);
        gems.put (KinderItems.QUARTZ_GEM_6, GemColors.PINK);
        gems.put (KinderItems.QUARTZ_GEM_7, GemColors.GRAY);
        gems.put (KinderItems.QUARTZ_GEM_8, GemColors.LIGHT_GRAY);
        gems.put (KinderItems.QUARTZ_GEM_9, GemColors.CYAN);
        gems.put (KinderItems.QUARTZ_GEM_10, GemColors.PURPLE);
        gems.put (KinderItems.QUARTZ_GEM_11, GemColors.BLUE);
        gems.put (KinderItems.QUARTZ_GEM_12, GemColors.BROWN);
        gems.put (KinderItems.QUARTZ_GEM_13, GemColors.GREEN);
        gems.put (KinderItems.QUARTZ_GEM_14, GemColors.RED);
        gems.put (KinderItems.QUARTZ_GEM_15, GemColors.BLACK);
        return new GemConditions (baseRarity, tempMin, tempIdeal, tempMax, depthMin, depthMax, biomes, gems);
    }

    public static GemConditions SapphireConditions (){
        float baseRarity = -0.6f;
        float tempMin = -1.0f;
        float tempIdeal = -0.5f;
        float tempMax = 0.05f;
        float depthMax = -255;
        float depthMin = 70;
        HashMap<String, Float> biomes = new HashMap<> ();

        HashMap<Item, GemColors> gems = new HashMap<> () {};
        gems.put (KinderItems.SAPPHIRE_GEM_0, GemColors.WHITE);
        gems.put (KinderItems.SAPPHIRE_GEM_1, GemColors.ORANGE);
        gems.put (KinderItems.SAPPHIRE_GEM_2, GemColors.MAGENTA);
        gems.put (KinderItems.SAPPHIRE_GEM_3, GemColors.LIGHT_BLUE);
        gems.put (KinderItems.SAPPHIRE_GEM_4, GemColors.YELLOW);
        gems.put (KinderItems.SAPPHIRE_GEM_5, GemColors.LIME);
        gems.put (KinderItems.SAPPHIRE_GEM_6, GemColors.PINK);
        gems.put (KinderItems.SAPPHIRE_GEM_7, GemColors.GRAY);
        gems.put (KinderItems.SAPPHIRE_GEM_8, GemColors.LIGHT_GRAY);
        gems.put (KinderItems.SAPPHIRE_GEM_9, GemColors.CYAN);
        gems.put (KinderItems.SAPPHIRE_GEM_10, GemColors.PURPLE);
        gems.put (KinderItems.SAPPHIRE_GEM_11, GemColors.BLUE);
        gems.put (KinderItems.SAPPHIRE_GEM_12, GemColors.BROWN);
        gems.put (KinderItems.SAPPHIRE_GEM_13, GemColors.GREEN);
        gems.put (KinderItems.SAPPHIRE_GEM_15, GemColors.BLACK);
        return new GemConditions (baseRarity, tempMin, tempIdeal, tempMax, depthMin, depthMax, biomes, gems);
    }
}

