package phyner.kinder.client.render.gems;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import phyner.kinder.client.models.gems.RubyEntityModel;
import phyner.kinder.client.models.gems.SapphireEntityModel;
import phyner.kinder.client.render.layers.*;
import phyner.kinder.entities.gems.RubyEntity;
import phyner.kinder.entities.gems.SapphireEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(EnvType.CLIENT) public class SapphireEntityRenderer extends GeoEntityRenderer<SapphireEntity> {
    public static float baseHeight = 0.75f;
    public static float baseWidth = 0.75f;
    public SapphireEntityRenderer(EntityRendererFactory.Context renderManager){
        super (renderManager, new SapphireEntityModel());

        addRenderLayer (new GemSkinLayer<> (this));
        addRenderLayer (new GemHairLayer<> (this));
        addRenderLayer (new GemEyeLayer<> (this));
        addRenderLayer (new GemInsigniaLayer<> (this));
        addRenderLayer (new GemOutfitLayer<> (this));
        addRenderLayer (new GemGemLayer<> (this));
    }
    @Override
    public void scaleModelForRender(float widthScale, float heightScale, MatrixStack poseStack, SapphireEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        float scaler = animatable.getPerfectionScaler(animatable.getPerfection());
        super.scaleModelForRender(baseWidth * scaler, baseHeight * scaler, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }
    @Override public boolean hasLabel (SapphireEntity animatable){
        return super.hasLabel (animatable);
    }

}