package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HopsEnchantment extends Enchantment {

    HopsEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[]{EquipmentSlotType.FEET});
        this.setRegistryName("hops");
    }

    @SubscribeEvent
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (EnchantmentFeature.enableHops.get() && !event.getEntity().level.isClientSide()) {
            float lvl = (float) EnchantmentHelper.getItemEnchantmentLevel(this, event.getEntityLiving().getItemBySlot(EquipmentSlotType.FEET));
            if (lvl != 0) {
                entity.push(0, lvl / 10D, 0);
                if (entity instanceof ServerPlayerEntity) {
                    ServerPlayerEntity playerMP = (ServerPlayerEntity) entity;
                    playerMP.connection.send(new SEntityVelocityPacket(playerMP));
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        if (EnchantmentFeature.enableHops.get() && !event.getEntity().level.isClientSide()) {
            event.setDistance(event.getDistance() - EnchantmentHelper.getItemEnchantmentLevel(this, event.getEntityLiving().getItemBySlot(EquipmentSlotType.FEET)));
        }
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 8;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return enchantmentLevel * 10 + 51;
    }

    @Override
    public int getMaxLevel() {
        return EnchantmentFeature.enableHops.get() ? 3 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getSlot().equals(EquipmentSlotType.FEET) && EnchantmentFeature.enableHops.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return EnchantmentFeature.enableHops.get();
    }
}
