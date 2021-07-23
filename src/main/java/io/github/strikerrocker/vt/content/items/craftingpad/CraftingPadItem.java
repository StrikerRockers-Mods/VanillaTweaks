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
        super(new Item.Properties().tab(ItemGroup.TAB_TOOLS).stacksTo(1));
        setRegistryName("pad");
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isClientSide()) {
            playerIn.openMenu(new SimpleNamedContainerProvider((id, playerInv, player) -> new CraftingPadContainer(id, playerInv, IWorldPosCallable.create(worldIn, player.blockPosition())), new TranslationTextComponent("item.vanillatweaks.pad")));
            return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getItemInHand(handIn));
        }
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getItemInHand(handIn));
    }
}
