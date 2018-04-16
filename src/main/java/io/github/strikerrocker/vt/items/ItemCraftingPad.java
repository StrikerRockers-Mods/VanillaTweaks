package io.github.strikerrocker.vt.items;

import io.github.strikerrocker.vt.vt;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import static io.github.strikerrocker.vt.vt.proxy;


/**
 * A portable crafting pad that allows you to craft items on the go
 */
public class ItemCraftingPad extends ItemBase
{

    private final String name;

    public ItemCraftingPad(String name) {
        super(name);
        this.name = name;
        setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.openGui(vt.instance, 0, worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ());
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }


    public void registerItemModel(Item item) {
        proxy.registerItemRenderer(this, 0, name);
    }

}