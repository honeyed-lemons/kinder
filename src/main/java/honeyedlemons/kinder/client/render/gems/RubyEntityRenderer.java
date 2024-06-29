package honeyedlemons.kinder.client.render.gems;

import com.mojang.blaze3d.vertex.PoseStack;
import honeyedlemons.kinder.client.models.gems.RubyEntityModel;
import honeyedlemons.kinder.client.render.layers.*;
import honeyedlemons.kinder.entities.gems.RubyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment (EnvType.CLIENT)
public class RubyEntityRenderer extends GeoEntityRenderer<RubyEntity> {
    public static float baseHeight = 0.75f;
    public static float baseWidth = 0.75f;

    public RubyEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RubyEntityModel());

        addRenderLayer(new GemSkinLayer<>(this));
        addRenderLayer(new GemHairLayer<>(this));
        addRenderLayer(new GemEyeLayer<>(this));
        addRenderLayer(new GemInsigniaLayer<>(this));
        addRenderLayer(new GemOutfitLayer<>(this));
        addRenderLayer(new GemGemLayer<>(this));
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, RubyEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        float scaler = animatable.getPerfectionScaler(animatable.getPerfection());
        super.scaleModelForRender(baseWidth * scaler, baseHeight * scaler, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }
}