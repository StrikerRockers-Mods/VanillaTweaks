package io.github.strikerrocker.vt.content.blocks.pedestal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ObjectHolder;

public class PedestalContainer extends AbstractContainerMenu {
    @ObjectHolder("vanillatweaks:pedestal")
    public static final MenuType<PedestalContainer> TYPE = null;

    public PedestalContainer(int id, Inventory playerInv, BlockPos pos) {
        super(TYPE, id);
        PedestalBlockEntity pedestal = (PedestalBlockEntity) playerInv.player.level.getBlockEntity(pos);
        if (pedestal != null) {
            pedestal.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .ifPresent(inv -> addSlot(new SlotItemHandler(inv, 0, 80, 20) {
                        @Override
                        public void setChanged() {
                            super.setChanged();
                            pedestal.setChanged();
                        }
                    }));
        }
        // Add player inventory slots
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 51 + i * 18));
            }
        }
        // Add player hotbar slots
        for (int k = 0; k < 9; k++) {
            addSlot(new Slot(playerInv, k, 8 + k * 18, 109));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            int containerSlots = slots.size() - player.getInventory().items.size();
            if (index < containerSlots) {
                if (!this.moveItemStackTo(originalStack, containerSlots, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (originalStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, originalStack);
        }
        return newStack;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }
}
