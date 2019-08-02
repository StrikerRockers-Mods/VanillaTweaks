package io.github.strikerrocker.vt.content.items.craftingpad;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerCraftingPad extends ContainerWorkbench {

    public ContainerCraftingPad(InventoryPlayer playerInventory, World worldIn) {
        super(playerInventory, worldIn, BlockPos.ORIGIN);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
