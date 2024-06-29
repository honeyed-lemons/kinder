package honeyedlemons.kinder.blocks.cropblocks;

import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.world.level.ItemLike;

public class BlueGemCropBlock extends GemCropBlock {
    public BlueGemCropBlock(Properties settings) {
        super(settings);
    }

    @Override
    public ItemLike getBaseSeedId() {
        return KinderItems.BLUE_GEM_SEEDS;
    }

}
