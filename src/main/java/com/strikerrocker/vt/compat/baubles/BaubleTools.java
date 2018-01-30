package com.strikerrocker.vt.compat.baubles;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import com.strikerrocker.vt.items.VTItems;
import com.strikerrocker.vt.vt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BaubleTools {
    public static boolean hasProbeGoggle(EntityPlayer player) {
        if (vt.baubles) {
            IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
            if (handler == null) {
                return false;
            }
            ItemStack stackInSlot = handler.getStackInSlot(4);
            return !stackInSlot.isEmpty() && stackInSlot.getItem() == VTItems.bb;
        }
        return false;
    }

    public static Item initBinocularBauble() {
        return new BinocularBauble();
    }

    public static void initModel(Item binocularBauble) {
        ((BinocularBauble) binocularBauble).registerItemModel();
    }
}
