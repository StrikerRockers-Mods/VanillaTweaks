package io.github.strikerrocker.vt.compat;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.content.items.ForgeItems;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadMenu;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * Handles JustEnoughItems compatibility
 */
@mezz.jei.api.JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        if (ForgeItems.enablePad.get())
            registration.addRecipeCatalyst(new ItemStack(ForgeItems.CRAFTING_PAD.get()), VanillaRecipeCategoryUid.CRAFTING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(CraftingPadMenu.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(VanillaTweaks.MOD_ID, VanillaTweaks.MOD_ID);
    }
}