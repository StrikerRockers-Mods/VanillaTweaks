package io.github.strikerrocker.vt.content.items.craftingpad;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCraftingPad extends Item {
    public ItemCraftingPad() {
        super(new Item.Properties().group(ItemGroup.TOOLS));
        setRegistryName("pad");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BlockPos pos = playerIn.getPosition();
        //playerIn.openGui(VanillaTweaks.instance, GuiHandler.PAD, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
    }
}
