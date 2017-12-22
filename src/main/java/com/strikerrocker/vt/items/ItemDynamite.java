package com.strikerrocker.vt.items;

import com.strikerrocker.vt.entities.EntityDynamite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import static com.strikerrocker.vt.vt.proxy;

/**
 * Dynamite item to go along-side dynamite entity
 */
public class ItemDynamite extends ItemBase {

    private String name;

    public ItemDynamite(String name) {
        super(name);
        this.name = name;
        this.setCreativeTab(CreativeTabs.MISC);
        this.setMaxStackSize(16);
        setUnlocalizedName(name);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {

        ItemStack itemstack = playerIn.getHeldItem(hand);

        if (!playerIn.isCreative()) {
            --itemstack.stackSize;
        }

        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote) {
            EntityDynamite dynamite = new EntityDynamite(worldIn, playerIn);
            dynamite.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0, 1.5F, 0);
            playerIn.getEntityWorld().spawnEntityInWorld(dynamite);
        }
        playerIn.addStat(StatList.getObjectUseStats(this));
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public void registerItemModel(Item item) {
        proxy.registerItemRenderer(this,0,name);
    }
}
