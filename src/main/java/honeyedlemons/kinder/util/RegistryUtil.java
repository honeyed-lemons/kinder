package honeyedlemons.kinder.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RegistryUtil {
    public record ItemData(Item item, String item_id, boolean datagen) {
    }

    public record BlockData(Block block, String block_id, boolean item) {
    }
}
