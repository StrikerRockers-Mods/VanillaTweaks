package io.github.strikerrocker.vt.content.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SlimeBucketItem extends Item {
    SlimeBucketItem() {
        super(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1));
        this.setRegistryName("slime");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            ChunkPos chunkpos = new ChunkPos(playerIn.func_233580_cy_());
            boolean slime = SharedSeedRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, ((ServerWorld) worldIn).getSeed(), 987234911L).nextInt(10) == 0;
            playerIn.sendStatusMessage(new TranslationTextComponent(slime ? "slime.chunk" : "slime.chunk.false"), true);
        }
        return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
    }
}