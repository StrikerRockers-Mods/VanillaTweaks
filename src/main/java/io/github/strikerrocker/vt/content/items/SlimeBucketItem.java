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
        super(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1));
        this.setRegistryName("slime");
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isClientSide()) {
            ChunkPos chunkpos = new ChunkPos(playerIn.blockPosition());
            boolean slime = SharedSeedRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, ((ServerWorld) worldIn).getSeed(), 987234911L).nextInt(10) == 0;
            playerIn.displayClientMessage(new TranslationTextComponent(slime ? "slime.chunk" : "slime.chunk.false"), true);
        }
        return new ActionResult<>(ActionResultType.PASS, playerIn.getItemInHand(handIn));
    }
}