package io.github.strikerrocker.vt.content;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.WorldgenRandom;

public class SlimeBucketItem extends Item {
    public SlimeBucketItem() {
        super(new Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide) {
            ChunkPos chunkpos = new ChunkPos(user.blockPosition());
            boolean slime = WorldgenRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, ((WorldGenLevel) world).getSeed(), 987234911L).nextInt(10) == 0;
            user.displayClientMessage(new TranslatableComponent(slime ? "slime_bucket.chunk" : "slime_bucket.chunk.false"), true);
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, user.getItemInHand(hand));
    }
}