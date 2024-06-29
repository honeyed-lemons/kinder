package honeyedlemons.kinder.blocks.cropblocks;

import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.world.level.ItemLike;

public class PinkGemCropBlock extends GemCropBlock {
    public PinkGemCropBlock(Properties settings) {
        super(settings);
    }

    @Override
    public ItemLike getBaseSeedId() {
        return KinderItems.PINK_GEM_SEEDS;
    }

}
