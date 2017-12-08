package com.strikerrocker.vt.recipes;

import com.strikerrocker.vt.handlers.VTConfigHandler;
import com.strikerrocker.vt.misc.RecipeHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import static com.strikerrocker.vt.misc.RecipeHelper.addShapedRecipe;
import static com.strikerrocker.vt.misc.RecipeHelper.replaceStairsRecipe;

@Mod.EventBusSubscriber
public class VTRecipeReplacer {

    @SubscribeEvent
    public static void removeRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) event.getRegistry();
        ResourceLocation oak = new ResourceLocation("minecraft:oak_stairs");
        ResourceLocation acacia = new ResourceLocation("minecraft:acacia_stairs");
        ResourceLocation darkoak = new ResourceLocation("minecraft:dark_oak_stairs");
        ResourceLocation birch = new ResourceLocation("minecraft:birch_stairs");
        ResourceLocation stone = new ResourceLocation("minecraft:stone_stairs");
        ResourceLocation brick = new ResourceLocation("minecraft:brick_stairs");
        ResourceLocation stbrick = new ResourceLocation("minecraft:stone_brick_stairs");
        ResourceLocation nether = new ResourceLocation("minecraft:nether_brick_stairs");
        ResourceLocation sand = new ResourceLocation("minecraft:sandstone_stairs");
        ResourceLocation jungle = new ResourceLocation("minecraft:jungle_stairs");
        ResourceLocation quart = new ResourceLocation("minecraft:quartz_stairs");
        ResourceLocation reds = new ResourceLocation("minecraft:red_sandstone_stairs");
        ResourceLocation purpur = new ResourceLocation("minecraft:purpur_stairs");
        ResourceLocation spruce = new ResourceLocation("minecraft:spruce_stairs");
        ResourceLocation ssword = new ResourceLocation("minecraft:stone_sword");
        ResourceLocation spick = new ResourceLocation("minecraft:stone_pickaxe");
        ResourceLocation saxe = new ResourceLocation("minecraft:stone_axe");
        ResourceLocation sspade = new ResourceLocation("minecraft:stone_shovel");
        ResourceLocation shoe = new ResourceLocation("minecraft:stone_hoe");

        if (VTConfigHandler.useBetterStairsRecipes) {
            modRegistry.remove(oak);
            modRegistry.remove(darkoak);
            modRegistry.remove(acacia);
            modRegistry.remove(birch);
            modRegistry.remove(stone);
            modRegistry.remove(brick);
            modRegistry.remove(stbrick);
            modRegistry.remove(nether);
            modRegistry.remove(sand);
            modRegistry.remove(jungle);
            modRegistry.remove(quart);
            modRegistry.remove(reds);
            modRegistry.remove(purpur);
            modRegistry.remove(spruce);
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

        if (VTConfigHandler.useBetterStoneToolRecipes) {
            modRegistry.remove(spick);
            modRegistry.remove(saxe);
            modRegistry.remove(shoe);
            modRegistry.remove(ssword);
            modRegistry.remove(sspade);
            addShapedRecipe(new ItemStack(Items.STONE_SWORD), "S", "S", "T", 'S', "stone", 'T', "stickWood");
            addShapedRecipe(new ItemStack(Items.STONE_SHOVEL), "S", "T", "T", 'S', "stone", 'T', "stickWood");
            addShapedRecipe(new ItemStack(Items.STONE_PICKAXE), "SSS", " T ", " T ", 'S', "stone", 'T', "stickWood");
            addShapedRecipe(new ItemStack(Items.STONE_AXE), "SS", "ST", " T", 'S', "stone", 'T', "stickWood");
            addShapedRecipe(new ItemStack(Items.STONE_HOE), "SS", " T", " T", 'S', "stone", 'T', "stickWood");
        }
    }
}

