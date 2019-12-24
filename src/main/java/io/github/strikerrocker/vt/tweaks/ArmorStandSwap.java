package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Arrays;

public class ArmorStandSwap extends Feature {
    private ForgeConfigSpec.BooleanValue enableArmorStandSwapping;

    private static void swapSlot(PlayerEntity player, ArmorStandEntity armorStand, EquipmentSlotType slot) {
        ItemStack playerItem = player.getItemStackFromSlot(slot);
        ItemStack armorStandItem = armorStand.getItemStackFromSlot(slot);
        player.setItemStackToSlot(slot, armorStandItem);
        armorStand.setItemStackToSlot(slot, playerItem);
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enableArmorStandSwapping = builder
                .translation("config.vanillatweaks:enableArmorStandSwapping")
                .comment("Want an way to swap armor with armor stand?")
                .define("enableArmorStandSwapping", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        PlayerEntity player = event.getPlayer();
        Entity target = event.getTarget();
        if (player.isCrouching() && enableArmorStandSwapping.get()) {
            if (target.world.isRemote || player.isSpectator() || player.isCreative() || !(target instanceof ArmorStandEntity))
                return;
            event.setCanceled(true);
            ArmorStandEntity armorStand = (ArmorStandEntity) target;
            Arrays.stream(EquipmentSlotType.values()).filter(equipmentSlotType -> equipmentSlotType.getSlotType() == EquipmentSlotType.Group.ARMOR).forEach(equipmentSlotType -> {
                ItemStack playerItem = player.getItemStackFromSlot(equipmentSlotType);
                ItemStack armorStandItem = armorStand.getItemStackFromSlot(equipmentSlotType);
                player.setItemStackToSlot(equipmentSlotType, armorStandItem);
                armorStand.setItemStackToSlot(equipmentSlotType, playerItem);
            });
        }
    }
}