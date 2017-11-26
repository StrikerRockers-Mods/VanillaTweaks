package com.strikerrocker.vt.recipes;

import com.strikerrocker.vt.blocks.VTBlocks;
import com.strikerrocker.vt.handlers.VTConfigHandler;
import com.strikerrocker.vt.items.VTItems;
import com.strikerrocker.vt.misc.RecipeHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

/**
 * The recipe registration class for Vanilla Tweaks
 */
public class VTRecipes {
    /**
     * Registers the recipes for Vanilla Tweaks
     */
    public static void registerRecipes() {
        //Storage Blocks
        RecipeHelper.addShapelessRecipe(new ItemStack(VTBlocks.flint), "III", "III", "III", 'I', new ItemStack(Items.FLINT, 1));
        RecipeHelper.addShapelessRecipe(new ItemStack(VTBlocks.sugar), "III", "III", "III", 'I', new ItemStack(Items.SUGAR, 1));
        RecipeHelper.addShapelessRecipe(new ItemStack(VTBlocks.charcoal), "III", "III", "III", 'I', new ItemStack(Items.COAL, 1, 1));
        RecipeHelper.addShapelessRecipe(new ItemStack(Items.COAL, 9, 1), VTBlocks.charcoal);
        RecipeHelper.addShapelessRecipe(new ItemStack(Items.FLINT, 9), VTBlocks.flint);
        RecipeHelper.addShapelessRecipe(new ItemStack(Items.SUGAR, 9), VTBlocks.sugar);
        //Vanilla Tweaks Items
        RecipeHelper.addShapedRecipe(new ItemStack(VTItems.dynamite, 1, 0), " W", " G", "S ", 'W', Items.STRING, 'G', Items.GUNPOWDER, 'S', Blocks.SAND);
        if (VTConfigHandler.craftingTableChanges) {
            RecipeHelper.addShapedOreRecipe(new ItemStack(VTItems.pad), "PP", "PP", 'P', "plankWood");
            RecipeHelper.addShapedRecipe(new ItemStack(Blocks.CRAFTING_TABLE), "PC", " ", " ", 'P', "plankWood", 'C', VTItems.pad);
        }
        RecipeHelper.addSmelting(Items.EGG, new ItemStack(VTItems.friedegg), 0.35F);
        ItemStack dyeStack = new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage());
        RecipeHelper.addShapedRecipe(new ItemStack(VTItems.lens, 4), " I ", "IPI", " I ", 'I', Items.IRON_INGOT, 'P', Blocks.GLASS_PANE);
        RecipeHelper.addShapedRecipe(new ItemStack(VTItems.binoculars), "DDD", "LIL", "DDD", 'D', dyeStack, 'L', VTItems.lens, 'I', Items.IRON_INGOT);
        //Better Vanilla
        RecipeHelper.addShapelessRecipe(new ItemStack(Items.STRING, 4), Blocks.WOOL);
        RecipeHelper.addShapelessRecipe(new ItemStack(Items.LEATHER, 1), new ItemStack(Items.ROTTEN_FLESH, 9));
        RecipeHelper.addShapedRecipe(new ItemStack(Items.NAME_TAG), "  I", " P ", "P  ", 'I', Items.IRON_INGOT, 'P', Items.PAPER);
        RecipeHelper.addShapelessRecipe(new ItemStack(Blocks.PACKED_ICE), new ItemStack(Blocks.ICE, 4));

        //pedestal
        if (VTConfigHandler.pedestal) {
            RecipeHelper.addShapedRecipe(new ItemStack(VTBlocks.pedestal), "SSS", " B ", "SBS", 'S', new ItemStack(Blocks.STONE_SLAB, 1, 5), 'B', new ItemStack(Blocks.STONEBRICK, 1, 0));
        }
    }

    /**
     * Registers storage block recipes for the specified input and output
     *
     * @param input  The ingredient ItemStack of the recipe
     * @param output The output (storage block) of the recipe
     */
}
