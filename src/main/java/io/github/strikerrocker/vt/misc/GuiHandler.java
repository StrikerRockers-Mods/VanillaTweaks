package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalContainer;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalScreen;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalTileEntity;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadContainer;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    public static final int PAD = 0;
    public static final int PEDESTAL = 1;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
        if (ID == PAD) {
            return new CraftingPadContainer(player, world);
        } else if (ID == PEDESTAL) {
            return new PedestalContainer(player.inventory, (PedestalTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
        if (ID == PAD) {
            return new CraftingPadScreen(player.inventory, world);
        } else if (ID == PEDESTAL) {
            return new PedestalScreen(player.inventory, (PedestalTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
