package io.github.strikerrocker.vt.content.items.craftingpad;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraftforge.registries.ObjectHolder;

public class CraftingPadContainer extends WorkbenchContainer {

    @ObjectHolder("vanillatweaks:crafting_pad")
    public static ContainerType<CraftingPadContainer> TYPE;

    public CraftingPadContainer(int id, PlayerInventory playerInventory) {
        super(id, playerInventory);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
