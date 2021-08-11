package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ArmorStandSwap extends Feature {
    private ForgeConfigSpec.BooleanValue enableArmorStandSwapping;

    /**
     * Swaps the items in given slot of Player and ArmorStand
     */
    private static void swapSlot(Player player, ArmorStand armorStand, EquipmentSlot slot) {
        ItemStack playerItem = player.getItemBySlot(slot);
        ItemStack armorStandItem = armorStand.getItemBySlot(slot);
        player.setItemSlot(slot, armorStandItem);
        armorStand.setItemSlot(slot, playerItem);
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

    /**
     * Swaps all the armor slots of armor stand and player when shift right clicked
     */
    @SubscribeEvent
    public void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        Player player = event.getPlayer();
        if (player.isCrouching() && enableArmorStandSwapping.get() && !player.level.isClientSide() && !player.isSpectator()
                && event.getTarget() instanceof ArmorStand armorStand) {
            event.setCanceled(true);
            for (EquipmentSlot equipmentSlotType : EquipmentSlot.values()) {
                if (equipmentSlotType.getType() == EquipmentSlot.Type.ARMOR) {
                    swapSlot(player, armorStand, equipmentSlotType);
                }
            }
        }
    }
}