package phyner.kinder.client.render.layers;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.AbstractGemEntity;
import phyner.kinder.util.GemColors;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class GemInsigniaLayer<T extends AbstractGemEntity> extends GeoRenderLayer<T> {
    public GemInsigniaLayer (GeoRenderer<T> entityRenderer){
        super (entityRenderer);
    }

    @Override
    public void render (MatrixStack poseStack, T gem, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay){
        Identifier texture;
        float[] insigniaColors = GemColors.getDyedColor (GemColors.byId (gem.getInsigniaColor ()));
        if (gem.getInsigniaVariant () != 0) {
            texture = new Identifier (KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType ().getUntranslatedName () + "/outfits/insignia_" + gem.getInsigniaVariant () + ".png");
        } else {
            texture = new Identifier (KinderMod.MOD_ID, "textures/entity/blank.png");
        }
        if (gem.hasOutfitPlacementVariant ()) {
            for (int i : gem.outfitPlacementVariants ()) {
                if (i == gem.getGemPlacement ().id) {
                    texture = new Identifier (KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType ().getUntranslatedName () + "/outfits/insignia_" + gem.getGemPlacement ().name ().toLowerCase () + ".png");
                    break;
                }
            }
        }
        RenderLayer armorRenderType = RenderLayer.getEntityTranslucent (texture);
        getRenderer ().reRender (getDefaultBakedModel (gem), poseStack, bufferSource, gem, armorRenderType, bufferSource.getBuffer (armorRenderType), partialTick, packedLight, OverlayTexture.DEFAULT_UV, insigniaColors[0], insigniaColors[1], insigniaColors[2], 1);
    }
}