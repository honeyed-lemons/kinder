package phyner.kinder.client.render.gems;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import phyner.kinder.client.models.gems.PearlEntityModel;
import phyner.kinder.client.render.layers.*;
import phyner.kinder.entities.gems.PearlEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(EnvType.CLIENT) public class PearlEntityRenderer extends GeoEntityRenderer<PearlEntity> {
    public static float baseHeight = 1f;
    public static float baseWidth = 1f;
    public PearlEntityRenderer (EntityRendererFactory.Context renderManager){
        super (renderManager, new PearlEntityModel ());
        addRenderLayer (new GemSkinLayer<> (this));
        addRenderLayer (new GemPearlNoseLayer<> (this));
        addRenderLayer (new GemEyeLayer<> (this));
        addRenderLayer (new GemHairLayer<> (this));
        addRenderLayer (new GemHairExtraLayer<> (this));
        addRenderLayer (new GemOutfitLayer<> (this));
        addRenderLayer (new GemInsigniaLayer<> (this));
        addRenderLayer (new GemGemLayer<> (this));
    }
    @Override
    public void scaleModelForRender(float widthScale, float heightScale, MatrixStack poseStack, PearlEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        float scaler = animatable.getPerfectionScaler(animatable.getPerfection());
        super.scaleModelForRender(baseWidth * scaler, baseHeight * scaler, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }

    @Override public boolean hasLabel (PearlEntity animatable){
        return super.hasLabel (animatable);
    }
}