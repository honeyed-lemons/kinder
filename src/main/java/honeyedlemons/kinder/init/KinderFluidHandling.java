package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.blocks.EssenceFluidBlock;
import honeyedlemons.kinder.fluids.BlueEssenceFluid;
import honeyedlemons.kinder.fluids.PinkEssenceFluid;
import honeyedlemons.kinder.fluids.WhiteEssenceFluid;
import honeyedlemons.kinder.fluids.YellowEssenceFluid;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class KinderFluidHandling {

    public static FlowableFluid STILL_WHITE_ESSENCE = new WhiteEssenceFluid.Still();
    public static FlowableFluid FLOWING_WHITE_ESSENCE = new WhiteEssenceFluid.Flowing();
    public static Block WHITE_ESSENCE_BLOCK = new EssenceFluidBlock(STILL_WHITE_ESSENCE,FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.WHITE));

    public static FlowableFluid STILL_YELLOW_ESSENCE = new YellowEssenceFluid.Still();
    public static FlowableFluid FLOWING_YELLOW_ESSENCE = new YellowEssenceFluid.Flowing();
    public static Block YELLOW_ESSENCE_BLOCK = new EssenceFluidBlock(STILL_YELLOW_ESSENCE,FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.YELLOW));

    public static FlowableFluid STILL_PINK_ESSENCE = new PinkEssenceFluid.Still();
    public static FlowableFluid FLOWING_PINK_ESSENCE = new PinkEssenceFluid.Flowing();
    public static Block PINK_ESSENCE_BLOCK = new EssenceFluidBlock(STILL_PINK_ESSENCE,FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.PINK));

    public static FlowableFluid STILL_BLUE_ESSENCE = new BlueEssenceFluid.Still();
    public static FlowableFluid FLOWING_BLUE_ESSENCE = new BlueEssenceFluid.Flowing();
    public static Block BLUE_ESSENCE_BLOCK = new EssenceFluidBlock(STILL_BLUE_ESSENCE,FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.BLUE));

    public static void registerFluids()
    {
        Registry.register(Registries.FLUID,new Identifier(KinderMod.MOD_ID, "still_white_essence"),STILL_WHITE_ESSENCE);
        Registry.register(Registries.FLUID,new Identifier(KinderMod.MOD_ID, "flowing_white_essence"),FLOWING_WHITE_ESSENCE);
        Registry.register(Registries.FLUID,new Identifier(KinderMod.MOD_ID, "still_yellow_essence"),STILL_YELLOW_ESSENCE);
        Registry.register(Registries.FLUID,new Identifier(KinderMod.MOD_ID, "flowing_yellow_essence"),FLOWING_YELLOW_ESSENCE);
        Registry.register(Registries.FLUID,new Identifier(KinderMod.MOD_ID, "still_pink_essence"),STILL_PINK_ESSENCE);
        Registry.register(Registries.FLUID,new Identifier(KinderMod.MOD_ID, "flowing_pink_essence"),FLOWING_PINK_ESSENCE);
        Registry.register(Registries.FLUID,new Identifier(KinderMod.MOD_ID, "still_blue_essence"),STILL_BLUE_ESSENCE);
        Registry.register(Registries.FLUID,new Identifier(KinderMod.MOD_ID, "flowing_blue_essence"),FLOWING_BLUE_ESSENCE);
    }
    public static void setupFluidRendering(final Fluid still, final Fluid flowing, final Identifier textureBase) {
        final Identifier stillTexture = new Identifier(KinderMod.MOD_ID, "block/fluids/" + textureBase.getPath() + "_still");
        final Identifier flowingTexture = new Identifier(KinderMod.MOD_ID, "block/fluids/" + textureBase.getPath() + "_flow");

        FluidRenderHandler handler = new SimpleFluidRenderHandler(stillTexture, flowingTexture);
        FluidRenderHandlerRegistry.INSTANCE.register(still, handler);
        FluidRenderHandlerRegistry.INSTANCE.register(flowing, handler);
    }

    public static void fluidRendering()
    {
        setupFluidRendering(STILL_WHITE_ESSENCE,FLOWING_WHITE_ESSENCE, new Identifier("white_essence"));
        setupFluidRendering(STILL_YELLOW_ESSENCE,FLOWING_YELLOW_ESSENCE, new Identifier("yellow_essence"));
        setupFluidRendering(STILL_PINK_ESSENCE,FLOWING_PINK_ESSENCE, new Identifier("pink_essence"));
        setupFluidRendering(STILL_BLUE_ESSENCE,FLOWING_BLUE_ESSENCE, new Identifier("blue_essence"));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),STILL_WHITE_ESSENCE,FLOWING_WHITE_ESSENCE);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),STILL_YELLOW_ESSENCE,FLOWING_YELLOW_ESSENCE);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),STILL_PINK_ESSENCE,FLOWING_PINK_ESSENCE);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),STILL_BLUE_ESSENCE,FLOWING_BLUE_ESSENCE);


    }
}
