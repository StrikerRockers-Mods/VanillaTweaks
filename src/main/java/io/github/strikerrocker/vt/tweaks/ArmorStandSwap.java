package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ArmorStandSwap extends Feature {
    private boolean enableArmorStandSwapping;

    private static void swapSlot(EntityPlayer player, EntityArmorStand armorStand, EntityEquipmentSlot slot) {
        ItemStack playerItem = player.getItemStackFromSlot(slot);
        ItemStack armorStandItem = armorStand.getItemStackFromSlot(slot);
        player.setItemStackToSlot(slot, armorStandItem);
        armorStand.setItemStackToSlot(slot, playerItem);
    }

    @Override
    public void syncConfig(Configuration config, String category) {
        enableArmorStandSwapping = config.get(category, "enableArmorStandSwapping", true, "Want an way to swap armor with armor stand?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        if (player.isSneaking() && enableArmorStandSwapping) {
            if (target.world.isRemote || player.isSpectator() || player.isCreative() || !(target instanceof EntityArmorStand))
                return;
            event.setCanceled(true);
            EntityArmorStand armorStand = (EntityArmorStand) target;
            swapSlot(player, armorStand, EntityEquipmentSlot.HEAD);
            swapSlot(player, armorStand, EntityEquipmentSlot.CHEST);
            swapSlot(player, armorStand, EntityEquipmentSlot.LEGS);
            swapSlot(player, armorStand, EntityEquipmentSlot.FEET);
        }
    }
}