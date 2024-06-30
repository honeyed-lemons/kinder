package honeyedlemons.kinder.client.render.gems;

import com.mojang.blaze3d.vertex.PoseStack;
import honeyedlemons.kinder.client.models.gems.QuartzEntityModel;
import honeyedlemons.kinder.client.render.layers.*;
import honeyedlemons.kinder.entities.gems.QuartzEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment (EnvType.CLIENT)
public class QuartzEntityRenderer extends GeoEntityRenderer<QuartzEntity> {
    public static float baseHeight = 1f;
    public static float baseWidth = 1f;

    public QuartzEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new QuartzEntityModel());
        addRenderLayer(new GemSkinLayer<>(this));
        addRenderLayer(new GemEyeLayer<>(this));
        addRenderLayer(new GemInsigniaLayer<>(this));
        addRenderLayer(new GemOutfitLayer<>(this));
        addRenderLayer(new GemHairLayer<>(this));
        addRenderLayer(new GemGemLayer<>(this));
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, QuartzEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        float scaler = animatable.getPerfectionScalar(animatable.getPerfection());
        super.scaleModelForRender(baseWidth * scaler, baseHeight * scaler, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }
}