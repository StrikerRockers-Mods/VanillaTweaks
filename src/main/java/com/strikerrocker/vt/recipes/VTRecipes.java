package com.strikerrocker.vt.recipes;

import com.strikerrocker.vt.blocks.VTBlocks;
import com.strikerrocker.vt.handlers.VTConfigHandler;
import com.strikerrocker.vt.items.VTItems;
import net.minecraft.block.BlockHalfStoneSlab;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * The recipe registration class for Vanilla Tweaks
 */
public class VTRecipes {
    /**
     * Registers the recipes for Vanilla Tweaks
     */
    public static void registerRecipes() {
        //Storage Blocks
        GameRegistry.addRecipe(new ItemStack(VTBlocks.flint), "III", "III", "III", 'I', new ItemStack(Items.FLINT,1));
        GameRegistry.addRecipe(new ItemStack(VTBlocks.sugar), "III", "III", "III", 'I', new ItemStack(Items.SUGAR,1));
        GameRegistry.addRecipe(new ItemStack(VTBlocks.charcoal), "III", "III", "III", 'I', new ItemStack(Items.COAL,1,1));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.COAL, 9,1), VTBlocks.charcoal);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.FLINT, 9), VTBlocks.flint);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.SUGAR, 9), VTBlocks.sugar);
        //Vanilla Tweaks Items
        GameRegistry.addRecipe(new ItemStack(VTItems.dynamite, 1, 0), " W", " G", "S ", 'W', Items.STRING, 'G', Items.GUNPOWDER, 'S', Blocks.SAND);
        if (VTConfigHandler.craftingTableChanges) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VTItems.pad), "PP", "PP", 'P', "plankWood"));
            GameRegistry.addRecipe(new ItemStack(Blocks.CRAFTING_TABLE), "PC", " ", " ", 'P', "plankWood", 'C', VTItems.pad);
        }
        GameRegistry.addSmelting(Items.EGG, new ItemStack(VTItems.friedegg), 0.35F);
        ItemStack dyeStack = new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage());
        GameRegistry.addRecipe(new ItemStack(VTItems.lens, 4), " I ", "IPI", " I ", 'I', Items.IRON_INGOT, 'P', Blocks.GLASS_PANE);
        GameRegistry.addRecipe(new ItemStack(VTItems.binoculars), "DDD", "LIL", "DDD", 'D', dyeStack, 'L', VTItems.lens, 'I', Items.IRON_INGOT);
        //Better Vanilla
        GameRegistry.addShapelessRecipe(new ItemStack(Items.STRING, 4), Blocks.WOOL);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.LEATHER,1),new ItemStack(Items.ROTTEN_FLESH,9));
        GameRegistry.addRecipe(new ItemStack(Items.NAME_TAG), "  I", " P ", "P  ", 'I', Items.IRON_INGOT, 'P', Items.PAPER);
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.PACKED_ICE), new ItemStack(Blocks.ICE,4));
        
        //pedestal
        if(VTConfigHandler.pedestal){
                GameRegistry.addRecipe(new ItemStack(VTBlocks.pedestal), "SSS", " B ", "SBS", 'S',new ItemStack(Blocks.STONE_SLAB,1,5), 'B',new ItemStack(Blocks.STONEBRICK,1,0));
        }
    }

    /**
     * Registers storage block recipes for the specified input and output
     *
     * @param input  The ingredient ItemStack of the recipe
     * @param output The output (storage block) of the recipe
     */
}
