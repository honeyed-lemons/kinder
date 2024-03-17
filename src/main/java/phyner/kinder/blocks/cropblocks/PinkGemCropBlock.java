package phyner.kinder.blocks.cropblocks;

import net.minecraft.item.ItemConvertible;
import phyner.kinder.init.KinderItems;

public class PinkGemCropBlock extends GemCropBlock {
    public PinkGemCropBlock (Settings settings){
        super (settings);
    }

    @Override public ItemConvertible getSeedsItem (){
        return KinderItems.PINK_GEM_SEEDS;
    }

}
