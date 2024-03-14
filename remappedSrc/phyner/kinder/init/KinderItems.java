package phyner.kinder.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.items.GemItem;
import phyner.kinder.util.GemColors;

public class KinderItems {
    public static final Item RUBY_GEM = new GemItem(KinderGemEntities.RUBY,GemColors.RED,gemSettings());
    public static final Item QUARTZ_GEM_0 = new GemItem(KinderGemEntities.QUARTZ,GemColors.WHITE,gemSettings());
    public static final Item QUARTZ_GEM_1 = new GemItem(KinderGemEntities.QUARTZ,GemColors.ORANGE,gemSettings());
    public static final Item QUARTZ_GEM_2 = new GemItem(KinderGemEntities.QUARTZ,GemColors.MAGENTA,gemSettings());
    public static final Item QUARTZ_GEM_3 = new GemItem(KinderGemEntities.QUARTZ,GemColors.LIGHT_BLUE,gemSettings());
    public static final Item QUARTZ_GEM_4 = new GemItem(KinderGemEntities.QUARTZ,GemColors.YELLOW,gemSettings());
    public static final Item QUARTZ_GEM_5 = new GemItem(KinderGemEntities.QUARTZ,GemColors.LIME,gemSettings());
    public static final Item QUARTZ_GEM_6 = new GemItem(KinderGemEntities.QUARTZ,GemColors.PINK,gemSettings());
    public static final Item QUARTZ_GEM_7 = new GemItem(KinderGemEntities.QUARTZ,GemColors.GRAY,gemSettings());
    public static final Item QUARTZ_GEM_8 = new GemItem(KinderGemEntities.QUARTZ,GemColors.LIGHT_GRAY,gemSettings());
    public static final Item QUARTZ_GEM_9 = new GemItem(KinderGemEntities.QUARTZ,GemColors.CYAN,gemSettings());
    public static final Item QUARTZ_GEM_10 = new GemItem(KinderGemEntities.QUARTZ,GemColors.PURPLE,gemSettings());
    public static final Item QUARTZ_GEM_11 = new GemItem(KinderGemEntities.QUARTZ,GemColors.BLUE,gemSettings());
    public static final Item QUARTZ_GEM_12 = new GemItem(KinderGemEntities.QUARTZ,GemColors.BROWN,gemSettings());
    public static final Item QUARTZ_GEM_13 = new GemItem(KinderGemEntities.QUARTZ,GemColors.GREEN,gemSettings());
    public static final Item QUARTZ_GEM_14 = new GemItem(KinderGemEntities.QUARTZ,GemColors.RED,gemSettings());
    public static final Item QUARTZ_GEM_15 = new GemItem(KinderGemEntities.QUARTZ,GemColors.BLACK,gemSettings());
    public static final Item PEARL_GEM_0 = new GemItem(KinderGemEntities.PEARL,GemColors.WHITE,gemSettings());
    public static final Item PEARL_GEM_1 = new GemItem(KinderGemEntities.PEARL,GemColors.ORANGE,gemSettings());
    public static final Item PEARL_GEM_2 = new GemItem(KinderGemEntities.PEARL,GemColors.MAGENTA,gemSettings());
    public static final Item PEARL_GEM_3 = new GemItem(KinderGemEntities.PEARL,GemColors.LIGHT_BLUE,gemSettings());
    public static final Item PEARL_GEM_4 = new GemItem(KinderGemEntities.PEARL,GemColors.YELLOW,gemSettings());
    public static final Item PEARL_GEM_5 = new GemItem(KinderGemEntities.PEARL,GemColors.LIME,gemSettings());
    public static final Item PEARL_GEM_6 = new GemItem(KinderGemEntities.PEARL,GemColors.PINK,gemSettings());
    public static final Item PEARL_GEM_7 = new GemItem(KinderGemEntities.PEARL,GemColors.GRAY,gemSettings());
    public static final Item PEARL_GEM_8 = new GemItem(KinderGemEntities.PEARL,GemColors.LIGHT_GRAY,gemSettings());
    public static final Item PEARL_GEM_9 = new GemItem(KinderGemEntities.PEARL,GemColors.CYAN,gemSettings());
    public static final Item PEARL_GEM_10 = new GemItem(KinderGemEntities.PEARL,GemColors.PURPLE,gemSettings());
    public static final Item PEARL_GEM_11 = new GemItem(KinderGemEntities.PEARL,GemColors.BLUE,gemSettings());
    public static final Item PEARL_GEM_12 = new GemItem(KinderGemEntities.PEARL,GemColors.BROWN,gemSettings());
    public static final Item PEARL_GEM_13 = new GemItem(KinderGemEntities.PEARL,GemColors.GREEN,gemSettings());
    public static final Item PEARL_GEM_14 = new GemItem(KinderGemEntities.PEARL,GemColors.RED,gemSettings());
    public static final Item PEARL_GEM_15 = new GemItem(KinderGemEntities.PEARL,GemColors.BLACK,gemSettings());

    public static FabricItemSettings gemSettings(){
        return new FabricItemSettings().maxCount(1).fireproof();
    }

    public static void registerItems(){
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"ruby_gem"),RUBY_GEM);

        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_0"),QUARTZ_GEM_0);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_1"),QUARTZ_GEM_1);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_2"),QUARTZ_GEM_2);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_3"),QUARTZ_GEM_3);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_4"),QUARTZ_GEM_4);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_5"),QUARTZ_GEM_5);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_6"),QUARTZ_GEM_6);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_7"),QUARTZ_GEM_7);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_8"),QUARTZ_GEM_8);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_9"),QUARTZ_GEM_9);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_10"),QUARTZ_GEM_10);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_11"),QUARTZ_GEM_11);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_12"),QUARTZ_GEM_12);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_13"),QUARTZ_GEM_13);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_14"),QUARTZ_GEM_14);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"quartz_gem_15"),QUARTZ_GEM_15);

        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_0"),PEARL_GEM_0);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_1"),PEARL_GEM_1);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_2"),PEARL_GEM_2);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_3"),PEARL_GEM_3);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_4"),PEARL_GEM_4);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_5"),PEARL_GEM_5);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_6"),PEARL_GEM_6);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_7"),PEARL_GEM_7);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_8"),PEARL_GEM_8);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_9"),PEARL_GEM_9);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_10"),PEARL_GEM_10);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_11"),PEARL_GEM_11);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_12"),PEARL_GEM_12);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_13"),PEARL_GEM_13);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_14"),PEARL_GEM_14);
        Registry.register(Registries.ITEM,new Identifier(KinderMod.MOD_ID,"pearl_gem_15"),PEARL_GEM_15);
    }
}
