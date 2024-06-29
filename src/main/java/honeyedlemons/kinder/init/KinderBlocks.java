package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.blocks.IncubatorBlock;
import honeyedlemons.kinder.blocks.OysterBlock;
import honeyedlemons.kinder.blocks.cropblocks.BlueGemCropBlock;
import honeyedlemons.kinder.blocks.cropblocks.PinkGemCropBlock;
import honeyedlemons.kinder.blocks.cropblocks.WhiteGemCropBlock;
import honeyedlemons.kinder.blocks.cropblocks.YellowGemCropBlock;
import honeyedlemons.kinder.blocks.entities.IncubatorBlockEntity;
import honeyedlemons.kinder.blocks.entities.OysterBlockEntity;
import honeyedlemons.kinder.util.RegistryUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import java.util.ArrayList;

public class KinderBlocks {
    public static final OysterBlock OYSTER_BLOCK = new OysterBlock(FabricBlockSettings.create().strength(2.0f, 30.0f).noOcclusion().sound(SoundType.STONE));
    public static final IncubatorBlock INCUBATOR_BLOCK = new IncubatorBlock(FabricBlockSettings.create().strength(2.0f, 30.0f).noOcclusion().lightLevel(Blocks.litBlockEmission(6)).sound(SoundType.ANVIL));
    public static final Block COLD_DRAINED_BLOCK = new Block(FabricBlockSettings.create().requiresCorrectToolForDrops().strength(1.5F).sound(SoundType.STONE));
    public static final Block TEMP_DRAINED_BLOCK = new Block(FabricBlockSettings.create().requiresCorrectToolForDrops().strength(1.5F).sound(SoundType.STONE));
    public static final Block HOT_DRAINED_BLOCK = new Block(FabricBlockSettings.create().requiresCorrectToolForDrops().strength(1.5F).sound(SoundType.STONE));

    public static final Block WHITE_GEM_CROP_BLOCK = new WhiteGemCropBlock(cropSettings());
    public static final Block WHITE_GEM_CROP_FLOWER = new FlowerBlock(MobEffects.BLINDNESS, 1200, flowerSettings());
    public static final Block YELLOW_GEM_CROP_BLOCK = new YellowGemCropBlock(cropSettings());
    public static final Block YELLOW_GEM_CROP_FLOWER = new FlowerBlock(MobEffects.WEAKNESS, 1200, flowerSettings());
    public static final Block BLUE_GEM_CROP_BLOCK = new BlueGemCropBlock(cropSettings());
    public static final Block BLUE_GEM_CROP_FLOWER = new FlowerBlock(MobEffects.CONFUSION, 1200, flowerSettings());
    public static final Block PINK_GEM_CROP_BLOCK = new PinkGemCropBlock(cropSettings());
    public static final Block PINK_GEM_CROP_FLOWER = new FlowerBlock(MobEffects.POISON, 1200, flowerSettings());

    public static BlockBehaviour.Properties cropSettings() {
        return FabricBlockSettings.create().noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY);
    }    public static final BlockEntityType<IncubatorBlockEntity> INCUBATOR_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(KinderMod.MOD_ID, "incubator_block_entity"), FabricBlockEntityTypeBuilder.create(IncubatorBlockEntity::new, INCUBATOR_BLOCK).build());

    public static BlockBehaviour.Properties flowerSettings() {
        return FabricBlockSettings.create().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY);
    }

    public static void registerBlocks() {
        for (RegistryUtil.BlockData blockData : blockData()) {
            Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KinderMod.MOD_ID, blockData.block_id()), blockData.block());
            if (blockData.item()) {
                Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KinderMod.MOD_ID, blockData.block_id()), new BlockItem(blockData.block(), new FabricItemSettings()));
            }
        }
    }

    public static ArrayList<RegistryUtil.BlockData> blockData() {
        ArrayList<RegistryUtil.BlockData> blockData = new ArrayList<>();
        blockData.add(new RegistryUtil.BlockData(OYSTER_BLOCK, "oyster", true));
        blockData.add(new RegistryUtil.BlockData(INCUBATOR_BLOCK, "incubator", true));

        blockData.add(new RegistryUtil.BlockData(HOT_DRAINED_BLOCK, "hot_drained_block", true));
        blockData.add(new RegistryUtil.BlockData(TEMP_DRAINED_BLOCK, "temp_drained_block", true));
        blockData.add(new RegistryUtil.BlockData(COLD_DRAINED_BLOCK, "cold_drained_block", true));

        blockData.add(new RegistryUtil.BlockData(WHITE_GEM_CROP_BLOCK, "white_gem_crop", false));
        blockData.add(new RegistryUtil.BlockData(WHITE_GEM_CROP_FLOWER, "white_gem_crop_flower", true));
        blockData.add(new RegistryUtil.BlockData(YELLOW_GEM_CROP_BLOCK, "yellow_gem_crop", false));
        blockData.add(new RegistryUtil.BlockData(YELLOW_GEM_CROP_FLOWER, "yellow_gem_crop_flower", true));
        blockData.add(new RegistryUtil.BlockData(BLUE_GEM_CROP_BLOCK, "blue_gem_crop", false));
        blockData.add(new RegistryUtil.BlockData(BLUE_GEM_CROP_FLOWER, "blue_gem_crop_flower", true));
        blockData.add(new RegistryUtil.BlockData(PINK_GEM_CROP_BLOCK, "pink_gem_crop", false));
        blockData.add(new RegistryUtil.BlockData(PINK_GEM_CROP_FLOWER, "pink_gem_crop_flower", true));

        blockData.add(new RegistryUtil.BlockData(KinderFluidHandling.WHITE_ESSENCE_BLOCK, "white_essence_block", false));
        blockData.add(new RegistryUtil.BlockData(KinderFluidHandling.YELLOW_ESSENCE_BLOCK, "yellow_essence_block", false));
        blockData.add(new RegistryUtil.BlockData(KinderFluidHandling.BLUE_ESSENCE_BLOCK, "blue_essence_block", false));
        blockData.add(new RegistryUtil.BlockData(KinderFluidHandling.PINK_ESSENCE_BLOCK, "pink_essence_block", false));

        return blockData;
    }    public static final BlockEntityType<OysterBlockEntity> OYSTER_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(KinderMod.MOD_ID, "oyster_block_entity"), FabricBlockEntityTypeBuilder.create(OysterBlockEntity::new, OYSTER_BLOCK).build());

    @Environment (EnvType.CLIENT)
    public static void setBlockRender() {
        BlockRenderLayerMap.INSTANCE.putBlock(KinderBlocks.INCUBATOR_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(KinderBlocks.WHITE_GEM_CROP_BLOCK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(KinderBlocks.WHITE_GEM_CROP_FLOWER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(KinderBlocks.YELLOW_GEM_CROP_BLOCK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(KinderBlocks.YELLOW_GEM_CROP_FLOWER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(KinderBlocks.BLUE_GEM_CROP_BLOCK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(KinderBlocks.BLUE_GEM_CROP_FLOWER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(KinderBlocks.PINK_GEM_CROP_BLOCK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(KinderBlocks.PINK_GEM_CROP_FLOWER, RenderType.cutout());
    }






}
