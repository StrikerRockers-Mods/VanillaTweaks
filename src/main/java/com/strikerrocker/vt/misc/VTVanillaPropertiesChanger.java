package com.strikerrocker.vt.misc;

import com.strikerrocker.vt.handlers.VTConfigHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

import static com.strikerrocker.vt.handlers.VTConfigHandler.endFrameBroken;

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
        //Modifying End Portal Frame harvestability
        if (endFrameBroken) {
            Blocks.END_PORTAL_FRAME.setHarvestLevel("pickaxe", 2);
            Blocks.END_PORTAL_FRAME.setHardness(5.0F);
        }
    }
}
