package io.github.strikerrocker.vt.content.items.craftingpad;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class CraftingPadItem extends Item {
    public CraftingPadItem() {
        super(new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1));
        setRegistryName("pad");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote())
            playerIn.openContainer(new SimpleNamedContainerProvider((id, playerInv, player) -> new CraftingPadContainer(id, playerInv, IWorldPosCallable.of(worldIn, player.getPosition())), new TranslationTextComponent("item.vanillatweaks.pad")));
        return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
    }
}
