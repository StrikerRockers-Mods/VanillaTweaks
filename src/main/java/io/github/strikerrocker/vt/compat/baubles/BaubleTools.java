package io.github.strikerrocker.vt.compat.baubles;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class BaubleTools {
    private BaubleTools() {
    }

    public static boolean hasProbeGoggle(EntityPlayer player) {
        if (Loader.isModLoaded("baubles")) {
            IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
            if (handler == null) {
                return false;
            }
            ItemStack stackInSlot = handler.getStackInSlot(4);
            return !stackInSlot.isEmpty() && stackInSlot.getItem() instanceof ItemBaubleBino;
        } else {
            return false;
        }
    }

    public static Item initBinocularBauble() {
        return new ItemBaubleBino();
    }
}
