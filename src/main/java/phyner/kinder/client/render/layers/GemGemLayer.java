package phyner.kinder.client.render.layers;

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

public class GemGemLayer<T extends AbstractGemEntity> extends GeoRenderLayer<T> {
    public GemGemLayer(GeoRenderer<T> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(MatrixStack poseStack, T gem, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        Identifier texture;
        if (gem.getGemPlacement() != null)
        {
            texture = new Identifier(KinderMod.MOD_ID, "textures/entity/gems/"+gem.getType().getUntranslatedName()+"/gems/gem_"+gem.getGemPlacement().name().toLowerCase()+".png");
        }
        else
        {
            texture = new Identifier(KinderMod.MOD_ID, "textures/entity/blank.png");
        }
        Color gemColor = new Color(gem.getGemColor());
        float r = (float) gemColor.getRed() / 255;
        float b = (float) gemColor.getBlue() / 255;
        float g = (float) gemColor.getGreen() / 255;
        RenderLayer armorRenderType = RenderLayer.getEntityCutoutNoCull(texture);
        getRenderer().reRender(getDefaultBakedModel(gem), poseStack, bufferSource, gem, armorRenderType,
                bufferSource.getBuffer(armorRenderType), partialTick, packedLight, OverlayTexture.DEFAULT_UV,
                r, g, b, 1);
    }
}