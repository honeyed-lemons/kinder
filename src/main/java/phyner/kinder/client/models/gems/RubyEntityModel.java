package phyner.kinder.client.models.gems;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.gems.RubyEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@Environment(EnvType.CLIENT) public class RubyEntityModel extends DefaultedEntityGeoModel<RubyEntity> {

    public RubyEntityModel (){
        super (new Identifier (KinderMod.MOD_ID, "gems/ruby/ruby"), true);
    }
}
