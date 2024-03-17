package phyner.kinder.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.items.GemItem;
import phyner.kinder.items.PearlCustomizerItem;
import phyner.kinder.util.GemColors;

import java.util.HashMap;
import java.util.Map;

public class KinderItems {
    public static final Item RUBY_GEM = new GemItem (KinderGemEntities.RUBY, GemColors.RED, gemSettings ());

    public static final Item QUARTZ_GEM_0 = new GemItem (KinderGemEntities.QUARTZ, GemColors.WHITE, gemSettings ());
    public static final Item QUARTZ_GEM_1 = new GemItem (KinderGemEntities.QUARTZ, GemColors.ORANGE, gemSettings ());
    public static final Item QUARTZ_GEM_2 = new GemItem (KinderGemEntities.QUARTZ, GemColors.MAGENTA, gemSettings ());
    public static final Item QUARTZ_GEM_3 = new GemItem (KinderGemEntities.QUARTZ, GemColors.LIGHT_BLUE, gemSettings ());
    public static final Item QUARTZ_GEM_4 = new GemItem (KinderGemEntities.QUARTZ, GemColors.YELLOW, gemSettings ());
    public static final Item QUARTZ_GEM_5 = new GemItem (KinderGemEntities.QUARTZ, GemColors.LIME, gemSettings ());
    public static final Item QUARTZ_GEM_6 = new GemItem (KinderGemEntities.QUARTZ, GemColors.PINK, gemSettings ());
    public static final Item QUARTZ_GEM_7 = new GemItem (KinderGemEntities.QUARTZ, GemColors.GRAY, gemSettings ());
    public static final Item QUARTZ_GEM_8 = new GemItem (KinderGemEntities.QUARTZ, GemColors.LIGHT_GRAY, gemSettings ());
    public static final Item QUARTZ_GEM_9 = new GemItem (KinderGemEntities.QUARTZ, GemColors.CYAN, gemSettings ());
    public static final Item QUARTZ_GEM_10 = new GemItem (KinderGemEntities.QUARTZ, GemColors.PURPLE, gemSettings ());
    public static final Item QUARTZ_GEM_11 = new GemItem (KinderGemEntities.QUARTZ, GemColors.BLUE, gemSettings ());
    public static final Item QUARTZ_GEM_12 = new GemItem (KinderGemEntities.QUARTZ, GemColors.BROWN, gemSettings ());
    public static final Item QUARTZ_GEM_13 = new GemItem (KinderGemEntities.QUARTZ, GemColors.GREEN, gemSettings ());
    public static final Item QUARTZ_GEM_14 = new GemItem (KinderGemEntities.QUARTZ, GemColors.RED, gemSettings ());
    public static final Item QUARTZ_GEM_15 = new GemItem (KinderGemEntities.QUARTZ, GemColors.BLACK, gemSettings ());

    public static final Item PEARL_GEM_0 = new GemItem (KinderGemEntities.PEARL, GemColors.WHITE, gemSettings ());
    public static final Item PEARL_GEM_1 = new GemItem (KinderGemEntities.PEARL, GemColors.ORANGE, gemSettings ());
    public static final Item PEARL_GEM_2 = new GemItem (KinderGemEntities.PEARL, GemColors.MAGENTA, gemSettings ());
    public static final Item PEARL_GEM_3 = new GemItem (KinderGemEntities.PEARL, GemColors.LIGHT_BLUE, gemSettings ());
    public static final Item PEARL_GEM_4 = new GemItem (KinderGemEntities.PEARL, GemColors.YELLOW, gemSettings ());
    public static final Item PEARL_GEM_5 = new GemItem (KinderGemEntities.PEARL, GemColors.LIME, gemSettings ());
    public static final Item PEARL_GEM_6 = new GemItem (KinderGemEntities.PEARL, GemColors.PINK, gemSettings ());
    public static final Item PEARL_GEM_7 = new GemItem (KinderGemEntities.PEARL, GemColors.GRAY, gemSettings ());
    public static final Item PEARL_GEM_8 = new GemItem (KinderGemEntities.PEARL, GemColors.LIGHT_GRAY, gemSettings ());
    public static final Item PEARL_GEM_9 = new GemItem (KinderGemEntities.PEARL, GemColors.CYAN, gemSettings ());
    public static final Item PEARL_GEM_10 = new GemItem (KinderGemEntities.PEARL, GemColors.PURPLE, gemSettings ());
    public static final Item PEARL_GEM_11 = new GemItem (KinderGemEntities.PEARL, GemColors.BLUE, gemSettings ());
    public static final Item PEARL_GEM_12 = new GemItem (KinderGemEntities.PEARL, GemColors.BROWN, gemSettings ());
    public static final Item PEARL_GEM_13 = new GemItem (KinderGemEntities.PEARL, GemColors.GREEN, gemSettings ());
    public static final Item PEARL_GEM_14 = new GemItem (KinderGemEntities.PEARL, GemColors.RED, gemSettings ());
    public static final Item PEARL_GEM_15 = new GemItem (KinderGemEntities.PEARL, GemColors.BLACK, gemSettings ());

    public static final Item WHITE_ESSENCE = new Item (essenceSettings ());
    public static final Item PINK_ESSENCE = new Item (essenceSettings ());
    public static final Item BLUE_ESSENCE = new Item (essenceSettings ());
    public static final Item YELLOW_ESSENCE = new Item (essenceSettings ());

