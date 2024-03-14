package phyner.kinder.client.models.gems;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.gems.QuartzEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@Environment(EnvType.CLIENT)
public class QuartzEntityModel extends DefaultedEntityGeoModel<QuartzEntity> {

    public QuartzEntityModel(){
        super(new Identifier(KinderMod.MOD_ID,"gems/quartz/quartz"),true);
    }
}
