package com.strikerrocker.vt.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;

public class VanillaHandler {

    public static void  frame() {
        if(VTConfigHandler.endframebroken == true )
        Blocks.END_PORTAL_FRAME.setHarvestLevel( "ItemPickaxe" , 3);

    }
}