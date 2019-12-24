package io.github.strikerrocker.vt.content.items.dynamite;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DynamiteItem extends Item {
    public DynamiteItem(String name) {
        super(new Item.Properties().group(ItemGroup.MISC).maxStackSize(16));
        this.setRegistryName(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!playerIn.isCreative()) itemstack.shrink(1);
        BlockPos pos=playerIn.getPosition();
        worldIn.playSound(null,pos.getX(), pos.getY(),pos.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldownTracker().setCooldown(this, 20);
        if (!worldIn.isRemote) {
            DynamiteEntity dynamite = new DynamiteEntity(worldIn, playerIn);
            dynamite.setItem(itemstack);
            dynamite.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0, 1.5F, 0);
            playerIn.getEntityWorld().addEntity(dynamite);
        }
        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}
