package honeyedlemons.kinder.init;

import honeyedlemons.kinder.util.RegistryUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class KinderModelGenerator extends FabricModelProvider {
    public KinderModelGenerator (FabricDataOutput output){
        super (output);
    }

    @Override public void generateBlockStateModels (BlockStateModelGenerator blockStateModelGenerator){

    }

    @Override public void generateItemModels (ItemModelGenerator itemModelGenerator){
        for (RegistryUtil.ItemData itemData : KinderItems.itemData()) {
            if (itemData.datagen()) {
                itemModelGenerator.register(itemData.item(), Models.GENERATED);
            }
        }
    }
}
