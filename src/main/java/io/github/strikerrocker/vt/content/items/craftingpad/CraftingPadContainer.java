package io.github.strikerrocker.vt.content.items.craftingpad;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.IWorldPosCallable;

public class CraftingPadContainer extends WorkbenchContainer {

    CraftingPadContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        super(id, playerInventory, worldPosCallable);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return true;
    }
}
