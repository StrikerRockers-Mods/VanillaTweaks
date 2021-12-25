package io.github.strikerrocker.vt.content.items.craftingpad;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

public class CraftingPadScreenHandler extends CraftingMenu {

    CraftingPadScreenHandler(int id, Inventory playerInventory, ContainerLevelAccess screenHandlerContext) {
        super(id, playerInventory, screenHandlerContext);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}