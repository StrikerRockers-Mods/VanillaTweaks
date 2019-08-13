package io.github.strikerrocker.vt.content.items.craftingpad;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.WorkbenchContainer;

public class CraftingPadContainer extends WorkbenchContainer {

    public CraftingPadContainer(int id, PlayerInventory playerInventory) {
        super(id, playerInventory);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
