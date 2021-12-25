package io.github.strikerrocker.vt.content.items.craftingpad;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CraftingPadItem extends Item {
    public CraftingPadItem() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide()) {
            user.openMenu(new SimpleMenuProvider(
                    (id, playerInventory, playerEntity)
                            -> new CraftingPadScreenHandler(id, playerInventory,
                            ContainerLevelAccess.create(world, user.blockPosition())),
                    new TranslatableComponent("item.vanillatweaks.crafting_pad")
            ));
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, user.getItemInHand(hand));
        }
        return InteractionResultHolder.sidedSuccess(user.getItemInHand(hand), world.isClientSide);
    }
}