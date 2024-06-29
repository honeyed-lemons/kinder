package honeyedlemons.kinder.init;

import honeyedlemons.kinder.util.RegistryUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

public class KinderModelGenerator extends FabricModelProvider {
    public KinderModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        for (RegistryUtil.ItemData itemData : KinderItems.itemData()) {
            if (itemData.datagen()) {
                itemModelGenerator.generateFlatItem(itemData.item(), ModelTemplates.FLAT_ITEM);
            }
        }
    }
}
