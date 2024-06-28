package honeyedlemons.kinder.blocks.cropblocks;

import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.item.ItemConvertible;

public class WhiteGemCropBlock extends GemCropBlock {
    public WhiteGemCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ItemConvertible getSeedsItem() {
        return KinderItems.WHITE_GEM_SEEDS;
    }

}
