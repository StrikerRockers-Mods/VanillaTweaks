package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ShearNameTag extends Feature {
    private ForgeConfigSpec.BooleanValue shearOffNameTag;

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        PlayerEntity player = event.getPlayer();
        Entity target = event.getTarget();
        ItemStack heldItem = !player.getMainHandItem().isEmpty() ? player.getMainHandItem() : player.getOffhandItem();
        if (shearOffNameTag.get() && !heldItem.isEmpty()) {
            World world = player.level;
            if (heldItem.getItem() instanceof ShearsItem && target instanceof LivingEntity && target.hasCustomName() && !world.isClientSide()) {
                target.playSound(SoundEvents.SHEEP_SHEAR, 1, 1);
                ItemStack nameTag = new ItemStack(Items.NAME_TAG).setHoverName(target.getCustomName());
                nameTag.getTag().putInt("RepairCost", 0);
                target.spawnAtLocation(nameTag, 0);
                target.setCustomName(null);
                heldItem.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
            }
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