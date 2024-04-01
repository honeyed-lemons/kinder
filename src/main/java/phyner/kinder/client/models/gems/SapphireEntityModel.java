package phyner.kinder.client.models.gems;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.gems.PearlEntity;
import phyner.kinder.entities.gems.SapphireEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@Environment(EnvType.CLIENT) public class SapphireEntityModel extends DefaultedEntityGeoModel<SapphireEntity> {

    public SapphireEntityModel(){
        super (new Identifier (KinderMod.MOD_ID, "gems/sapphire/sapphire"), true);
    }
}
