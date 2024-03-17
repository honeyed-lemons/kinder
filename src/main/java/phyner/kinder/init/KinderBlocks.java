package phyner.kinder.init;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.blocks.IncubatorBlock;
import phyner.kinder.blocks.OysterBlock;
import phyner.kinder.blocks.cropblocks.BlueGemCropBlock;
import phyner.kinder.blocks.cropblocks.PinkGemCropBlock;
import phyner.kinder.blocks.cropblocks.WhiteGemCropBlock;
import phyner.kinder.blocks.cropblocks.YellowGemCropBlock;
import phyner.kinder.blocks.entities.IncubatorBlockEntity;
import phyner.kinder.blocks.entities.OysterBlockEntity;

public class KinderBlocks {
    public static final OysterBlock OYSTER_BLOCK = new OysterBlock (FabricBlockSettings.create ().strength (2.0f, 30.0f).nonOpaque ().sounds (BlockSoundGroup.STONE));
    public static final IncubatorBlock INCUBATOR_BLOCK = new IncubatorBlock (FabricBlockSettings.create ().strength (2.0f, 30.0f).nonOpaque ().luminance (Blocks.createLightLevelFromLitBlockState (6)).sounds (BlockSoundGroup.ANVIL));
    public static final Block COLD_DRAINED_BLOCK = new Block (FabricBlockSettings.create ().strength (1.5F).sounds (BlockSoundGroup.STONE));
    public static final Block TEMP_DRAINED_BLOCK = new Block (FabricBlockSettings.create ().strength (1.5F).sounds (BlockSoundGroup.STONE));
    public static final Block HOT_DRAINED_BLOCK = new Block (FabricBlockSettings.create ().strength (1.5F).sounds (BlockSoundGroup.STONE));

    public static final Block WHITE_GEM_CROP_BLOCK = new WhiteGemCropBlock (cropSettings ());
    public static final Block WHITE_GEM_CROP_FLOWER = new FlowerBlock (StatusEffects.BLINDNESS, 1200, flowerSettings ());
    public static final Block YELLOW_GEM_CROP_BLOCK = new YellowGemCropBlock (cropSettings ());
    public static final Block YELLOW_GEM_CROP_FLOWER = new FlowerBlock (StatusEffects.WEAKNESS, 1200, flowerSettings ());
    public static final Block BLUE_GEM_CROP_BLOCK = new BlueGemCropBlock (cropSettings ());
    public static final Block BLUE_GEM_CROP_FLOWER = new FlowerBlock (StatusEffects.NAUSEA, 1200, flowerSettings ());
    public static final Block PINK_GEM_CROP_BLOCK = new PinkGemCropBlock (cropSettings ());
    public static final Block PINK_GEM_CROP_FLOWER = new FlowerBlock (StatusEffects.POISON, 1200, flowerSettings ());

    public static FabricBlockSettings cropSettings (){
        return FabricBlockSettings.create ().noCollision ().ticksRandomly ().breakInstantly ().sounds (BlockSoundGroup.CROP).pistonBehavior (PistonBehavior.DESTROY);
    }

    public static FabricBlockSettings flowerSettings (){
        return FabricBlockSettings.create ().noCollision ().breakInstantly ().sounds (BlockSoundGroup.GRASS).offset (AbstractBlock.OffsetType.XZ).pistonBehavior (PistonBehavior.DESTROY);
    }    public static final BlockEntityType<OysterBlockEntity> OYSTER_BLOCK_ENTITY = Registry.register (Registries.BLOCK_ENTITY_TYPE, new Identifier (KinderMod.MOD_ID, "oyster_block_entity"), FabricBlockEntityTypeBuilder.create (OysterBlockEntity::new, OYSTER_BLOCK).build ());

    public static void registerBlock (Block block, String name){
        Registry.register (Registries.BLOCK, new Identifier (KinderMod.MOD_ID, name), block);
        Registry.register (Registries.ITEM, new Identifier (KinderMod.MOD_ID, name), new BlockItem (block, new FabricItemSettings ()));
    }

    public static void registerBlockNoItem (Block block, String name){
        Registry.register (Registries.BLOCK, new Identifier (KinderMod.MOD_ID, name), block);
    }    public static final BlockEntityType<IncubatorBlockEntity> INCUBATOR_BLOCK_ENTITY = Registry.register (Registries.BLOCK_ENTITY_TYPE, new Identifier (KinderMod.MOD_ID, "incubator_block_entity"), FabricBlockEntityTypeBuilder.create (IncubatorBlockEntity::new, INCUBATOR_BLOCK).build ());

    public static void registerBlocks (){
        registerBlock (OYSTER_BLOCK, "oyster");
        registerBlock (INCUBATOR_BLOCK, "incubator");

        registerBlock (HOT_DRAINED_BLOCK, "hot_drained_block");
        registerBlock (TEMP_DRAINED_BLOCK, "temp_drained_block");
        registerBlock (COLD_DRAINED_BLOCK, "cold_drained_block");

        registerBlockNoItem (WHITE_GEM_CROP_BLOCK, "white_gem_crop");
        registerBlock (WHITE_GEM_CROP_FLOWER, "white_gem_crop_flower");
        registerBlockNoItem (YELLOW_GEM_CROP_BLOCK, "yellow_gem_crop");
        registerBlock (YELLOW_GEM_CROP_FLOWER, "yellow_gem_crop_flower");
        registerBlockNoItem (BLUE_GEM_CROP_BLOCK, "blue_gem_crop");
        registerBlock (BLUE_GEM_CROP_FLOWER, "blue_gem_crop_flower");
        registerBlockNoItem (PINK_GEM_CROP_BLOCK, "pink_gem_crop");
        registerBlock (PINK_GEM_CROP_FLOWER, "pink_gem_crop_flower");
    }

    public static void setBlockRender (){
        BlockRenderLayerMap.INSTANCE.putBlock (KinderBlocks.INCUBATOR_BLOCK, RenderLayer.getTranslucent ());
        BlockRenderLayerMap.INSTANCE.putBlock (KinderBlocks.WHITE_GEM_CROP_BLOCK, RenderLayer.getCutout ());
        BlockRenderLayerMap.INSTANCE.putBlock (KinderBlocks.WHITE_GEM_CROP_FLOWER, RenderLayer.getCutout ());
        BlockRenderLayerMap.INSTANCE.putBlock (KinderBlocks.YELLOW_GEM_CROP_BLOCK, RenderLayer.getCutout ());
        BlockRenderLayerMap.INSTANCE.putBlock (KinderBlocks.YELLOW_GEM_CROP_FLOWER, RenderLayer.getCutout ());
        BlockRenderLayerMap.INSTANCE.putBlock (KinderBlocks.BLUE_GEM_CROP_BLOCK, RenderLayer.getCutout ());
        BlockRenderLayerMap.INSTANCE.putBlock (KinderBlocks.BLUE_GEM_CROP_FLOWER, RenderLayer.getCutout ());
        BlockRenderLayerMap.INSTANCE.putBlock (KinderBlocks.PINK_GEM_CROP_BLOCK, RenderLayer.getCutout ());
        BlockRenderLayerMap.INSTANCE.putBlock (KinderBlocks.PINK_GEM_CROP_FLOWER, RenderLayer.getCutout ());
    }






}
