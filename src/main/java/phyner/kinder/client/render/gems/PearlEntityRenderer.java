package phyner.kinder.client.render.gems;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import phyner.kinder.client.models.gems.PearlEntityModel;
import phyner.kinder.client.models.gems.RubyEntityModel;
import phyner.kinder.client.render.layers.*;
import phyner.kinder.entities.gems.PearlEntity;
import phyner.kinder.entities.gems.RubyEntity;
import phyner.kinder.util.GemPlacements;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class PearlEntityRenderer extends GeoEntityRenderer<PearlEntity> {
    public PearlEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new PearlEntityModel());
        addRenderLayer(new GemSkinLayer<>(this));
        addRenderLayer(new GemPearlNoseLayer<>(this));
        addRenderLayer(new GemEyeLayer<>(this));
        addRenderLayer(new GemHairLayer<>(this));
        addRenderLayer(new GemOutfitLayer<>(this));
        addRenderLayer(new GemInsigniaLayer<>(this));
        addRenderLayer(new GemGemLayer<>(this));
    }

    @Override
    public boolean hasLabel(PearlEntity animatable) {
        return super.hasLabel(animatable);
    }
}