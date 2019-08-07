package io.github.strikerrocker.vt.content.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemSlimeBucket extends Item {
    ItemSlimeBucket() {
        super(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1));
        this.setRegistryName("slime");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            int x = MathHelper.floor(playerIn.posX);
            int z = MathHelper.floor(playerIn.posZ);
            boolean slime;//TODO = worldIn.getChunk(new BlockPos(x, 0, z)).getRandomWithSeed(987234911L).nextInt(10) == 0;
            playerIn.sendStatusMessage(new TranslationTextComponent(slime ? "slime.chunk" : "slime.chunk.false"), true);

        }
        return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
    }
}
