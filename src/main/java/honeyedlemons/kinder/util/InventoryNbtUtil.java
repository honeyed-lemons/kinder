package honeyedlemons.kinder.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class InventoryNbtUtil {
    public static void readInventoryNbt(CompoundTag nbt, String key, Container stacks) {
        var inventoryNbt = nbt.getList(key, Tag.TAG_COMPOUND);
        for (int i = 0; i < inventoryNbt.size(); ++i) {
            var slotNbt = inventoryNbt.getCompound(i);
            int slotId = slotNbt.getByte("slot") & 255;
            if (slotId < stacks.getContainerSize()) {
                stacks.setItem(slotId, ItemStack.of(slotNbt));
            }
        }
    }

    @SuppressWarnings ("UnusedReturnValue")
    public static CompoundTag writeInventoryNbt(CompoundTag nbt, String key, Container stacks, int end) {
        return writeInventoryNbt(nbt, key, stacks, end, true);
    }

    public static CompoundTag writeInventoryNbt(CompoundTag nbt, String key, Container stacks, int end, boolean setIfEmpty) {
        var inventoryNbt = new ListTag();

        for (int i = 0; i < end; ++i) {
            var slotStack = stacks.getItem(i);
            if (!slotStack.isEmpty()) {
                var slotNbt = new CompoundTag();
                slotNbt.putByte("slot", (byte) (i));
                slotStack.save(slotNbt);
                inventoryNbt.add(slotNbt);
            }
        }

        if (!inventoryNbt.isEmpty() || setIfEmpty) {
            nbt.put(key, inventoryNbt);
        }

        return nbt;
    }
}
