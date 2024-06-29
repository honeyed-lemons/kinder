package honeyedlemons.kinder.client.render.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractGemEntity;
import honeyedlemons.kinder.util.GemPlacements;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.awt.*;

@Environment (value = EnvType.CLIENT)
public class GemPearlNoseLayer<T extends AbstractGemEntity> extends GeoRenderLayer<T> {
    public GemPearlNoseLayer(GeoRenderer<T> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(PoseStack poseStack, T gem, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        ResourceLocation TEXTURE = new ResourceLocation(KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType().toShortString() + "/nose.png");
        Color skinColor = new Color(gem.getSkinColor());
        float r = (float) skinColor.getRed() / 255;
        float b = (float) skinColor.getBlue() / 255;
        float g = (float) skinColor.getGreen() / 255;
        RenderType armorRenderType = RenderType.entityCutoutNoCull(TEXTURE);
        if (gem.getGemPlacement() != GemPlacements.NOSE) {
            getRenderer().reRender(getDefaultBakedModel(gem), poseStack, bufferSource, gem, armorRenderType, bufferSource.getBuffer(armorRenderType), partialTick, packedLight, packedOverlay, r, g, b, 1);
        }
    }
}