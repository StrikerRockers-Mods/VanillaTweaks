package com.strikerrocker.vt.compat.jei;

import com.strikerrocker.vt.blocks.VTBlocks;
import com.strikerrocker.vt.gui.ContainerCraftingPad;
import com.strikerrocker.vt.items.VTItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

import static com.strikerrocker.vt.handlers.VTConfigHandler.craftingPad;

@JEIPlugin
public class VTJeiPlugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
        if (craftingPad) {
            registry.addRecipeCatalyst(new ItemStack(VTItems.pad), VanillaRecipeCategoryUid.CRAFTING);
            IRecipeTransferRegistry transfer = registry.getRecipeTransferRegistry();
            transfer.addRecipeTransferHandler(ContainerCraftingPad.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
        }
        registry.addIngredientInfo(Arrays.asList(

                new ItemStack(VTBlocks.acaciabark),
                new ItemStack(VTBlocks.junglebark),
                new ItemStack(VTBlocks.sprucebark),
                new ItemStack(VTBlocks.oakbark),
                new ItemStack(VTBlocks.darkoakbark),
                new ItemStack(VTBlocks.birchbark)
                ),
                ItemStack.class,
                "Barks carved out of logs for your blocks"
        );
    }
}
