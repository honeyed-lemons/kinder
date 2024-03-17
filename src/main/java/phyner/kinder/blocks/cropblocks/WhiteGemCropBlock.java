package phyner.kinder.blocks.cropblocks;

import net.minecraft.item.ItemConvertible;
import phyner.kinder.init.KinderItems;

public class WhiteGemCropBlock extends GemCropBlock {
    public WhiteGemCropBlock (Settings settings){
        super (settings);
    }

    @Override public ItemConvertible getSeedsItem (){
        return KinderItems.WHITE_GEM_SEEDS;
    }

}
