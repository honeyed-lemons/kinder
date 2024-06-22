package honeyedlemons.kinder.blocks.cropblocks;

import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.item.ItemConvertible;

public class PinkGemCropBlock extends GemCropBlock {
    public PinkGemCropBlock (Settings settings){
        super (settings);
    }

    @Override public ItemConvertible getSeedsItem (){
        return KinderItems.PINK_GEM_SEEDS;
    }

}
