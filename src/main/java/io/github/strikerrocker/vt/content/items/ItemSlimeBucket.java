package io.github.strikerrocker.vt.content.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemSlimeBucket extends Item {
    ItemSlimeBucket(String name) {
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote) {
            int x = MathHelper.floor(playerIn.posX);
            int z = MathHelper.floor(playerIn.posZ);
            boolean slime = worldIn.getChunk(new BlockPos(x, 0, z)).getRandomWithSeed(987234911L).nextInt(10) == 0;
            playerIn.sendStatusMessage(new TextComponentTranslation(slime ? "slime.chunk" : "slime.chunk.false"), true);

        }
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
