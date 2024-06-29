package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class KinderItemGroups {
    private static final CreativeModeTab GEM_GROUP = FabricItemGroup.builder().icon(() -> new ItemStack(KinderItems.RUBY_GEM)).title(Component.translatable("itemGroup.kinder.gem_group")).displayItems((context, entries) -> {
        entries.accept(KinderItems.RUBY_GEM);
        entries.accept(KinderItems.QUARTZ_GEM_0);
        entries.accept(KinderItems.QUARTZ_GEM_1);
        entries.accept(KinderItems.QUARTZ_GEM_2);
        entries.accept(KinderItems.QUARTZ_GEM_3);
        entries.accept(KinderItems.QUARTZ_GEM_4);
        entries.accept(KinderItems.QUARTZ_GEM_5);
        entries.accept(KinderItems.QUARTZ_GEM_6);
        entries.accept(KinderItems.QUARTZ_GEM_7);
        entries.accept(KinderItems.QUARTZ_GEM_8);
        entries.accept(KinderItems.QUARTZ_GEM_9);
        entries.accept(KinderItems.QUARTZ_GEM_10);
        entries.accept(KinderItems.QUARTZ_GEM_11);
        entries.accept(KinderItems.QUARTZ_GEM_12);
        entries.accept(KinderItems.QUARTZ_GEM_13);
        entries.accept(KinderItems.QUARTZ_GEM_14);
        entries.accept(KinderItems.QUARTZ_GEM_15);
        entries.accept(KinderItems.SAPPHIRE_GEM_0);
        entries.accept(KinderItems.SAPPHIRE_GEM_1);
        entries.accept(KinderItems.SAPPHIRE_GEM_2);
        entries.accept(KinderItems.SAPPHIRE_GEM_3);
        entries.accept(KinderItems.SAPPHIRE_GEM_4);
        entries.accept(KinderItems.SAPPHIRE_GEM_5);
        entries.accept(KinderItems.SAPPHIRE_GEM_6);
        entries.accept(KinderItems.SAPPHIRE_GEM_7);
        entries.accept(KinderItems.SAPPHIRE_GEM_8);
        entries.accept(KinderItems.SAPPHIRE_GEM_9);
        entries.accept(KinderItems.SAPPHIRE_GEM_10);
        entries.accept(KinderItems.SAPPHIRE_GEM_11);
        entries.accept(KinderItems.SAPPHIRE_GEM_12);
        entries.accept(KinderItems.SAPPHIRE_GEM_13);
        entries.accept(KinderItems.SAPPHIRE_GEM_15);
        entries.accept(KinderItems.PEARL_GEM_0);
        entries.accept(KinderItems.PEARL_GEM_1);
        entries.accept(KinderItems.PEARL_GEM_2);
        entries.accept(KinderItems.PEARL_GEM_3);
        entries.accept(KinderItems.PEARL_GEM_4);
        entries.accept(KinderItems.PEARL_GEM_5);
        entries.accept(KinderItems.PEARL_GEM_6);
        entries.accept(KinderItems.PEARL_GEM_7);
        entries.accept(KinderItems.PEARL_GEM_8);
        entries.accept(KinderItems.PEARL_GEM_9);
        entries.accept(KinderItems.PEARL_GEM_10);
        entries.accept(KinderItems.PEARL_GEM_11);
        entries.accept(KinderItems.PEARL_GEM_12);
        entries.accept(KinderItems.PEARL_GEM_13);
        entries.accept(KinderItems.PEARL_GEM_14);
        entries.accept(KinderItems.PEARL_GEM_15);
    }).build();

    private static final CreativeModeTab MISC_GROUP = FabricItemGroup.builder().icon(() -> new ItemStack(KinderBlocks.INCUBATOR_BLOCK.asItem())).title(Component.translatable("itemGroup.kinder.misc_group")).displayItems((context, entries) -> {
        entries.accept(KinderItems.PEARL_SHUCK);
        entries.accept(KinderItems.PEARL_CUSTOMIZER);
        entries.accept(KinderBlocks.OYSTER_BLOCK.asItem());
        entries.accept(KinderBlocks.INCUBATOR_BLOCK.asItem());
        entries.accept(KinderBlocks.COLD_DRAINED_BLOCK.asItem());
        entries.accept(KinderBlocks.TEMP_DRAINED_BLOCK.asItem());
        entries.accept(KinderBlocks.HOT_DRAINED_BLOCK.asItem());
        entries.accept(KinderItems.DRIED_GEM_SEEDS);
        entries.accept(KinderItems.WHITE_GEM_SEEDS);
        entries.accept(KinderBlocks.WHITE_GEM_CROP_FLOWER.asItem());
        entries.accept(KinderItems.YELLOW_GEM_SEEDS);
        entries.accept(KinderBlocks.YELLOW_GEM_CROP_FLOWER.asItem());
        entries.accept(KinderItems.BLUE_GEM_SEEDS);
        entries.accept(KinderBlocks.BLUE_GEM_CROP_FLOWER.asItem());
        entries.accept(KinderItems.PINK_GEM_SEEDS);
        entries.accept(KinderBlocks.PINK_GEM_CROP_FLOWER.asItem());
        entries.accept(KinderItems.WHITE_ESSENCE_BOTTLE);
        entries.accept(KinderItems.YELLOW_ESSENCE_BOTTLE);
        entries.accept(KinderItems.BLUE_ESSENCE_BOTTLE);
        entries.accept(KinderItems.PINK_ESSENCE_BOTTLE);
        entries.accept(KinderItems.WHITE_ESSENCE_BUCKET);
        entries.accept(KinderItems.YELLOW_ESSENCE_BUCKET);
        entries.accept(KinderItems.BLUE_ESSENCE_BUCKET);
        entries.accept(KinderItems.PINK_ESSENCE_BUCKET);
        entries.accept(KinderItems.REJUVENATOR);
        entries.accept(KinderItems.LIGHT_REACTOR);
    }).build();

    public static void registerItemGroups() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(KinderMod.MOD_ID, "misc_group"), MISC_GROUP);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(KinderMod.MOD_ID, "gem_group"), GEM_GROUP);

    }
}
