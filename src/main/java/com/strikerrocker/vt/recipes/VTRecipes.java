package com.strikerrocker.vt.recipes;

import com.strikerrocker.vt.blocks.VTBlocks;
import com.strikerrocker.vt.handlers.VTConfigHandler;
import com.strikerrocker.vt.items.VTItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import static com.strikerrocker.vt.handlers.VTConfigHandler.storageBlocks;
import static com.strikerrocker.vt.handlers.VTConfigHandler.useBetterChestRecipe;
import static net.minecraftforge.fml.common.registry.GameRegistry.*;

/**
 * The recipe registration class for Vanilla Tweaks
 */
public class VTRecipes {
    /**
     * Registers the recipes for Vanilla Tweaks
     */
    public static void registerRecipes() {
        addSmelting(Items.EGG, new ItemStack(VTItems.friedegg), 0.35F);
        if (useBetterChestRecipe) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.CHEST, 4), "OOO", "O O", "OOO", 'O', "logWood"));
        }
        addShapedRecipe(new ItemStack(VTItems.slime), "ISI", " I ", 'I', Items.IRON_INGOT, 'S', Items.SLIME_BALL);
        addShapelessRecipe(new ItemStack(Blocks.PACKED_ICE), new ItemStack(Blocks.ICE, 4));
        if (storageBlocks) {
            registerStorageRecipes(new ItemStack(Items.FLINT), VTBlocks.flint);
            registerStorageRecipes(new ItemStack(Items.SUGAR), VTBlocks.sugar);
            registerStorageRecipes(new ItemStack(Items.COAL, 1, 1), VTBlocks.charcoal);
        }
        //Craft++ Items
        GameRegistry.addRecipe(new ItemStack(VTItems.dynamite, 1), " W", " G", "S ", 'W', Items.STRING, 'G', Items.GUNPOWDER, 'S', Blocks.SAND);
        if (VTConfigHandler.craftingPad)
            addRecipe(new ShapedOreRecipe(new ItemStack(VTItems.pad), "PP", "PP", 'P', "plankWood"));
        GameRegistry.addSmelting(Items.EGG, new ItemStack(VTItems.friedegg), 0.35F);
        ItemStack dyeStack = new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage());
        GameRegistry.addRecipe(new ItemStack(VTItems.lens, 4), " I ", "IPI", " I ", 'I', Items.IRON_INGOT, 'P', Blocks.GLASS_PANE);
        GameRegistry.addRecipe(new ItemStack(VTItems.binoculars), "DDD", "LIL", "DDD", 'D', dyeStack, 'L', VTItems.lens, 'I', Items.IRON_INGOT);
        //Better Vanilla
        GameRegistry.addShapelessRecipe(new ItemStack(Items.STRING, 4), Blocks.WOOL);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.ROTTEN_FLESH, 4), Items.LEATHER);
        GameRegistry.addShapedRecipe(new ItemStack(Items.NAME_TAG), "  I", " P ", "P  ", 'P', Items.PAPER, 'I', Items.IRON_INGOT);
        GameRegistry.addShapedRecipe(new ItemStack(VTBlocks.pedestal), "SSS", " B ", "SBS", 'S', new ItemStack(Blocks.STONE_SLAB, 1, 5), 'B', new ItemStack(Blocks.STONEBRICK, 1, 0));
    }

    /**
     * Registers storage block recipes for the specified input and output
     *
     * @param input  The ingredient ItemStack of the recipe
     * @param output The output (storage block) of the recipe
     */
    private static void registerStorageRecipes(ItemStack input, Block output) {
        GameRegistry.addRecipe(new ItemStack(output), "III", "III", "III", 'I', input);
        GameRegistry.addShapelessRecipe(input, output);
    }
}
