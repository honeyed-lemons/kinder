package phyner.kinder.client.render.gems;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import phyner.kinder.client.models.gems.QuartzEntityModel;
import phyner.kinder.client.models.gems.RubyEntityModel;
import phyner.kinder.entities.gems.QuartzEntity;
import phyner.kinder.entities.gems.RubyEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(EnvType.CLIENT) public class RubyEntityRenderer extends GeoEntityRenderer<RubyEntity> {
    public RubyEntityRenderer (EntityRendererFactory.Context renderManager){
        super (renderManager, new RubyEntityModel ());
        this.scaleWidth = 0.85F;
        this.scaleHeight = 0.85F;

        addRenderLayer (new GemSkinLayer<> (this));
        addRenderLayer (new GemHairLayer<> (this));
        addRenderLayer (new GemInsigniaLayer<> (this));
        addRenderLayer (new GemOutfitLayer<> (this));
        addRenderLayer (new GemGemLayer<> (this));
    }

    @Override public boolean hasLabel (RubyEntity animatable){
        return super.hasLabel (animatable);
    }

}