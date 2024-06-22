package honeyedlemons.kinder.init;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;

import java.util.Map;

public class KinderModelGenerator extends FabricModelProvider {
    public KinderModelGenerator (FabricDataOutput output){
        super (output);
    }

    @Override public void generateBlockStateModels (BlockStateModelGenerator blockStateModelGenerator){

    }

    @Override public void generateItemModels (ItemModelGenerator itemModelGenerator){
        for (Map.Entry<Item, String> entry : KinderItems.ItemsWithDataGen().entrySet ()) {
            itemModelGenerator.register (entry.getKey (), Models.GENERATED);
        }
    }
}
