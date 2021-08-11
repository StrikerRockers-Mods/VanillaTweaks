package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ShearNameTag extends Feature {
    private ForgeConfigSpec.BooleanValue shearOffNameTag;

    /**
     * Make the nametag shear-able
     */
    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();
        Entity target = event.getTarget();
        ItemStack heldItem = player.getItemInHand(event.getHand());
        if (shearOffNameTag.get() && !heldItem.isEmpty() && heldItem.getItem() instanceof ShearsItem &&
                target instanceof LivingEntity && target.hasCustomName() && !player.level.isClientSide()) {
            target.playSound(SoundEvents.SHEEP_SHEAR, 1, 1);
            ItemStack nameTag = new ItemStack(Items.NAME_TAG).setHoverName(target.getCustomName());
            nameTag.getOrCreateTag().putInt("RepairCost", 0);
            target.spawnAtLocation(nameTag);
            target.setCustomName(null);
            heldItem.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
        }
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        shearOffNameTag = builder
                .translation("config.vanillatweaks:shearOffNameTag")
                .comment("Want to shear nametag of named entities?")
                .define("shearOffNameTag", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

}