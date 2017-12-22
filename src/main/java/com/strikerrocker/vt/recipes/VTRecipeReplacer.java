package com.strikerrocker.vt.recipes;

import com.strikerrocker.vt.VTUtils;
import com.strikerrocker.vt.handlers.VTConfigHandler;
import com.strikerrocker.vt.items.VTItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeRepairItem;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.List;

import static com.strikerrocker.vt.vt.logInfo;

public class VTRecipeReplacer {
    /**
     * Actually does all of the recipe replacing
     */
    public static void replaceRecipes() {
        //Stone Tools
        if (VTConfigHandler.useBetterStoneToolRecipes) {
            removeRecipes(new ItemStack(Items.STONE_SWORD));
            removeRecipes(new ItemStack(Items.STONE_SHOVEL));
            removeRecipes(new ItemStack(Items.STONE_PICKAXE));
            removeRecipes(new ItemStack(Items.STONE_AXE));
            removeRecipes(new ItemStack(Items.STONE_HOE));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.STONE_SWORD), "S", "S", "T", 'S', "stone", 'T', "stickWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.STONE_SHOVEL), "S", "T", "T", 'S', "stone", 'T', "stickWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.STONE_PICKAXE), "SSS", " T ", " T ", 'S', "stone", 'T', "stickWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.STONE_AXE), "SS", "ST", " T", 'S', "stone", 'T', "stickWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.STONE_HOE), "SS", " T", " T", 'S', "stone", 'T', "stickWood"));
        }
        //Stairs
        if (VTConfigHandler.useBetterStairsRecipes) {
            boolean doStairsFieldsExist = true;
            Iterable<Block> blocks = Block.REGISTRY;
            for (Block block : blocks)
                if (block instanceof BlockStairs) {
                    BlockStairs stairs = (BlockStairs) block;
                    IBlockState modelState = VTUtils.findObject(stairs, "modelState", "field_150151_M");
                    if (modelState == null) {
                        doStairsFieldsExist = false;
                        break;
                    }
                    Block modelBlock = modelState.getBlock();
                    int modelMetadata = OreDictionary.WILDCARD_VALUE;
                    for (ItemStack itemstack : OreDictionary.getOres("plankWood"))
                        if (itemstack.getItem() == Item.getItemFromBlock(modelBlock)) {
                            modelMetadata = modelBlock.getMetaFromState(modelState);
                            break;
                        }
                    replaceStairsRecipe(stairs, new ItemStack(modelBlock, 1, modelMetadata));
                }
            if (!doStairsFieldsExist) { //backup
                logInfo("Reverting to backup stairs recipe replacing");
                replaceStairsRecipe(Blocks.OAK_STAIRS, new ItemStack(Blocks.PLANKS));
                replaceStairsRecipe(Blocks.SPRUCE_STAIRS, new ItemStack(Blocks.PLANKS, 1, 1));
                replaceStairsRecipe(Blocks.BIRCH_STAIRS, new ItemStack(Blocks.PLANKS, 1, 2));
                replaceStairsRecipe(Blocks.JUNGLE_STAIRS, new ItemStack(Blocks.PLANKS, 1, 3));
                replaceStairsRecipe(Blocks.ACACIA_STAIRS, new ItemStack(Blocks.PLANKS, 1, 4));
                replaceStairsRecipe(Blocks.DARK_OAK_STAIRS, new ItemStack(Blocks.PLANKS, 1, 5));

                replaceStairsRecipe(Blocks.STONE_STAIRS, new ItemStack(Blocks.COBBLESTONE));
                replaceStairsRecipe(Blocks.BRICK_STAIRS, new ItemStack(Blocks.BRICK_BLOCK));
                replaceStairsRecipe(Blocks.STONE_BRICK_STAIRS, new ItemStack(Blocks.STONEBRICK, 1, OreDictionary.WILDCARD_VALUE));
                replaceStairsRecipe(Blocks.NETHER_BRICK_FENCE, new ItemStack(Blocks.NETHER_BRICK));
                replaceStairsRecipe(Blocks.SANDSTONE_STAIRS, new ItemStack(Blocks.SANDSTONE, 1, OreDictionary.WILDCARD_VALUE));
                replaceStairsRecipe(Blocks.RED_SANDSTONE_STAIRS, new ItemStack(Blocks.RED_SANDSTONE, 1, OreDictionary.WILDCARD_VALUE));
                replaceStairsRecipe(Blocks.QUARTZ_STAIRS, new ItemStack(Blocks.QUARTZ_BLOCK, 1, OreDictionary.WILDCARD_VALUE));
            }
        }
        //Buttons
        removeRecipes(new ItemStack(Blocks.WOODEN_BUTTON));
        removeRecipes(new ItemStack(Blocks.STONE_BUTTON));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.WOODEN_BUTTON, 4), "plankWood"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.STONE_BUTTON, 4), "stone"));
        //Repairing Items
        removeRecipe(RecipeRepairItem.class);
        //Crafting Table Changes
        if (VTConfigHandler.craftingPad) {
            removeRecipes(new ItemStack(Blocks.CRAFTING_TABLE));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.CRAFTING_TABLE), "P", "W", 'P', VTItems.pad, 'W', "plankWood"));
        }
    }

    /**
     * Removes a recipe/recipes from the game. <br>
     * All recipes for the resulting ItemStack are removed from the game.
     *
     * @param result The ItemStack which is outputted from the workbench
     */
    private static void removeRecipes(ItemStack result) {
        List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
        List<IRecipe> recipeListCopy = VTUtils.copyList(recipeList);
        recipeListCopy.stream().filter(recipe -> ItemStack.areItemStacksEqual(result, recipe.getRecipeOutput())).forEach(recipeList::remove);
    }

    /**
     * Removes the specified IRecipe (class) from the game
     *
     * @param recipeClass The class of the IRecipe to remove from the game
     */
    private static void removeRecipe(Class<? extends IRecipe> recipeClass) {
        List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
        List<IRecipe> recipeListCopy = VTUtils.copyList(recipeList);
        recipeListCopy.stream().filter(recipe -> recipe.getClass() == recipeClass).forEach(recipeList::remove);
    }

    /**
     * Replaces the recipe for stairs with the better one
     *
     * @param stairs   The stairs
     * @param material The material used to craft the stairs
     */
    private static void replaceStairsRecipe(Block stairs, ItemStack material) {
        ItemStack stairsStack = new ItemStack(stairs, 4);
        removeRecipes(stairsStack);
        GameRegistry.addRecipe(stairsStack, " S", "SS", 'S', material);
    }

}

