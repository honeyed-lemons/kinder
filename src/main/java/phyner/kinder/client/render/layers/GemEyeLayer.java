package phyner.kinder.client.render.layers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.AbstractGemEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.awt.*;

@Environment(value = EnvType.CLIENT) public class GemEyeLayer<T extends AbstractGemEntity> extends GeoRenderLayer<T> {
    public GemEyeLayer (GeoRenderer<T> entityRenderer){
        super (entityRenderer);
    }

    @Override
    public void render (MatrixStack poseStack, T gem, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay){
        Identifier texture;
        texture = new Identifier (KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType ().getUntranslatedName () + "/eyes.png");
        Color gemColor = new Color (gem.getGemColor ());
        float r = (float) gemColor.getRed () / 255;
        float b = (float) gemColor.getBlue () / 255;
        float g = (float) gemColor.getGreen () / 255;
        RenderLayer armorRenderType = RenderLayer.getEntityTranslucent (texture);
        getRenderer ().reRender (getDefaultBakedModel (gem), poseStack, bufferSource, gem, armorRenderType, bufferSource.getBuffer (armorRenderType), partialTick, packedLight, OverlayTexture.DEFAULT_UV, r, g, b, 1);
    }
}