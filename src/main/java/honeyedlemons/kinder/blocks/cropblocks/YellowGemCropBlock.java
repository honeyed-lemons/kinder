package honeyedlemons.kinder.blocks.cropblocks;

import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.item.ItemConvertible;

public class YellowGemCropBlock extends GemCropBlock {
    public YellowGemCropBlock (Settings settings){
        super (settings);
    }

    @Override public ItemConvertible getSeedsItem (){
        return KinderItems.YELLOW_GEM_SEEDS;
    }

}
