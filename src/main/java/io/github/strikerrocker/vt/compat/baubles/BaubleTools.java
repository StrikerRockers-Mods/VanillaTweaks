package io.github.strikerrocker.vt.compat.baubles;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import io.github.strikerrocker.vt.items.VTItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class BaubleTools {
    public static boolean hasProbeGoggle(EntityPlayer player) {
        if (Loader.isModLoaded("baubles")) {
            IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
            if (handler == null) {
                return false;
            }
            ItemStack stackInSlot = handler.getStackInSlot(4);
            return !stackInSlot.isEmpty() && stackInSlot.getItem() == VTItems.bb;
        } else {
            return false;
        }
    }

    public static Item initBinocularBauble() {
        return new BinocularBauble();
    }
}
