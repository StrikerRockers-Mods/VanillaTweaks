package com.strikerrocker.vt.misc;

import com.strikerrocker.vt.handlers.VTConfigHandler;
import com.strikerrocker.vt.items.VTItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

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
        //Modifying achievements
        if (VTConfigHandler.craftingTableChanges)
            ReflectionHelper.setPrivateValue(Achievement.class, AchievementList.BUILD_WORK_BENCH, new ItemStack(VTItems.pad), "theItemStack", "field_75990_d");
    }
}
