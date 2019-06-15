package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.content.items.craftingpad.ContainerCraftingPad;
import io.github.strikerrocker.vt.content.items.craftingpad.GuiCraftingPad;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    public static final int PAD = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case PAD:
                return new ContainerCraftingPad(player.inventory, world);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case PAD:
                return new GuiCraftingPad(player.inventory, world);
        }
        return null;
    }
}
