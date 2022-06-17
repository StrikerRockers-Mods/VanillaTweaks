package io.github.strikerrocker.vt.content.items.craftingpad;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CraftingPadItem extends Item {
    public CraftingPadItem() {
        super(new Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide()) {
            user.openMenu(new SimpleMenuProvider((id, playerInventory, playerEntity) ->
                    new CraftingPadMenu(id, playerInventory, ContainerLevelAccess.create(world,
                            user.blockPosition()), hand == InteractionHand.OFF_HAND ?
                            Inventory.SLOT_OFFHAND :
                            playerInventory.selected
                    ), Component.translatable("item.vanillatweaks.crafting_pad")));
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, user.getItemInHand(hand));
        }
        return InteractionResultHolder.sidedSuccess(user.getItemInHand(hand), world.isClientSide);
    }
}