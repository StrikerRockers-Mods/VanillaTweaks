package com.strikerrocker.vt.compat;

import com.strikerrocker.vt.gui.ContainerCraftingPad;
import com.strikerrocker.vt.items.VTItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

import static com.strikerrocker.vt.handlers.VTConfigHandler.craftingPad;

@JEIPlugin
public class VTJeiPlugin implements IModPlugin {
    @Override
    public void register(@Nonnull IModRegistry registry) {
        IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
        if (craftingPad) {
            registry.addRecipeCatalyst(new ItemStack(VTItems.pad), VanillaRecipeCategoryUid.CRAFTING);
            IRecipeTransferRegistry transfer = registry.getRecipeTransferRegistry();
            transfer.addRecipeTransferHandler(ContainerCraftingPad.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
        }
    }
}
