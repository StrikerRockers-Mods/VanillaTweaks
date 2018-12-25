package io.github.strikerrocker.vt.items;

import io.github.strikerrocker.vt.VT;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;


/**
 * A portable crafting pad that allows you to craft items on the go
 */
public class ItemCraftingPad extends Item {

    ItemCraftingPad(String name) {
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.openGui(VT.instance, 0, worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ());
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}