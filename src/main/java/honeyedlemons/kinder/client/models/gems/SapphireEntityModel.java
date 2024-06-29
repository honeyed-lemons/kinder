package honeyedlemons.kinder.client.models.gems;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.gems.SapphireEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@Environment (EnvType.CLIENT)
public class SapphireEntityModel extends DefaultedEntityGeoModel<SapphireEntity> {

    public SapphireEntityModel() {
        super(new ResourceLocation(KinderMod.MOD_ID, "gems/sapphire/sapphire"), true);
    }
}
