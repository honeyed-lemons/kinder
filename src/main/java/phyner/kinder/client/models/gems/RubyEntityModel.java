package phyner.kinder.client.models.gems;

import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.gems.RubyEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class RubyEntityModel extends DefaultedEntityGeoModel<RubyEntity> {

    public RubyEntityModel() {
        super(new Identifier(KinderMod.MOD_ID,"gems/ruby/ruby"), true);
    }
}
