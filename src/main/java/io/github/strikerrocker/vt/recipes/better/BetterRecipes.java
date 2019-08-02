package io.github.strikerrocker.vt.recipes.better;

import com.google.common.collect.Lists;
import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.GameData;

import java.util.ArrayList;

public class BetterRecipes extends Feature {
    private boolean betterStairs;
    private boolean betterStoneTools;

    @SuppressWarnings("all")
    private static void addShapedRecipe(ItemStack output, Object... params) {
        ResourceLocation baseLoc = new ResourceLocation(VTModInfo.MODID, output.getItem().getRegistryName().getPath());
        ResourceLocation recipeLoc = baseLoc;
        int index = 0;
        while (CraftingManager.REGISTRY.containsKey(recipeLoc)) {
            index++;
            recipeLoc = new ResourceLocation(VTModInfo.MODID, baseLoc.getPath() + "_" + index);
        }
        CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(params);
        GameData.register_impl(new ShapedRecipes(output.getItem().getRegistryName().toString(), primer.width, primer.height, primer.input, output).setRegistryName(recipeLoc));
    }

    private static void replaceStairsRecipe(ForgeRegistry<IRecipe> recipeRegistry, IRecipe r, ItemStack material) {
        removeRecipe(recipeRegistry, r);
        ItemStack stairsStack = r.getRecipeOutput();
        stairsStack.setCount(8);
        addShapedRecipe(stairsStack, "S  ", "SS ", "SSS", 'S', material);
    }

    private static void removeRecipe(ForgeRegistry<IRecipe> recipeRegistry, IRecipe r) {
        recipeRegistry.remove(r.getRegistryName());
        recipeRegistry.register(DummyRecipe.from(r));
    }

    @Override
    public void syncConfig(Configuration config, String category) {
        betterStairs = config.get(category, "betterStairs", true, "Want 8 stairs rather than 4 in stairs recipe?").getBoolean();
        betterStoneTools = config.get(category, "betterStoneTools", true, "Cobblestone used in stone tools doesn't make sense?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
        ArrayList<IRecipe> recipes = Lists.newArrayList(recipeRegistry.getValuesCollection());
        for (IRecipe recipe : recipes) {
            ItemStack output = recipe.getRecipeOutput();
            if (betterStairs && output.getItem() instanceof ItemBlock) {
                replaceStairsRecipes(Block.getBlockFromItem(output.getItem()), recipeRegistry, recipe);
            }
            if (betterStoneTools) {
                String stickOreDict = "stickWood";
                String stone = "stone";
                if (output.getItem() == Items.STONE_AXE) {
                    addShapedRecipe(new ItemStack(Items.STONE_AXE), "SS", "ST", " T", 'S', stone, 'T', stickOreDict);
                    removeRecipe(recipeRegistry, recipe);
                } else if (output.getItem() == Items.STONE_HOE) {
                    addShapedRecipe(new ItemStack(Items.STONE_HOE), "SS", " T", " T", 'S', stone, 'T', stickOreDict);
                    removeRecipe(recipeRegistry, recipe);
                } else if (output.getItem() == Items.STONE_SWORD) {
                    addShapedRecipe(new ItemStack(Items.STONE_SWORD), "S", "S", "T", 'S', stone, 'T', stickOreDict);
                    removeRecipe(recipeRegistry, recipe);
                } else if (output.getItem() == Items.STONE_SHOVEL) {
                    addShapedRecipe(new ItemStack(Items.STONE_SHOVEL), "S", "T", "T", 'S', stone, 'T', stickOreDict);
                    removeRecipe(recipeRegistry, recipe);
                } else if (output.getItem() == Items.STONE_PICKAXE) {
                    addShapedRecipe(new ItemStack(Items.STONE_PICKAXE), "SSS", " T ", " T ", 'S', stone, 'T', stickOreDict);
                    removeRecipe(recipeRegistry, recipe);
                }
            }
        }
    }

    private void replaceStairsRecipes(Block stair, ForgeRegistry<IRecipe> recipeRegistry, IRecipe recipe) {
        if (stair == Blocks.OAK_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.PLANKS));
        } else if (stair == Blocks.SPRUCE_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.PLANKS, 1, 1));
        } else if (stair == Blocks.BIRCH_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.PLANKS, 1, 2));
        } else if (stair == Blocks.JUNGLE_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.PLANKS, 1, 3));
        } else if (stair == Blocks.ACACIA_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.PLANKS, 1, 4));
        } else if (stair == Blocks.DARK_OAK_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.PLANKS, 1, 5));
        } else if (stair == Blocks.STONE_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.COBBLESTONE));
        } else if (stair == Blocks.BRICK_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.BRICK_BLOCK));
        } else if (stair == Blocks.STONE_BRICK_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.STONEBRICK, 1, 0));
        } else if (stair == Blocks.NETHER_BRICK_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.NETHER_BRICK));
        } else if (stair == Blocks.SANDSTONE_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.SANDSTONE, 1, 0));
        } else if (stair == Blocks.RED_SANDSTONE_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.RED_SANDSTONE, 1, 0));
        } else if (stair == Blocks.QUARTZ_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0));
        } else if (stair == Blocks.PURPUR_STAIRS) {
            replaceStairsRecipe(recipeRegistry, recipe, new ItemStack(Blocks.PURPUR_BLOCK, 1));
        }
    }
}
