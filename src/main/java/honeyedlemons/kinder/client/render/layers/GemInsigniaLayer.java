package honeyedlemons.kinder.client.render.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractGemEntity;
import honeyedlemons.kinder.util.GemColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class GemInsigniaLayer<T extends AbstractGemEntity> extends GeoRenderLayer<T> {
    public GemInsigniaLayer(GeoRenderer<T> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(PoseStack poseStack, T gem, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        ResourceLocation texture;
        float[] insigniaColors = GemColors.getDyedColor(GemColors.byId(gem.getInsigniaColor()));
        if (gem.getInsigniaVariant() != 0) {
            texture = new ResourceLocation(KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType().toShortString() + "/outfits/insignia_" + gem.getInsigniaVariant() + ".png");
        } else {
            texture = new ResourceLocation(KinderMod.MOD_ID, "textures/entity/blank.png");
        }
        if (gem.hasOutfitPlacementVariant()) {
            for (int i : gem.outfitPlacementVariants()) {
                if (i == gem.getGemPlacement().id) {
                    texture = new ResourceLocation(KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType().toShortString() + "/outfits/insignia_" + gem.getGemPlacement().name().toLowerCase() + ".png");
                    break;
                }
            }
        }
        RenderType armorRenderType = RenderType.entityTranslucent(texture);
        getRenderer().reRender(getDefaultBakedModel(gem), poseStack, bufferSource, gem, armorRenderType, bufferSource.getBuffer(armorRenderType), partialTick, packedLight, packedOverlay, insigniaColors[0], insigniaColors[1], insigniaColors[2], 1);
    }
}