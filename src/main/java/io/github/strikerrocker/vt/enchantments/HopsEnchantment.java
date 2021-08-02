package io.github.strikerrocker.vt.enchantments;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HopsEnchantment extends Enchantment {

    HopsEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
        this.setRegistryName("hops");
    }

    /**
     * Handles the jumping logic of Hops enchantment
     *
     * @param event LivingJumpEvent
     */
    @SubscribeEvent
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (EnchantmentFeature.enableHops.get() && !event.getEntity().level.isClientSide()) {
            float lvl = (float) EnchantmentHelper.getItemEnchantmentLevel(this, event.getEntityLiving().getItemBySlot(EquipmentSlot.FEET));
            if (lvl != 0) {
                entity.push(0, lvl / 10D, 0);
                if (entity instanceof ServerPlayer playerMP) {
                    playerMP.connection.send(new ClientboundSetEntityMotionPacket(playerMP));
                }
            }
        }
    }

    /**
     * Handles the falling logic of Hops enchantment
     *
     * @param event LivingFallEvent
     */
    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        if (EnchantmentFeature.enableHops.get() && !event.getEntity().level.isClientSide()) {
            event.setDistance(event.getDistance() - EnchantmentHelper.getItemEnchantmentLevel(this, event.getEntityLiving().getItemBySlot(EquipmentSlot.FEET)));
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
        return stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getSlot().equals(EquipmentSlot.FEET) && EnchantmentFeature.enableHops.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return EnchantmentFeature.enableHops.get();
    }
}