    public static final Item PEARL_SHUCK = new Item (new FabricItemSettings ().maxCount (1).maxDamage (32));

    public static final Item DRIED_GEM_SEEDS = new Item (new FabricItemSettings ().maxCount (1));

    public static final Item WHITE_GEM_SEEDS = new AliasedBlockItem (KinderBlocks.WHITE_GEM_CROP_BLOCK, new FabricItemSettings ());
    public static final Item YELLOW_GEM_SEEDS = new AliasedBlockItem (KinderBlocks.YELLOW_GEM_CROP_BLOCK, new FabricItemSettings ());
    public static final Item BLUE_GEM_SEEDS = new AliasedBlockItem (KinderBlocks.BLUE_GEM_CROP_BLOCK, new FabricItemSettings ());
    public static final Item PINK_GEM_SEEDS = new AliasedBlockItem (KinderBlocks.PINK_GEM_CROP_BLOCK, new FabricItemSettings ());
    public static final Item PEARL_CUSTOMIZER = new PearlCustomizerItem (new FabricItemSettings ().maxCount (1));


    public static FabricItemSettings gemSettings (){
        return new FabricItemSettings ().maxCount (1).fireproof ();
    }

    public static FabricItemSettings essenceSettings (){
        return new FabricItemSettings ().maxCount (16).recipeRemainder (Items.GLASS_BOTTLE);
    }

    public static HashMap<Item, String> Items (){
        HashMap<Item, String> hashmap = new HashMap<> ();
        //Ruby
        hashmap.put (RUBY_GEM, "ruby_gem");
        //Quartzes
        hashmap.put (QUARTZ_GEM_0, "quartz_gem_0");
        hashmap.put (QUARTZ_GEM_1, "quartz_gem_1");
        hashmap.put (QUARTZ_GEM_2, "quartz_gem_2");
        hashmap.put (QUARTZ_GEM_3, "quartz_gem_3");
        hashmap.put (QUARTZ_GEM_4, "quartz_gem_4");
        hashmap.put (QUARTZ_GEM_5, "quartz_gem_5");
        hashmap.put (QUARTZ_GEM_6, "quartz_gem_6");
        hashmap.put (QUARTZ_GEM_7, "quartz_gem_7");
        hashmap.put (QUARTZ_GEM_8, "quartz_gem_8");
        hashmap.put (QUARTZ_GEM_9, "quartz_gem_9");
        hashmap.put (QUARTZ_GEM_10, "quartz_gem_10");
        hashmap.put (QUARTZ_GEM_11, "quartz_gem_11");
        hashmap.put (QUARTZ_GEM_12, "quartz_gem_12");
        hashmap.put (QUARTZ_GEM_13, "quartz_gem_13");
        hashmap.put (QUARTZ_GEM_14, "quartz_gem_14");
        hashmap.put (QUARTZ_GEM_15, "quartz_gem_15");

        // Pearls
        hashmap.put (PEARL_GEM_0, "pearl_gem_0");
        hashmap.put (PEARL_GEM_1, "pearl_gem_1");
        hashmap.put (PEARL_GEM_2, "pearl_gem_2");
        hashmap.put (PEARL_GEM_3, "pearl_gem_3");
        hashmap.put (PEARL_GEM_4, "pearl_gem_4");
        hashmap.put (PEARL_GEM_5, "pearl_gem_5");
        hashmap.put (PEARL_GEM_6, "pearl_gem_6");
        hashmap.put (PEARL_GEM_7, "pearl_gem_7");
        hashmap.put (PEARL_GEM_8, "pearl_gem_8");
        hashmap.put (PEARL_GEM_9, "pearl_gem_9");
        hashmap.put (PEARL_GEM_10, "pearl_gem_10");
        hashmap.put (PEARL_GEM_11, "pearl_gem_11");
        hashmap.put (PEARL_GEM_12, "pearl_gem_12");
        hashmap.put (PEARL_GEM_13, "pearl_gem_13");
        hashmap.put (PEARL_GEM_14, "pearl_gem_14");
        hashmap.put (PEARL_GEM_15, "pearl_gem_15");
        // Essences
        hashmap.put (WHITE_ESSENCE, "white_essence");
        hashmap.put (YELLOW_ESSENCE, "yellow_essence");
        hashmap.put (BLUE_ESSENCE, "blue_essence");
        hashmap.put (PINK_ESSENCE, "pink_essence");

        // Tools
        hashmap.put (PEARL_SHUCK, "pearl_shuck");
        hashmap.put (PEARL_CUSTOMIZER, "pearl_customizer");
        // Seeds
        hashmap.put (DRIED_GEM_SEEDS, "dried_gem_seeds");

        hashmap.put (WHITE_GEM_SEEDS, "white_gem_seeds");
        hashmap.put (YELLOW_GEM_SEEDS, "yellow_gem_seeds");
        hashmap.put (BLUE_GEM_SEEDS, "blue_gem_seeds");
        hashmap.put (PINK_GEM_SEEDS, "pink_gem_seeds");

        return hashmap;
    }

    public static void registerItems (){
        for (Map.Entry<Item, String> entry : Items ().entrySet ()) {
            Item item = entry.getKey ();
            String strings = entry.getValue ();
            registerItem (strings, item);
        }
    }

    public static void registerItem (String id, Item item){
        Registry.register (Registries.ITEM, new Identifier (KinderMod.MOD_ID, id), item);
    }
}
