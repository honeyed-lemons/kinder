package honeyedlemons.kinder.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class RegistryUtil {
    public record ItemData(Item item, String item_id, boolean datagen) {
    }
    public record BlockData(Block block, String block_id, boolean item) {}
}
