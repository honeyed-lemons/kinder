package phyner.kinder.util;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

public class InventoryNbtUtil {
    public static void readInventoryNbt(NbtCompound nbt,String key,Inventory stacks) {
        var inventoryNbt = nbt.getList(key, NbtElement.COMPOUND_TYPE);

        for (int i = 0; i < inventoryNbt.size(); ++i) {
            var slotNbt = inventoryNbt.getCompound(i);
            int slotId = slotNbt.getByte("slot") & 255;
            if (slotId < stacks.size()) {
                stacks.setStack(slotId, ItemStack.fromNbt(slotNbt));
            }
        }
    }

    public static NbtCompound writeInventoryNbt(NbtCompound nbt, String key, Inventory stacks, int end) {
        return writeInventoryNbt(nbt, key, stacks, end, true);
    }
    public static NbtCompound writeInventoryNbt(NbtCompound nbt, String key, Inventory stacks, int end, boolean setIfEmpty) {
        var inventoryNbt = new NbtList();

        for (int i = 0; i < end; ++i) {
            var slotStack = stacks.getStack(i);
            if (!slotStack.isEmpty()) {
                var slotNbt = new NbtCompound();
                slotNbt.putByte("slot", (byte) (i));
                slotStack.writeNbt(slotNbt);
                inventoryNbt.add(slotNbt);
            }
        }

        if (!inventoryNbt.isEmpty() || setIfEmpty) {
            nbt.put(key, inventoryNbt);
        }

        return nbt;
    }
}
