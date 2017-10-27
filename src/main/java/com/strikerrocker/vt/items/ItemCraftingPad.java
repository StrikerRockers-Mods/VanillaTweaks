package com.strikerrocker.vt.items;

import com.strikerrocker.vt.main.vt;
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
public class ItemCraftingPad extends ItemBase {

    protected String name;

    public ItemCraftingPad(String name) {
        super(name);
        this.name = name;
        setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    @SuppressWarnings("NullableProblems")
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        player.openGui(vt.instance, 1, world, 0, 0, 0);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }


    public void registerItemModel(Item item) {
        vt.proxy.registerItemRenderer(this, 0, name);
    }


}
