package phyner.kinder.client.models.gems;

import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.gems.RubyEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class RubyEntityModel extends DefaultedEntityGeoModel<RubyEntity> {

    public RubyEntityModel() {
        super(new Identifier(KinderMod.MOD_ID,"ruby"), true);
    }
    public String getGemName() {
        return "ruby";
    }

    @Override
    public Identifier buildFormattedModelPath(Identifier basePath) {
        return new Identifier(KinderMod.MOD_ID,"geo/gems/" + getGemName() + ".geo.json");
    }
    @Override
    public Identifier buildFormattedTexturePath(Identifier basePath) {
        return new Identifier(KinderMod.MOD_ID,"textures/entity/gems/" + getGemName() + "/"+ getGemName() + ".png");
    }

    @Override
    public Identifier buildFormattedAnimationPath(Identifier basePath) {
        return new Identifier(KinderMod.MOD_ID,"animations/gems/" + getGemName() + ".animation.json");
    }
}
