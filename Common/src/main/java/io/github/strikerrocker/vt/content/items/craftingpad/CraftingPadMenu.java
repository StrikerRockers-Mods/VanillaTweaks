package io.github.strikerrocker.vt.content.items.craftingpad;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

public class CraftingPadMenu extends CraftingMenu {
    int slotId;

    CraftingPadMenu(int id, Inventory playerInventory, ContainerLevelAccess screenHandlerContext, int slotId) {
        super(id, playerInventory, screenHandlerContext);
        this.slotId = slotId;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getInventory().getItem(slotId).getItem() instanceof CraftingPadItem;
    }
}