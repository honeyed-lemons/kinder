package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.items.DestabItem;
import honeyedlemons.kinder.items.GemItem;
import honeyedlemons.kinder.items.PearlCustomizerItem;
import honeyedlemons.kinder.util.GemColors;
import honeyedlemons.kinder.util.RegistryUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import java.util.ArrayList;

import static net.minecraft.world.item.Items.BUCKET;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;

@SuppressWarnings ("UnstableApiUsage")
public class KinderItems {
    public static final Item RUBY_GEM = new GemItem(KinderGemEntities.RUBY, GemColors.RED, gemSettings());

    public static final Item QUARTZ_GEM_0 = new GemItem(KinderGemEntities.QUARTZ, GemColors.WHITE, gemSettings());
    public static final Item QUARTZ_GEM_1 = new GemItem(KinderGemEntities.QUARTZ, GemColors.ORANGE, gemSettings());
    public static final Item QUARTZ_GEM_2 = new GemItem(KinderGemEntities.QUARTZ, GemColors.MAGENTA, gemSettings());
    public static final Item QUARTZ_GEM_3 = new GemItem(KinderGemEntities.QUARTZ, GemColors.LIGHT_BLUE, gemSettings());
    public static final Item QUARTZ_GEM_4 = new GemItem(KinderGemEntities.QUARTZ, GemColors.YELLOW, gemSettings());
    public static final Item QUARTZ_GEM_5 = new GemItem(KinderGemEntities.QUARTZ, GemColors.LIME, gemSettings());
    public static final Item QUARTZ_GEM_6 = new GemItem(KinderGemEntities.QUARTZ, GemColors.PINK, gemSettings());
    public static final Item QUARTZ_GEM_7 = new GemItem(KinderGemEntities.QUARTZ, GemColors.GRAY, gemSettings());
    public static final Item QUARTZ_GEM_8 = new GemItem(KinderGemEntities.QUARTZ, GemColors.LIGHT_GRAY, gemSettings());
    public static final Item QUARTZ_GEM_9 = new GemItem(KinderGemEntities.QUARTZ, GemColors.CYAN, gemSettings());
    public static final Item QUARTZ_GEM_10 = new GemItem(KinderGemEntities.QUARTZ, GemColors.PURPLE, gemSettings());
    public static final Item QUARTZ_GEM_11 = new GemItem(KinderGemEntities.QUARTZ, GemColors.BLUE, gemSettings());
    public static final Item QUARTZ_GEM_12 = new GemItem(KinderGemEntities.QUARTZ, GemColors.BROWN, gemSettings());
    public static final Item QUARTZ_GEM_13 = new GemItem(KinderGemEntities.QUARTZ, GemColors.GREEN, gemSettings());
    public static final Item QUARTZ_GEM_14 = new GemItem(KinderGemEntities.QUARTZ, GemColors.RED, gemSettings());
    public static final Item QUARTZ_GEM_15 = new GemItem(KinderGemEntities.QUARTZ, GemColors.BLACK, gemSettings());
    public static final Item SAPPHIRE_GEM_0 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.WHITE, gemSettings());
    public static final Item SAPPHIRE_GEM_1 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.ORANGE, gemSettings());
    public static final Item SAPPHIRE_GEM_2 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.MAGENTA, gemSettings());
    public static final Item SAPPHIRE_GEM_3 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.LIGHT_BLUE, gemSettings());
    public static final Item SAPPHIRE_GEM_4 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.YELLOW, gemSettings());
    public static final Item SAPPHIRE_GEM_5 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.LIME, gemSettings());
    public static final Item SAPPHIRE_GEM_6 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.PINK, gemSettings());
    public static final Item SAPPHIRE_GEM_7 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.GRAY, gemSettings());
    public static final Item SAPPHIRE_GEM_8 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.LIGHT_GRAY, gemSettings());
    public static final Item SAPPHIRE_GEM_9 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.CYAN, gemSettings());
    public static final Item SAPPHIRE_GEM_10 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.PURPLE, gemSettings());
    public static final Item SAPPHIRE_GEM_11 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.BLUE, gemSettings());
    public static final Item SAPPHIRE_GEM_12 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.BROWN, gemSettings());
    public static final Item SAPPHIRE_GEM_13 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.GREEN, gemSettings());
    public static final Item SAPPHIRE_GEM_15 = new GemItem(KinderGemEntities.SAPPHIRE, GemColors.BLACK, gemSettings());

    public static final Item PEARL_GEM_0 = new GemItem(KinderGemEntities.PEARL, GemColors.WHITE, gemSettings());
    public static final Item PEARL_GEM_1 = new GemItem(KinderGemEntities.PEARL, GemColors.ORANGE, gemSettings());
    public static final Item PEARL_GEM_2 = new GemItem(KinderGemEntities.PEARL, GemColors.MAGENTA, gemSettings());
    public static final Item PEARL_GEM_3 = new GemItem(KinderGemEntities.PEARL, GemColors.LIGHT_BLUE, gemSettings());
    public static final Item PEARL_GEM_4 = new GemItem(KinderGemEntities.PEARL, GemColors.YELLOW, gemSettings());
    public static final Item PEARL_GEM_5 = new GemItem(KinderGemEntities.PEARL, GemColors.LIME, gemSettings());
    public static final Item PEARL_GEM_6 = new GemItem(KinderGemEntities.PEARL, GemColors.PINK, gemSettings());
    public static final Item PEARL_GEM_7 = new GemItem(KinderGemEntities.PEARL, GemColors.GRAY, gemSettings());
    public static final Item PEARL_GEM_8 = new GemItem(KinderGemEntities.PEARL, GemColors.LIGHT_GRAY, gemSettings());
    public static final Item PEARL_GEM_9 = new GemItem(KinderGemEntities.PEARL, GemColors.CYAN, gemSettings());
    public static final Item PEARL_GEM_10 = new GemItem(KinderGemEntities.PEARL, GemColors.PURPLE, gemSettings());
    public static final Item PEARL_GEM_11 = new GemItem(KinderGemEntities.PEARL, GemColors.BLUE, gemSettings());
    public static final Item PEARL_GEM_12 = new GemItem(KinderGemEntities.PEARL, GemColors.BROWN, gemSettings());
    public static final Item PEARL_GEM_13 = new GemItem(KinderGemEntities.PEARL, GemColors.GREEN, gemSettings());
    public static final Item PEARL_GEM_14 = new GemItem(KinderGemEntities.PEARL, GemColors.RED, gemSettings());
    public static final Item PEARL_GEM_15 = new GemItem(KinderGemEntities.PEARL, GemColors.BLACK, gemSettings());

    public static final Item WHITE_ESSENCE_BOTTLE = new Item(essenceBottleSettings());
    public static final Item WHITE_ESSENCE_BUCKET = new BucketItem(KinderFluidHandling.STILL_WHITE_ESSENCE, essenceBucketSettings());
    public static final Item PINK_ESSENCE_BOTTLE = new Item(essenceBottleSettings());
    public static final Item PINK_ESSENCE_BUCKET = new BucketItem(KinderFluidHandling.STILL_PINK_ESSENCE, essenceBucketSettings());

    public static final Item BLUE_ESSENCE_BOTTLE = new Item(essenceBottleSettings());
    public static final Item BLUE_ESSENCE_BUCKET = new BucketItem(KinderFluidHandling.STILL_BLUE_ESSENCE, essenceBucketSettings());

    public static final Item YELLOW_ESSENCE_BOTTLE = new Item(essenceBottleSettings());
    public static final Item YELLOW_ESSENCE_BUCKET = new BucketItem(KinderFluidHandling.STILL_YELLOW_ESSENCE, essenceBucketSettings());
    public static final Item PEARL_SHUCK = new Item(new FabricItemSettings().stacksTo(1).durability(64));
    public static final Item DRIED_GEM_SEEDS = new Item(new FabricItemSettings().stacksTo(1));
    public static final Item WHITE_GEM_SEEDS = new ItemNameBlockItem(KinderBlocks.WHITE_GEM_CROP_BLOCK, new FabricItemSettings());
    public static final Item YELLOW_GEM_SEEDS = new ItemNameBlockItem(KinderBlocks.YELLOW_GEM_CROP_BLOCK, new FabricItemSettings());
    public static final Item BLUE_GEM_SEEDS = new ItemNameBlockItem(KinderBlocks.BLUE_GEM_CROP_BLOCK, new FabricItemSettings());
    public static final Item PINK_GEM_SEEDS = new ItemNameBlockItem(KinderBlocks.PINK_GEM_CROP_BLOCK, new FabricItemSettings());
    public static final Item PEARL_CUSTOMIZER = new PearlCustomizerItem(new FabricItemSettings().stacksTo(1));
    public static final Item REJUVENATOR = new DestabItem(new FabricItemSettings().stacksTo(1).durability(64));
    public static final Item LIGHT_REACTOR_BLUEPRINT = new Item(new FabricItemSettings());
    public static final Item LIGHT_REACTOR = new Item(new FabricItemSettings());

    public static void registerFluidItems() {
        FluidStorage.combinedItemApiProvider(GLASS_BOTTLE).register(context ->
                new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(WHITE_ESSENCE_BOTTLE), KinderFluidHandling.STILL_WHITE_ESSENCE, FluidConstants.BOTTLE)
        );
        FluidStorage.combinedItemApiProvider(WHITE_ESSENCE_BOTTLE).register(context ->
                new FullItemFluidStorage(context, bucket -> ItemVariant.of(GLASS_BOTTLE), FluidVariant.of(KinderFluidHandling.STILL_WHITE_ESSENCE), FluidConstants.BOTTLE)
        );
        FluidStorage.combinedItemApiProvider(GLASS_BOTTLE).register(context ->
                new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(YELLOW_ESSENCE_BOTTLE), KinderFluidHandling.STILL_YELLOW_ESSENCE, FluidConstants.BOTTLE)
        );
        FluidStorage.combinedItemApiProvider(YELLOW_ESSENCE_BOTTLE).register(context ->
                new FullItemFluidStorage(context, bucket -> ItemVariant.of(GLASS_BOTTLE), FluidVariant.of(KinderFluidHandling.STILL_YELLOW_ESSENCE), FluidConstants.BOTTLE)
        );
        FluidStorage.combinedItemApiProvider(GLASS_BOTTLE).register(context ->
                new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(PINK_ESSENCE_BOTTLE), KinderFluidHandling.STILL_PINK_ESSENCE, FluidConstants.BOTTLE)
        );
        FluidStorage.combinedItemApiProvider(PINK_ESSENCE_BOTTLE).register(context ->
                new FullItemFluidStorage(context, bucket -> ItemVariant.of(GLASS_BOTTLE), FluidVariant.of(KinderFluidHandling.STILL_PINK_ESSENCE), FluidConstants.BOTTLE)
        );
        FluidStorage.combinedItemApiProvider(GLASS_BOTTLE).register(context ->
                new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(BLUE_ESSENCE_BOTTLE), KinderFluidHandling.STILL_BLUE_ESSENCE, FluidConstants.BOTTLE)
        );
        FluidStorage.combinedItemApiProvider(BLUE_ESSENCE_BOTTLE).register(context ->
                new FullItemFluidStorage(context, bucket -> ItemVariant.of(GLASS_BOTTLE), FluidVariant.of(KinderFluidHandling.STILL_BLUE_ESSENCE), FluidConstants.BOTTLE)
        );
    }

    public static Item.Properties gemSettings() {
        return new FabricItemSettings().stacksTo(1).fireResistant();
    }

    public static Item.Properties essenceBottleSettings() {
        return new FabricItemSettings().stacksTo(16).craftRemainder(GLASS_BOTTLE);
    }

    public static Item.Properties essenceBucketSettings() {
        return new FabricItemSettings().stacksTo(1).craftRemainder(BUCKET);
    }

    public static ArrayList<RegistryUtil.ItemData> itemData() {
        ArrayList<RegistryUtil.ItemData> itemData = new ArrayList<>();

        itemData.add(new RegistryUtil.ItemData(RUBY_GEM, "ruby_gem", true));

        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_0, "quartz_gem_0", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_1, "quartz_gem_1", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_2, "quartz_gem_2", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_3, "quartz_gem_3", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_4, "quartz_gem_4", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_5, "quartz_gem_5", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_6, "quartz_gem_6", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_7, "quartz_gem_7", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_8, "quartz_gem_8", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_9, "quartz_gem_9", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_10, "quartz_gem_10", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_11, "quartz_gem_11", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_12, "quartz_gem_12", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_13, "quartz_gem_13", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_14, "quartz_gem_14", true));
        itemData.add(new RegistryUtil.ItemData(QUARTZ_GEM_15, "quartz_gem_15", true));

        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_0, "sapphire_gem_0", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_1, "sapphire_gem_1", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_2, "sapphire_gem_2", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_3, "sapphire_gem_3", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_4, "sapphire_gem_4", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_5, "sapphire_gem_5", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_6, "sapphire_gem_6", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_7, "sapphire_gem_7", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_8, "sapphire_gem_8", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_9, "sapphire_gem_9", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_10, "sapphire_gem_10", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_11, "sapphire_gem_11", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_12, "sapphire_gem_12", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_13, "sapphire_gem_13", true));
        itemData.add(new RegistryUtil.ItemData(SAPPHIRE_GEM_15, "sapphire_gem_15", true));

        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_0, "pearl_gem_0", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_1, "pearl_gem_1", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_2, "pearl_gem_2", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_3, "pearl_gem_3", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_4, "pearl_gem_4", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_5, "pearl_gem_5", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_6, "pearl_gem_6", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_7, "pearl_gem_7", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_8, "pearl_gem_8", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_9, "pearl_gem_9", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_10, "pearl_gem_10", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_11, "pearl_gem_11", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_12, "pearl_gem_12", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_13, "pearl_gem_13", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_14, "pearl_gem_14", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_GEM_15, "pearl_gem_15", true));

        itemData.add(new RegistryUtil.ItemData(WHITE_ESSENCE_BOTTLE, "white_essence_bottle", true));
        itemData.add(new RegistryUtil.ItemData(YELLOW_ESSENCE_BOTTLE, "yellow_essence_bottle", true));
        itemData.add(new RegistryUtil.ItemData(BLUE_ESSENCE_BOTTLE, "blue_essence_bottle", true));
        itemData.add(new RegistryUtil.ItemData(PINK_ESSENCE_BOTTLE, "pink_essence_bottle", true));
        itemData.add(new RegistryUtil.ItemData(WHITE_ESSENCE_BUCKET, "white_essence_bucket", true));
        itemData.add(new RegistryUtil.ItemData(YELLOW_ESSENCE_BUCKET, "yellow_essence_bucket", true));
        itemData.add(new RegistryUtil.ItemData(BLUE_ESSENCE_BUCKET, "blue_essence_bucket", true));
        itemData.add(new RegistryUtil.ItemData(PINK_ESSENCE_BUCKET, "pink_essence_bucket", true));

        itemData.add(new RegistryUtil.ItemData(PEARL_SHUCK, "pearl_shuck", true));
        itemData.add(new RegistryUtil.ItemData(PEARL_CUSTOMIZER, "pearl_customizer", true));
        itemData.add(new RegistryUtil.ItemData(DRIED_GEM_SEEDS, "dried_gem_seeds", true));

        itemData.add(new RegistryUtil.ItemData(WHITE_GEM_SEEDS, "white_gem_seeds", true));
        itemData.add(new RegistryUtil.ItemData(YELLOW_GEM_SEEDS, "yellow_gem_seeds", true));
        itemData.add(new RegistryUtil.ItemData(BLUE_GEM_SEEDS, "blue_gem_seeds", true));
        itemData.add(new RegistryUtil.ItemData(PINK_GEM_SEEDS, "pink_gem_seeds", true));

        itemData.add(new RegistryUtil.ItemData(LIGHT_REACTOR_BLUEPRINT, "light_reactor_blueprint", true));
        itemData.add(new RegistryUtil.ItemData(LIGHT_REACTOR, "light_reactor", true));

        itemData.add(new RegistryUtil.ItemData(REJUVENATOR, "rejuvenator", false));

        return itemData;
    }

    public static void registerItems() {
        for (RegistryUtil.ItemData itemData : itemData()) {
            Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KinderMod.MOD_ID, itemData.item_id()), itemData.item());
        }
    }
}
