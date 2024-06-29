package honeyedlemons.kinder.client.render.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractGemEntity;
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
public class GemEyeLayer<T extends AbstractGemEntity> extends GeoRenderLayer<T> {
    public GemEyeLayer(GeoRenderer<T> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(PoseStack poseStack, T gem, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        ResourceLocation texture;
        texture = new ResourceLocation(KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType().toShortString() + "/eyes.png");
        Color gemColor = new Color(gem.getGemColor());
        float r = (float) gemColor.getRed() / 255;
        float b = (float) gemColor.getBlue() / 255;
        float g = (float) gemColor.getGreen() / 255;
        RenderType armorRenderType = RenderType.entityTranslucent(texture);
        getRenderer().reRender(getDefaultBakedModel(gem), poseStack, bufferSource, gem, armorRenderType, bufferSource.getBuffer(armorRenderType), partialTick, packedLight, packedOverlay, r, g, b, 1);
    }
}