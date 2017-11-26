package com.strikerrocker.vt.misc;

import com.strikerrocker.vt.handlers.VTConfigHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

/**
 * The vanilla properties changer for Vanilla Tweaks
 */
public class VTVanillaPropertiesChanger {
    /**
     * Initializes the vanilla properties changer
     */
    @SuppressWarnings("unchecked")
    public static void init() {
        //Modifying block creative tabs
        if (VTConfigHandler.commandBlockInRedstoneTab)
            Blocks.COMMAND_BLOCK.setCreativeTab(CreativeTabs.REDSTONE);
        Blocks.DRAGON_EGG.setCreativeTab(CreativeTabs.DECORATIONS);
        //Modifying block names
        if (VTConfigHandler.renameButtons) {
            Blocks.STONE_BUTTON.setUnlocalizedName("buttonStone");
            Blocks.WOODEN_BUTTON.setUnlocalizedName("buttonWood");
        }
    }
}
