package phyner.kinder.client.render.gems;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import phyner.kinder.client.models.gems.QuartzEntityModel;
import phyner.kinder.client.models.gems.RubyEntityModel;
import phyner.kinder.client.render.layers.*;
import phyner.kinder.entities.gems.QuartzEntity;
import phyner.kinder.entities.gems.RubyEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(EnvType.CLIENT) public class QuartzEntityRenderer extends GeoEntityRenderer<QuartzEntity> {
    public QuartzEntityRenderer (EntityRendererFactory.Context renderManager){
        super (renderManager, new QuartzEntityModel ());
        addRenderLayer (new GemSkinLayer<> (this));
        addRenderLayer (new GemHairLayer<> (this));
        addRenderLayer (new GemInsigniaLayer<> (this));
        addRenderLayer (new GemOutfitLayer<> (this));
        addRenderLayer (new GemGemLayer<> (this));
    }

    @Override public boolean hasLabel (QuartzEntity animatable){
        return super.hasLabel (animatable);
    }
}