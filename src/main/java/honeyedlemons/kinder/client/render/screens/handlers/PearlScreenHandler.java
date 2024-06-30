package honeyedlemons.kinder.client.render.screens.handlers;

import honeyedlemons.kinder.entities.AbstractGemEntity;
import honeyedlemons.kinder.init.KinderScreens;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ShulkerBoxSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class PearlScreenHandler extends AbstractContainerMenu implements ContainerListener {
    public final SimpleContainer inventory;
    public final AbstractGemEntity entity;

    public PearlScreenHandler(int syncId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(syncId, playerInventory, playerInventory.player.level().getEntity(buf.readVarInt()) instanceof AbstractGemEntity gem ? gem : null);
    }

    public PearlScreenHandler(int syncId, Inventory playerInventory, AbstractGemEntity entity) {
        this(syncId, playerInventory, new SimpleContainer(entity.getPerfection() * 9), entity, entity.getPerfection());
    }

    public PearlScreenHandler(int syncId, Inventory playerInventory, SimpleContainer inventory, AbstractGemEntity entity, int perfection) {
        super(KinderScreens.PEARL_SCREEN_HANDLER, syncId);
        int k;
        int j;
        this.inventory = inventory;
        this.entity = entity;
        checkContainerSize(inventory, entity.getInventorySize());
        inventory.startOpen(playerInventory.player);
        int i = (perfection - 4) * 18;
        for (j = 0; j < perfection; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new ShulkerBoxSlot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
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
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasItem()) {
            ItemStack itemStack2 = slot2.getItem();
            itemStack = itemStack2.copy();
            if (slot < entity.getInventorySize() ? !this.moveItemStackTo(itemStack2, entity.getInventorySize(), this.slots.size(), true) : !this.moveItemStackTo(itemStack2, 0, entity.getInventorySize(), false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setByPlayer(ItemStack.EMPTY);
            } else {
                slot2.setChanged();
            }
        }
        return itemStack;
    }

    @Override
    public void containerChanged(Container sender) {
        entity.playAmbientSound();
    }
}

