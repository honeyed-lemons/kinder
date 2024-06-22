package honeyedlemons.kinder.client.models.gems;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.gems.RubyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@Environment(EnvType.CLIENT) public class RubyEntityModel extends DefaultedEntityGeoModel<RubyEntity> {

    public RubyEntityModel (){
        super (new Identifier (KinderMod.MOD_ID, "gems/ruby/ruby"), true);
    }
}
