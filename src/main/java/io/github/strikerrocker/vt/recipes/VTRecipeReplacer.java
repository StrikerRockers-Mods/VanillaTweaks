package io.github.strikerrocker.vt.recipes;

import com.google.common.collect.Lists;
import io.github.strikerrocker.vt.items.VTItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.ArrayList;

import static io.github.strikerrocker.vt.handlers.ConfigHandler.recipes;
import static io.github.strikerrocker.vt.recipes.RecipeHelper.addShapedRecipe;
import static io.github.strikerrocker.vt.recipes.RecipeHelper.replaceStairsRecipe;


@Mod.EventBusSubscriber
public class VTRecipeReplacer {

    public static void replaceRecipes() {
        ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
        ArrayList<IRecipe> recipies = Lists.newArrayList(recipeRegistry.getValuesCollection());
        for (IRecipe r : recipies) {
            ItemStack output = r.getRecipeOutput();
            if (recipes.useBetterStairsRecipes) {
                if (output.getItem() == Item.getItemFromBlock(Blocks.ACACIA_STAIRS) || output.getItem() == Item.getItemFromBlock(Blocks.OAK_STAIRS) || output.getItem() == Item.getItemFromBlock(Blocks.DARK_OAK_STAIRS)
                        || output.getItem() == Item.getItemFromBlock(Blocks.BIRCH_STAIRS) || output.getItem() == Item.getItemFromBlock(Blocks.STONE_STAIRS) || output.getItem() == Item.getItemFromBlock(Blocks.STONE_BRICK_STAIRS)
                        || output.getItem() == Item.getItemFromBlock(Blocks.NETHER_BRICK_STAIRS) || output.getItem() == Item.getItemFromBlock(Blocks.BRICK_STAIRS) || output.getItem() == Item.getItemFromBlock(Blocks.SANDSTONE_STAIRS)
                        || output.getItem() == Item.getItemFromBlock(Blocks.JUNGLE_STAIRS) || output.getItem() == Item.getItemFromBlock(Blocks.QUARTZ_STAIRS) || output.getItem() == Item.getItemFromBlock(Blocks.RED_SANDSTONE_STAIRS)
                        || output.getItem() == Item.getItemFromBlock(Blocks.PURPUR_STAIRS) || output.getItem() == Item.getItemFromBlock(Blocks.SPRUCE_STAIRS)) {
                    recipeRegistry.remove(r.getRegistryName());
                    recipeRegistry.register(DummyRecipe.from(r));
                    replaceStairsRecipe(Blocks.OAK_STAIRS, new ItemStack(Blocks.PLANKS));
                    replaceStairsRecipe(Blocks.SPRUCE_STAIRS, new ItemStack(Blocks.PLANKS, 1, 1));
                    replaceStairsRecipe(Blocks.BIRCH_STAIRS, new ItemStack(Blocks.PLANKS, 1, 2));
                    replaceStairsRecipe(Blocks.JUNGLE_STAIRS, new ItemStack(Blocks.PLANKS, 1, 3));
                    replaceStairsRecipe(Blocks.ACACIA_STAIRS, new ItemStack(Blocks.PLANKS, 1, 4));
                    replaceStairsRecipe(Blocks.DARK_OAK_STAIRS, new ItemStack(Blocks.PLANKS, 1, 5));
                    replaceStairsRecipe(Blocks.STONE_STAIRS, new ItemStack(Blocks.COBBLESTONE));
                    replaceStairsRecipe(Blocks.BRICK_STAIRS, new ItemStack(Blocks.BRICK_BLOCK));
                    replaceStairsRecipe(Blocks.STONE_BRICK_STAIRS, new ItemStack(Blocks.STONEBRICK, 1, 0));
                    replaceStairsRecipe(Blocks.NETHER_BRICK_FENCE, new ItemStack(Blocks.NETHER_BRICK));
                    replaceStairsRecipe(Blocks.SANDSTONE_STAIRS, new ItemStack(Blocks.SANDSTONE, 1, 0));
                    replaceStairsRecipe(Blocks.RED_SANDSTONE_STAIRS, new ItemStack(Blocks.RED_SANDSTONE, 1, 0));
                    replaceStairsRecipe(Blocks.QUARTZ_STAIRS, new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0));
                }
            } else if (recipes.useBetterStoneToolRecipes) {
                if (output.getItem() == Items.STONE_AXE || output.getItem() == Items.STONE_HOE || output.getItem() == Items.STONE_PICKAXE || output.getItem() == Items.STONE_SHOVEL ||
                        output.getItem() == Items.STONE_SWORD) {
                    recipeRegistry.remove(r.getRegistryName());
                    recipeRegistry.register(DummyRecipe.from(r));
                    addShapedRecipe(new ItemStack(Items.STONE_SWORD), "S", "S", "T", 'S', "stone", 'T', "stickWood");
                    addShapedRecipe(new ItemStack(Items.STONE_SHOVEL), "S", "T", "T", 'S', "stone", 'T', "stickWood");
                    addShapedRecipe(new ItemStack(Items.STONE_PICKAXE), "SSS", " T ", " T ", 'S', "stone", 'T', "stickWood");
                    addShapedRecipe(new ItemStack(Items.STONE_AXE), "SS", "ST", " T", 'S', "stone", 'T', "stickWood");
                    addShapedRecipe(new ItemStack(Items.STONE_HOE), "SS", " T", " T", 'S', "stone", 'T', "stickWood");
                }
                GameRegistry.addSmelting(Items.EGG, new

                        ItemStack(VTItems.fried_egg), 0.35F);
            }
        }
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        replaceRecipes();
    }
}