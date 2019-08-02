package io.github.strikerrocker.vt.content.items.craftingpad;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.misc.GuiHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCraftingPad extends Item {
    public ItemCraftingPad(String name) {
        setRegistryName(name);
        setTranslationKey(name);
        setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        BlockPos pos = playerIn.getPosition();
        playerIn.openGui(VanillaTweaks.instance, GuiHandler.PAD, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
