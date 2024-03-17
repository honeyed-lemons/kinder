package phyner.kinder.blocks.cropblocks;

import net.minecraft.item.ItemConvertible;
import phyner.kinder.init.KinderItems;

public class YellowGemCropBlock extends GemCropBlock {
    public YellowGemCropBlock (Settings settings){
        super (settings);
    }

    @Override public ItemConvertible getSeedsItem (){
        return KinderItems.YELLOW_GEM_SEEDS;
    }

}
