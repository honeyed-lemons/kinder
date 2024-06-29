package honeyedlemons.kinder.blocks.cropblocks;

import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.world.level.ItemLike;

public class YellowGemCropBlock extends GemCropBlock {
    public YellowGemCropBlock(Properties settings) {
        super(settings);
    }

    @Override
    public ItemLike getBaseSeedId() {
        return KinderItems.YELLOW_GEM_SEEDS;
    }

}
