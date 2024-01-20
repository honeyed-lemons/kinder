package phyner.kinder.client.models.gems;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.gems.PearlEntity;
import phyner.kinder.entities.gems.QuartzEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@Environment(EnvType.CLIENT)
public class PearlEntityModel extends DefaultedEntityGeoModel<PearlEntity> {

    public PearlEntityModel() {
        super(new Identifier(KinderMod.MOD_ID,"gems/pearl/pearl"), true);
    }
}
