package com.strikerrocker.vt.recipes;

import com.strikerrocker.vt.items.VTItems;
import com.strikerrocker.vt.misc.RecipeHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import static com.strikerrocker.vt.handlers.VTConfigHandler.useBetterChestRecipe;
import static com.strikerrocker.vt.misc.RecipeHelper.addShapedOreRecipe;
import static com.strikerrocker.vt.misc.RecipeHelper.addShapedRecipe;
import static com.strikerrocker.vt.misc.RecipeHelper.replaceStairsRecipe;

/**
 * The recipe registration class for Vanilla Tweaks
 */
public class VTRecipes {
    /**
     * Registers the recipes for Vanilla Tweaks
     */
    public static void registerRecipes() {
        GameRegistry.addSmelting(Items.EGG, new ItemStack(VTItems.friedegg), 0.35F);
        if (useBetterChestRecipe)
        addShapedOreRecipe(new ItemStack(Blocks.CHEST,4),"OOO","O O","OOO",'O',"logWood");
    }
}
