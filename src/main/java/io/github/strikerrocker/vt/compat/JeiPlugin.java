package io.github.strikerrocker.vt.compat;

import io.github.strikerrocker.vt.content.items.Items;
import io.github.strikerrocker.vt.content.items.craftingpad.ContainerCraftingPad;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JeiPlugin implements IModPlugin {
    public JeiPlugin() {
    }

    @Override
    public void register(IModRegistry registry) {
        if (Items.enablePad) {
            registry.addRecipeCatalyst(new ItemStack(Items.craftingPad), VanillaRecipeCategoryUid.CRAFTING);
            IRecipeTransferRegistry transfer = registry.getRecipeTransferRegistry();
            transfer.addRecipeTransferHandler(ContainerCraftingPad.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
        }
    }
}
