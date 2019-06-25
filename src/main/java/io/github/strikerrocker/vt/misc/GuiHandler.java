package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.content.blocks.pedestal.ContainerPedestal;
import io.github.strikerrocker.vt.content.blocks.pedestal.GuiPedestal;
import io.github.strikerrocker.vt.content.blocks.pedestal.TileEntityPedestal;
import io.github.strikerrocker.vt.content.items.craftingpad.ContainerCraftingPad;
import io.github.strikerrocker.vt.content.items.craftingpad.GuiCraftingPad;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    public static final int PAD = 0;
    public static final int PEDESTAL = 1;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == PAD) {
            return new ContainerCraftingPad(player.inventory, world);
        } else if (ID == PEDESTAL) {
            return new ContainerPedestal(player.inventory, (TileEntityPedestal) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == PAD) {
            return new GuiCraftingPad(player.inventory, world);
        } else if (ID == PEDESTAL) {
            return new GuiPedestal(player.inventory, (TileEntityPedestal) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
