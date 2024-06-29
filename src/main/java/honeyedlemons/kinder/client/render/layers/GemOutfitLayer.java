package honeyedlemons.kinder.client.render.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractGemEntity;
import honeyedlemons.kinder.util.GemColors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@Environment (value = EnvType.CLIENT)
public class GemOutfitLayer<T extends AbstractGemEntity> extends GeoRenderLayer<T> {
    public GemOutfitLayer(GeoRenderer<T> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(PoseStack poseStack, T gem, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        ResourceLocation texture;
        float[] outfitColors = GemColors.getDyedColor(GemColors.byId(gem.getOutfitColor()));
        if (gem.getOutfitVariant() != 0) {
            texture = new ResourceLocation(KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType().toShortString() + "/outfits/outfit_" + gem.getOutfitVariant() + ".png");
        } else {
            texture = new ResourceLocation(KinderMod.MOD_ID, "textures/entity/blank.png");
        }
        if (gem.hasOutfitPlacementVariant()) {
            for (int i : gem.outfitPlacementVariants()) {
                if (i == gem.getGemPlacement().id) {
                    texture = new ResourceLocation(KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType().toShortString() + "/outfits/outfit_" + gem.getGemPlacement().name().toLowerCase() + ".png");
                    break;
                }
            }
        }
        RenderType armorRenderType = RenderType.entityTranslucent(texture);
        getRenderer().reRender(getDefaultBakedModel(gem), poseStack, bufferSource, gem, armorRenderType, bufferSource.getBuffer(armorRenderType), partialTick, packedLight, packedOverlay, outfitColors[0], outfitColors[1], outfitColors[2], 1);
    }
}