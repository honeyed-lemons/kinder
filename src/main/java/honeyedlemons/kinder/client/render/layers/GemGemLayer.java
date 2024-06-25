package honeyedlemons.kinder.client.render.layers;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractGemEntity;
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

import java.awt.*;

@Environment(value = EnvType.CLIENT) public class GemGemLayer<T extends AbstractGemEntity> extends GeoRenderLayer<T> {
    public GemGemLayer (GeoRenderer<T> entityRenderer){
        super (entityRenderer);
    }

    @Override
    public void render (MatrixStack poseStack, T gem, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay){
        Identifier texture;
        if (gem.getGemPlacement () != null) {
            texture = new Identifier (KinderMod.MOD_ID, "textures/entity/gems/" + gem.getType ().getUntranslatedName () + "/gems/gem_" + gem.getGemPlacement ().name ().toLowerCase () + ".png");
        } else {
            texture = new Identifier (KinderMod.MOD_ID, "textures/entity/blank.png");
        }
        Color gemColor = new Color (gem.getGemColor ());
        float r = (float) gemColor.getRed () / 255;
        float b = (float) gemColor.getBlue () / 255;
        float g = (float) gemColor.getGreen () / 255;
        RenderLayer armorRenderType = RenderLayer.getEntityCutoutNoCull (texture);
        getRenderer ().reRender (getDefaultBakedModel (gem), poseStack, bufferSource, gem, armorRenderType, bufferSource.getBuffer (armorRenderType), partialTick, packedLight, packedOverlay, r, g, b, 1);
    }
}