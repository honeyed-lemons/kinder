package phyner.kinder.client.render.screens.handlers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import phyner.kinder.entities.AbstractGemEntity;
import phyner.kinder.init.KinderScreens;
@Environment(value=EnvType.CLIENT)
public class PearlScreenHandler extends ScreenHandler implements InventoryChangedListener {
    private final SimpleInventory inventory;
    private final AbstractGemEntity entity;


    public PearlScreenHandler(int syncId,PlayerInventory playerInventory,PacketByteBuf buf) {
        this(syncId, playerInventory,
                playerInventory.player.getWorld().getEntityById(buf.readVarInt()) instanceof AbstractGemEntity snail ? snail : null
        );
    }
    public AbstractGemEntity gem() {
        return this.entity;
    }
    public PearlScreenHandler(int syncId,PlayerInventory playerInventory,AbstractGemEntity snail) {
        this(syncId, playerInventory, new SimpleInventory(6*9), snail);
    }

    public PearlScreenHandler(int syncId,PlayerInventory playerInventory,SimpleInventory inventory,AbstractGemEntity entity) {
        super(KinderScreens.PEARL_SCREEN_HANDLER, syncId);
        int k;
        int j;
        checkSize(inventory, 6 * 9);
        this.inventory = inventory;
        this.entity = entity;
        inventory.onOpen(playerInventory.player);
        int i = (6 - 4) * 18;
        for (j = 0; j < 6; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }
        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 161 + i));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < 6 * 9 ? !this.insertItem(itemStack2, 6 * 9, this.slots.size(), true) : !this.insertItem(itemStack2, 0, 6* 9, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public void onInventoryChanged(Inventory sender){

    }
}

