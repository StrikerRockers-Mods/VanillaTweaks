package io.github.strikerrocker.vt.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import static io.github.strikerrocker.vt.events.VTEventHandler.isSlimeChunk;
import static io.github.strikerrocker.vt.handlers.ConfigHandler.VanillaTweaks.slimeChunkFinder;

public class ItemSlimeBucket extends Item {

    public ItemSlimeBucket(String name) {
        this.setCreativeTab(CreativeTabs.MISC);
        this.setMaxStackSize(1);
        this.setTranslationKey(name);
        this.setRegistryName(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote && slimeChunkFinder) {
            int x = MathHelper.floor(playerIn.posX);
            int z = MathHelper.floor(playerIn.posZ);
            boolean slime = isSlimeChunk(worldIn, x, z);
            if (slime) {
                playerIn.sendStatusMessage(new TextComponentTranslation("slime.chunk"), true);
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
