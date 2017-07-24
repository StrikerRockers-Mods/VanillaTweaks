package com.strikerrocker.vt.handlers;

import com.strikerrocker.vt.blocks.VTBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

/**
 * The fuel handler for Vanilla` Tweaks
 */
public class VTFuelHandler implements IFuelHandler {
    @Override
    public int getBurnTime(ItemStack fuel) {
        return fuel.getItem() == Item.getItemFromBlock(VTBlocks.charcoal) ? 16000 : 0;
    }
}
