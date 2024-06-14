package phyner.kinder.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;

public class KinderItemGroups {
    private static final ItemGroup GEM_GROUP = FabricItemGroup.builder ().icon (() -> new ItemStack (KinderItems.RUBY_GEM)).displayName (Text.translatable ("itemGroup.kindergartening.gem_group")).entries ((context, entries) -> {
        entries.add (KinderItems.RUBY_GEM);
        entries.add (KinderItems.QUARTZ_GEM_0);
        entries.add (KinderItems.QUARTZ_GEM_1);
        entries.add (KinderItems.QUARTZ_GEM_2);
        entries.add (KinderItems.QUARTZ_GEM_3);
        entries.add (KinderItems.QUARTZ_GEM_4);
        entries.add (KinderItems.QUARTZ_GEM_5);
        entries.add (KinderItems.QUARTZ_GEM_6);
        entries.add (KinderItems.QUARTZ_GEM_7);
        entries.add (KinderItems.QUARTZ_GEM_8);
        entries.add (KinderItems.QUARTZ_GEM_9);
        entries.add (KinderItems.QUARTZ_GEM_10);
        entries.add (KinderItems.QUARTZ_GEM_11);
        entries.add (KinderItems.QUARTZ_GEM_12);
        entries.add (KinderItems.QUARTZ_GEM_13);
        entries.add (KinderItems.QUARTZ_GEM_14);
        entries.add (KinderItems.QUARTZ_GEM_15);
        entries.add (KinderItems.SAPPHIRE_GEM_0);
        entries.add (KinderItems.SAPPHIRE_GEM_1);
        entries.add (KinderItems.SAPPHIRE_GEM_2);
        entries.add (KinderItems.SAPPHIRE_GEM_3);
        entries.add (KinderItems.SAPPHIRE_GEM_4);
        entries.add (KinderItems.SAPPHIRE_GEM_5);
        entries.add (KinderItems.SAPPHIRE_GEM_6);
        entries.add (KinderItems.SAPPHIRE_GEM_7);
        entries.add (KinderItems.SAPPHIRE_GEM_8);
        entries.add (KinderItems.SAPPHIRE_GEM_9);
        entries.add (KinderItems.SAPPHIRE_GEM_10);
        entries.add (KinderItems.SAPPHIRE_GEM_11);
        entries.add (KinderItems.SAPPHIRE_GEM_12);
        entries.add (KinderItems.SAPPHIRE_GEM_13);
        entries.add (KinderItems.SAPPHIRE_GEM_15);
        entries.add (KinderItems.PEARL_GEM_0);
        entries.add (KinderItems.PEARL_GEM_1);
        entries.add (KinderItems.PEARL_GEM_2);
        entries.add (KinderItems.PEARL_GEM_3);
        entries.add (KinderItems.PEARL_GEM_4);
        entries.add (KinderItems.PEARL_GEM_5);
        entries.add (KinderItems.PEARL_GEM_6);
        entries.add (KinderItems.PEARL_GEM_7);
        entries.add (KinderItems.PEARL_GEM_8);
        entries.add (KinderItems.PEARL_GEM_9);
        entries.add (KinderItems.PEARL_GEM_10);
        entries.add (KinderItems.PEARL_GEM_11);
        entries.add (KinderItems.PEARL_GEM_12);
        entries.add (KinderItems.PEARL_GEM_13);
        entries.add (KinderItems.PEARL_GEM_14);
        entries.add (KinderItems.PEARL_GEM_15);
    }).build ();

    private static final ItemGroup MISC_GROUP = FabricItemGroup.builder ().icon (() -> new ItemStack (KinderBlocks.INCUBATOR_BLOCK.asItem ())).displayName (Text.translatable ("itemGroup.kindergartening.misc_group")).entries ((context, entries) -> {
        entries.add (KinderItems.PEARL_SHUCK);
        entries.add (KinderItems.PEARL_CUSTOMIZER);
        entries.add (KinderBlocks.OYSTER_BLOCK.asItem ());
        entries.add (KinderBlocks.INCUBATOR_BLOCK.asItem ());
        entries.add (KinderBlocks.COLD_DRAINED_BLOCK.asItem ());
        entries.add (KinderBlocks.TEMP_DRAINED_BLOCK.asItem ());
        entries.add (KinderBlocks.HOT_DRAINED_BLOCK.asItem ());
        entries.add (KinderItems.DRIED_GEM_SEEDS);
        entries.add (KinderItems.WHITE_GEM_SEEDS);
        entries.add (KinderBlocks.WHITE_GEM_CROP_FLOWER.asItem ());
        entries.add (KinderItems.YELLOW_GEM_SEEDS);
        entries.add (KinderBlocks.YELLOW_GEM_CROP_FLOWER.asItem ());
        entries.add (KinderItems.BLUE_GEM_SEEDS);
        entries.add (KinderBlocks.BLUE_GEM_CROP_FLOWER.asItem ());
        entries.add (KinderItems.PINK_GEM_SEEDS);
        entries.add (KinderBlocks.PINK_GEM_CROP_FLOWER.asItem ());
        entries.add (KinderItems.WHITE_ESSENCE);
        entries.add (KinderItems.YELLOW_ESSENCE);
        entries.add (KinderItems.BLUE_ESSENCE);
        entries.add (KinderItems.PINK_ESSENCE);
        entries.add (KinderItems.REJUVENATOR);
        entries.add (KinderItems.LIGHT_REACTOR);
        entries.add (KinderItems.LIGHT_REACTOR_BLUEPRINT);
    }).build ();

    public static void registerItemGroups (){
        Registry.register (Registries.ITEM_GROUP, new Identifier (KinderMod.MOD_ID, "misc_group"), MISC_GROUP);
        Registry.register (Registries.ITEM_GROUP, new Identifier (KinderMod.MOD_ID, "gem_group"), GEM_GROUP);

    }
}
