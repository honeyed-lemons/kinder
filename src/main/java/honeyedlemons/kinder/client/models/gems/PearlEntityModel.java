package honeyedlemons.kinder.client.models.gems;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.gems.PearlEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@Environment (EnvType.CLIENT)
public class PearlEntityModel extends DefaultedEntityGeoModel<PearlEntity> {

    public PearlEntityModel() {
        super(new ResourceLocation(KinderMod.MOD_ID, "gems/pearl/pearl"), true);
    }
}
