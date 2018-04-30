package io.github.strikerrocker.vt.misc;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import static io.github.strikerrocker.vt.handlers.ConfigHandler.miscellanious;
import static io.github.strikerrocker.vt.handlers.ConfigHandler.miscellanious_restart;


/**
 * The vanilla properties changer for Vanilla Tweaks
 */
public class VTVanillaPropertiesChanger {
    /**
     * Initializes the vanilla properties changer
     */
    public static void init() {
        //Modifying block creative tabs
        if (miscellanious_restart.commandBlockInRedstoneTab)
            Blocks.COMMAND_BLOCK.setCreativeTab(CreativeTabs.REDSTONE);
        Blocks.DRAGON_EGG.setCreativeTab(CreativeTabs.DECORATIONS);
        //Modifying block names
        if (miscellanious_restart.renameButtons) {
            Blocks.STONE_BUTTON.setUnlocalizedName("buttonStone");
            Blocks.WOODEN_BUTTON.setUnlocalizedName("buttonWood");
        }
        //Modifying End Portal Frame harvestability
        if (miscellanious.endFrameBroken) {
            Blocks.END_PORTAL_FRAME.setHarvestLevel("pickaxe", 2);
            Blocks.END_PORTAL_FRAME.setHardness(5.0F);
        }
        if (miscellanious_restart.stackSize >= 2) {
            int myInt = (int) miscellanious_restart.stackSize;
            Items.BED.setMaxStackSize(myInt);
            Items.TOTEM_OF_UNDYING.setMaxStackSize(myInt);
            Items.BOAT.setMaxStackSize(myInt);
            Items.CAKE.setMaxStackSize(myInt);
            Items.ACACIA_BOAT.setMaxStackSize(myInt);
            Items.BIRCH_BOAT.setMaxStackSize(myInt);
            Items.DARK_OAK_BOAT.setMaxStackSize(myInt);
            Items.JUNGLE_BOAT.setMaxStackSize(myInt);
            Items.SPRUCE_BOAT.setMaxStackSize(myInt);
            Items.ENDER_PEARL.setMaxStackSize(64);
        }
    }

}
