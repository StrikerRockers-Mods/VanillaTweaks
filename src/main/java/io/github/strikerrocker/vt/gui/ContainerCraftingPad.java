package io.github.strikerrocker.vt.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * The container for the crafting pad
 */
public class ContainerCraftingPad extends ContainerWorkbench
{
    public ContainerCraftingPad(InventoryPlayer inventoryPlayer, World world) {
        super(inventoryPlayer, world, BlockPos.ORIGIN);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;

    }
}