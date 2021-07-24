package io.github.strikerrocker.vt.content.items;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.WorldgenRandom;

public class SlimeBucketItem extends Item {
    SlimeBucketItem() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1));
        this.setRegistryName("slime");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!worldIn.isClientSide()) {
            ChunkPos chunkpos = new ChunkPos(playerIn.blockPosition());
            boolean slime = WorldgenRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, ((ServerLevel) worldIn).getSeed(), 987234911L).nextInt(10) == 0;
            playerIn.displayClientMessage(new TranslatableComponent(slime ? "slime.chunk" : "slime.chunk.false"), true);
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, playerIn.getItemInHand(handIn));
    }
}