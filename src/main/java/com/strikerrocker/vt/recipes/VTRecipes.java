package com.strikerrocker.vt.recipes;

import com.strikerrocker.vt.blocks.VTBlocks;
import com.strikerrocker.vt.handlers.VTConfigHandler;
import com.strikerrocker.vt.items.VTItems;
import com.strikerrocker.vt.misc.RecipeHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * The recipe registration class for Vanilla Tweaks
 */
public class VTRecipes {
    /**
     * Registers the recipes for Vanilla Tweaks
     */
    public static void registerRecipes() {
        GameRegistry.addSmelting(Items.EGG, new ItemStack(VTItems.friedegg), 0.35F);
    }

    /**
     * Registers storage block recipes for the specified input and output
     *
     * @param input  The ingredient ItemStack of the recipe
     * @param output The output (storage block) of the recipe
     */
}
