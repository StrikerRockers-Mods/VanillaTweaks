package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.handlers.ConfigHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

/**
 * The vanilla properties changer for Vanilla Tweaks
 */
public class VTVanillaPropertiesChanger {
    /**
     * Initializes the vanilla properties changer
     */
    public static void init() {
        //Modifying block creative tabs
        if (ConfigHandler.miscellanious_restart.commandBlockInRedstoneTab)
            Blocks.COMMAND_BLOCK.setCreativeTab(CreativeTabs.REDSTONE);
        Blocks.DRAGON_EGG.setCreativeTab(CreativeTabs.DECORATIONS);
        //Modifying block names
        if (ConfigHandler.miscellanious_restart.renameButtons) {
            Blocks.STONE_BUTTON.setTranslationKey("buttonStone");
            Blocks.WOODEN_BUTTON.setTranslationKey("buttonWood");
        }
        //Modifying End Portal Frame harvestability
        if (ConfigHandler.miscellanious.endFrameBroken) {
            Blocks.END_PORTAL_FRAME.setHarvestLevel("pickaxe", 2);
            Blocks.END_PORTAL_FRAME.setHardness(5.0F);
        }
        Items.BED.setMaxStackSize(ConfigHandler.stackSize.bed_stackSize);
        Items.TOTEM_OF_UNDYING.setMaxStackSize(ConfigHandler.stackSize.totem_of_undying_stackSize);
        Items.BOAT.setMaxStackSize(ConfigHandler.stackSize.boat_stackSize);
        Items.ACACIA_BOAT.setMaxStackSize(ConfigHandler.stackSize.boat_stackSize);
        Items.BIRCH_BOAT.setMaxStackSize(ConfigHandler.stackSize.boat_stackSize);
        Items.DARK_OAK_BOAT.setMaxStackSize(ConfigHandler.stackSize.boat_stackSize);
        Items.JUNGLE_BOAT.setMaxStackSize(ConfigHandler.stackSize.boat_stackSize);
        Items.SPRUCE_BOAT.setMaxStackSize(ConfigHandler.stackSize.boat_stackSize);
        Items.CAKE.setMaxStackSize(ConfigHandler.stackSize.cake_stackSize);
        Items.ENDER_PEARL.setMaxStackSize(ConfigHandler.stackSize.ender_pearl_stackSize);
    }

}