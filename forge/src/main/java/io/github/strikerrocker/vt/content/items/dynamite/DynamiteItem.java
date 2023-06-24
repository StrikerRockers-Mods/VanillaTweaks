package io.github.strikerrocker.vt.content.items.dynamite;

import io.github.strikerrocker.vt.content.items.ForgeItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DynamiteItem extends Item {
    public DynamiteItem() {
        super(new Item.Properties().stacksTo(16));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (!playerIn.isCreative())
            itemstack.shrink(1);
        BlockPos pos = playerIn.blockPosition();
        worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldowns().addCooldown(this, ForgeItems.dynamiteCooldown.get());
        if (!worldIn.isClientSide()) {
            DynamiteEntity dynamite = new DynamiteEntity(worldIn, playerIn);
            dynamite.setItem(itemstack);
            dynamite.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0, 1.5F, 0);
            playerIn.getCommandSenderWorld().addFreshEntity(dynamite);
        }
        playerIn.awardStat(Stats.ITEM_USED.get(this));
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }
}