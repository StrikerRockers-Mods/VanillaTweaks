package io.github.strikerrocker.vt.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.GameData;

class RecipeHelper {

    /**
     * Adds a basic shaped recipe
     *
     * @param output The stack that should be produced
     */
    public static void addShapedRecipe(ItemStack output, Object... params) {
        ResourceLocation location = getNameForRecipe(output);
        CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(params);
        ShapedRecipes recipe = new ShapedRecipes(output.getItem().getRegistryName().toString(), primer.width, primer.height, primer.input, output);
        recipe.setRegistryName(location);
        GameData.register_impl(recipe);
    }

    /**
     * Generates a unique name based of the active mod, and the itemstack that the recipe outputs
     *
     * @param output an itemstack, usually the one the the recipe produces
     * @return a unique ResourceLocation based off the item item
     */
    private static ResourceLocation getNameForRecipe(ItemStack output) {
        ModContainer activeContainer = Loader.instance().activeModContainer();
        ResourceLocation baseLoc = new ResourceLocation(activeContainer.getModId(), output.getItem().getRegistryName().getResourcePath());
        ResourceLocation recipeLoc = baseLoc;
        int index = 0;
        while (CraftingManager.REGISTRY.containsKey(recipeLoc)) {
            index++;
            recipeLoc = new ResourceLocation(activeContainer.getModId(), baseLoc.getResourcePath() + "_" + index);
        }
        return recipeLoc;
    }

    public static void replaceStairsRecipe(Block stairs, ItemStack material) {
        ItemStack stairsStack = new ItemStack(stairs, 8);
        addShapedRecipe(stairsStack, "S  ", "SS ","SSS",'S', material);
    }
}
