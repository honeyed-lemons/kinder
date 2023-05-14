package phyner.kinder.client.render.gems;

import net.minecraft.client.render.entity.EntityRendererFactory;
import phyner.kinder.client.models.gems.RubyEntityModel;
import phyner.kinder.entities.gems.RubyEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RubyEntityRenderer extends GeoEntityRenderer<RubyEntity> {

    public RubyEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new RubyEntityModel());
        this.scaleWidth = 0.85F;
        this.scaleHeight = 0.85F;
    }

    @Override
    public boolean hasLabel(RubyEntity animatable) {
        return super.hasLabel(animatable);
    }
}