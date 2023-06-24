package io.github.strikerrocker.vt.content.items.dynamite;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
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
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemstack = user.getItemInHand(hand);
        if (!user.isCreative())
            itemstack.shrink(1);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        user.getCooldowns().addCooldown(this, VanillaTweaksFabric.config.content.dynamiteCooldown);
        if (!world.isClientSide) {
            DynamiteEntity dynamite = new DynamiteEntity(world, user);
            dynamite.setItem(itemstack);
            dynamite.shootFromRotation(user, user.getXRot(), user.getYRot(), 0, 1.5F, 0);
            world.addFreshEntity(dynamite);
        }
        user.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide);
    }
}