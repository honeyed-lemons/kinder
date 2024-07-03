package honeyedlemons.kinder.client.render.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.gems.PearlEntity;
import honeyedlemons.kinder.util.GemColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class GemHatLayer<T extends PearlEntity> extends GeoRenderLayer<T> {
    public GemHatLayer(GeoRenderer<T> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(PoseStack poseStack, T gem, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        ResourceLocation texture;
        float[] insigniaColors = GemColors.getDyedColor(GemColors.byId(gem.getInsigniaColor()));
        if (gem.getHatVariant() != 0) {
            texture = new ResourceLocation(KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType().toShortString() + "/hats/hat_" + gem.getHatVariant() + ".png");
        } else {
            texture = new ResourceLocation(KinderMod.MOD_ID, "textures/entity/blank.png");
        }
        RenderType armorRenderType = RenderType.entityTranslucent(texture);
        getRenderer().reRender(getDefaultBakedModel(gem), poseStack, bufferSource, gem, armorRenderType, bufferSource.getBuffer(armorRenderType), partialTick, packedLight, packedOverlay, insigniaColors[0], insigniaColors[1], insigniaColors[2], 1);
    }
}