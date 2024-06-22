package honeyedlemons.kinder.client.render.layers;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractGemEntity;
import honeyedlemons.kinder.util.GemPlacements;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.awt.*;

@Environment(value = EnvType.CLIENT)
public class GemPearlNoseLayer<T extends AbstractGemEntity> extends GeoRenderLayer<T> {
    public GemPearlNoseLayer (GeoRenderer<T> entityRenderer){
        super (entityRenderer);
    }

    @Override
    public void render (MatrixStack poseStack, T gem, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay){
        Identifier TEXTURE = new Identifier (KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType ().getUntranslatedName () + "/nose.png");
        Color skinColor = new Color (gem.getSkinColor ());
        float r = (float) skinColor.getRed () / 255;
        float b = (float) skinColor.getBlue () / 255;
        float g = (float) skinColor.getGreen () / 255;
        RenderLayer armorRenderType = RenderLayer.getEntityCutoutNoCull (TEXTURE);
        if (gem.getGemPlacement () != GemPlacements.NOSE) {
            getRenderer ().reRender (getDefaultBakedModel (gem), poseStack, bufferSource, gem, armorRenderType, bufferSource.getBuffer (armorRenderType), partialTick, packedLight, OverlayTexture.DEFAULT_UV, r, g, b, 1);
        }
    }
}