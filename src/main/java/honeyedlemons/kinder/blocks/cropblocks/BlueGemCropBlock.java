package honeyedlemons.kinder.blocks.cropblocks;

import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.item.ItemConvertible;

public class BlueGemCropBlock extends GemCropBlock {
    public BlueGemCropBlock (Settings settings){
        super (settings);
    }

    @Override public ItemConvertible getSeedsItem (){
        return KinderItems.BLUE_GEM_SEEDS;
    }

}
