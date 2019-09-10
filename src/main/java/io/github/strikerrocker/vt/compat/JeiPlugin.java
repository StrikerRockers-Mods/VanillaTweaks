package io.github.strikerrocker.vt.compat;

import io.github.strikerrocker.vt.content.items.Items;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadContainer;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import static io.github.strikerrocker.vt.VTModInfo.MODID;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        if (Items.enablePad.get())
            registration.addRecipeCatalyst(new ItemStack(Items.CRAFTING_PAD), VanillaRecipeCategoryUid.CRAFTING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(CraftingPadContainer.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MODID, MODID);
    }
}
