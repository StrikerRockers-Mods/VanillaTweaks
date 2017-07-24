package com.strikerrocker.vt.handlers;

import com.strikerrocker.vt.gui.ContainerCraftingPad;
import com.strikerrocker.vt.gui.GuiCraftingPad;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * The GUI handler for Vanilla Tweaks
 */
public class VTGuiHandler implements IGuiHandler {

    public static final int PEDESTAL = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return id == 0 ? new ContainerCraftingPad(player.inventory, world) : null;

    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return id == 0 ? new GuiCraftingPad(player.inventory, world) : null;
    }


}
