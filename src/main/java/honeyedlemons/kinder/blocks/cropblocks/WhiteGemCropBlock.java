package honeyedlemons.kinder.blocks.cropblocks;

import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.world.level.ItemLike;

public class WhiteGemCropBlock extends GemCropBlock {
    public WhiteGemCropBlock(Properties settings) {
        super(settings);
    }

    @Override
    public ItemLike getBaseSeedId() {
        return KinderItems.WHITE_GEM_SEEDS;
    }

}
