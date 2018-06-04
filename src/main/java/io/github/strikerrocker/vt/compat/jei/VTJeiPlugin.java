package io.github.strikerrocker.vt.compat.jei;

import io.github.strikerrocker.vt.blocks.VTBlocks;
import io.github.strikerrocker.vt.gui.ContainerCraftingPad;
import io.github.strikerrocker.vt.handlers.ConfigHandler;
import io.github.strikerrocker.vt.items.VTItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

import java.util.Arrays;


@JEIPlugin
public class VTJeiPlugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        if (ConfigHandler.vanilla_tweaks.craftingPad) {
            registry.addRecipeCatalyst(new ItemStack(VTItems.pad), VanillaRecipeCategoryUid.CRAFTING);
            IRecipeTransferRegistry transfer = registry.getRecipeTransferRegistry();
            transfer.addRecipeTransferHandler(ContainerCraftingPad.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
        }
        if (ConfigHandler.miscellanious_restart.barks) {
            registry.addIngredientInfo(Arrays.asList(

                    new ItemStack(VTBlocks.acaciabark),
                    new ItemStack(VTBlocks.junglebark),
                    new ItemStack(VTBlocks.sprucebark),
                    new ItemStack(VTBlocks.oakbark),
                    new ItemStack(VTBlocks.darkoakbark),
                    new ItemStack(VTBlocks.birchbark)
                    ),
                    ItemStack.class,
                    "Barks carved out of logs for your crafty needs."
            );
        }
    }
}
