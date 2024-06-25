package honeyedlemons.kinder.client.render.layers;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractGemEntity;
import honeyedlemons.kinder.util.GemColors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@Environment(value = EnvType.CLIENT)
public class GemOutfitLayer<T extends AbstractGemEntity> extends GeoRenderLayer<T> {
    public GemOutfitLayer (GeoRenderer<T> entityRenderer){
        super (entityRenderer);
    }

    @Override
    public void render (MatrixStack poseStack, T gem, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay){
        Identifier texture;
        float[] outfitColors = GemColors.getDyedColor (GemColors.byId (gem.getOutfitColor ()));
        if (gem.getOutfitVariant () != 0) {
            texture = new Identifier (KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType ().getUntranslatedName () + "/outfits/outfit_" + gem.getOutfitVariant () + ".png");
        } else {
            texture = new Identifier (KinderMod.MOD_ID, "textures/entity/blank.png");
        }
        if (gem.hasOutfitPlacementVariant ()) {
            for (int i : gem.outfitPlacementVariants ()) {
                if (i == gem.getGemPlacement ().id) {
                    texture = new Identifier (KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType ().getUntranslatedName () + "/outfits/outfit_" + gem.getGemPlacement ().name ().toLowerCase () + ".png");
                    break;
                }
            }
        }
        RenderLayer armorRenderType = RenderLayer.getEntityCutoutNoCull (texture);
        getRenderer ().reRender (getDefaultBakedModel (gem), poseStack, bufferSource, gem, armorRenderType, bufferSource.getBuffer (armorRenderType), partialTick, packedLight, packedOverlay, outfitColors[0], outfitColors[1], outfitColors[2], 1);
    }
}