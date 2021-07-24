package io.github.strikerrocker.vt.content.items.craftingpad;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

public class CraftingPadContainer extends CraftingMenu {

    CraftingPadContainer(int id, Inventory playerInventory, ContainerLevelAccess worldPosCallable) {
        super(id, playerInventory, worldPosCallable);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }
}
