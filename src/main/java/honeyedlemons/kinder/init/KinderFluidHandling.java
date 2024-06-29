package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.blocks.EssenceFluidBlock;
import honeyedlemons.kinder.fluids.BlueEssenceFluid;
import honeyedlemons.kinder.fluids.PinkEssenceFluid;
import honeyedlemons.kinder.fluids.WhiteEssenceFluid;
import honeyedlemons.kinder.fluids.YellowEssenceFluid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;

public class KinderFluidHandling {

    public static FlowingFluid STILL_WHITE_ESSENCE = new WhiteEssenceFluid.Still();
    public static FlowingFluid FLOWING_WHITE_ESSENCE = new WhiteEssenceFluid.Flowing();
    public static Block WHITE_ESSENCE_BLOCK = new EssenceFluidBlock(STILL_WHITE_ESSENCE, FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.SNOW));

    public static FlowingFluid STILL_YELLOW_ESSENCE = new YellowEssenceFluid.Still();
    public static FlowingFluid FLOWING_YELLOW_ESSENCE = new YellowEssenceFluid.Flowing();
    public static Block YELLOW_ESSENCE_BLOCK = new EssenceFluidBlock(STILL_YELLOW_ESSENCE, FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.COLOR_YELLOW));

    public static FlowingFluid STILL_PINK_ESSENCE = new PinkEssenceFluid.Still();
    public static FlowingFluid FLOWING_PINK_ESSENCE = new PinkEssenceFluid.Flowing();
    public static Block PINK_ESSENCE_BLOCK = new EssenceFluidBlock(STILL_PINK_ESSENCE, FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.COLOR_PINK));

    public static FlowingFluid STILL_BLUE_ESSENCE = new BlueEssenceFluid.Still();
    public static FlowingFluid FLOWING_BLUE_ESSENCE = new BlueEssenceFluid.Flowing();
    public static Block BLUE_ESSENCE_BLOCK = new EssenceFluidBlock(STILL_BLUE_ESSENCE, FabricBlockSettings.copyOf(Blocks.WATER).mapColor(MapColor.COLOR_BLUE));

    public static void registerFluids() {
        Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(KinderMod.MOD_ID, "still_white_essence"), STILL_WHITE_ESSENCE);
        Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(KinderMod.MOD_ID, "flowing_white_essence"), FLOWING_WHITE_ESSENCE);
        Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(KinderMod.MOD_ID, "still_yellow_essence"), STILL_YELLOW_ESSENCE);
        Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(KinderMod.MOD_ID, "flowing_yellow_essence"), FLOWING_YELLOW_ESSENCE);
        Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(KinderMod.MOD_ID, "still_pink_essence"), STILL_PINK_ESSENCE);
        Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(KinderMod.MOD_ID, "flowing_pink_essence"), FLOWING_PINK_ESSENCE);
        Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(KinderMod.MOD_ID, "still_blue_essence"), STILL_BLUE_ESSENCE);
        Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(KinderMod.MOD_ID, "flowing_blue_essence"), FLOWING_BLUE_ESSENCE);
    }

    @Environment (EnvType.CLIENT)
    public static void setupFluidRendering(final Fluid still, final Fluid flowing, final ResourceLocation textureBase) {
        final ResourceLocation stillTexture = new ResourceLocation(KinderMod.MOD_ID, "block/fluids/" + textureBase.getPath() + "_still");
        final ResourceLocation flowingTexture = new ResourceLocation(KinderMod.MOD_ID, "block/fluids/" + textureBase.getPath() + "_flow");
        final ResourceLocation overlayTexture = new ResourceLocation(KinderMod.MOD_ID, "block/fluids/" + textureBase.getPath() + "_overlay");

        FluidRenderHandler handler = new SimpleFluidRenderHandler(stillTexture, flowingTexture, overlayTexture);

        FluidRenderHandlerRegistry.INSTANCE.register(still, handler);
        FluidRenderHandlerRegistry.INSTANCE.register(flowing, handler);
    }

    @Environment (EnvType.CLIENT)
    public static void fluidRendering() {
        setupFluidRendering(STILL_WHITE_ESSENCE, FLOWING_WHITE_ESSENCE, new ResourceLocation("white_essence"));
        setupFluidRendering(STILL_YELLOW_ESSENCE, FLOWING_YELLOW_ESSENCE, new ResourceLocation("yellow_essence"));
        setupFluidRendering(STILL_PINK_ESSENCE, FLOWING_PINK_ESSENCE, new ResourceLocation("pink_essence"));
        setupFluidRendering(STILL_BLUE_ESSENCE, FLOWING_BLUE_ESSENCE, new ResourceLocation("blue_essence"));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(), STILL_WHITE_ESSENCE, FLOWING_WHITE_ESSENCE);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(), STILL_YELLOW_ESSENCE, FLOWING_YELLOW_ESSENCE);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(), STILL_PINK_ESSENCE, FLOWING_PINK_ESSENCE);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(), STILL_BLUE_ESSENCE, FLOWING_BLUE_ESSENCE);


    }
}
