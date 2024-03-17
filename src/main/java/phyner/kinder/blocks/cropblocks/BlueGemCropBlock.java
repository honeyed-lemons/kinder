package phyner.kinder.blocks.cropblocks;

import net.minecraft.item.ItemConvertible;
import phyner.kinder.init.KinderItems;

public class BlueGemCropBlock extends GemCropBlock {
    public BlueGemCropBlock (Settings settings){
        super (settings);
    }

    @Override public ItemConvertible getSeedsItem (){
        return KinderItems.BLUE_GEM_SEEDS;
    }

}
