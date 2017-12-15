
package com.strikerrocker.vt.handlers;

import com.strikerrocker.vt.blocks.pedestal.ContainerPedestal;
import com.strikerrocker.vt.blocks.pedestal.TileEntityPedestal;
import com.strikerrocker.vt.gui.ContainerCraftingPad;
import com.strikerrocker.vt.gui.GuiCraftingPad;
import com.strikerrocker.vt.gui.GuiPedestal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * The GUI handler for Vanilla Tweaks
 */
public class VTGuiHandler implements IGuiHandler {

    public static final int PEDESTAL = 1;
    public static final int PAD = 0;

    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case PEDESTAL:
                return new ContainerPedestal(player.inventory, (TileEntityPedestal) world.getTileEntity(new BlockPos(x, y, z)));
            case PAD:
                return ID == 0 ? new ContainerCraftingPad(player.inventory, world) : null;
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case PEDESTAL:
                return new GuiPedestal(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            case PAD:
                return ID == 0 ? new GuiCraftingPad(player.inventory, world) : null;
            default:
                return null;
        }
    }


}

