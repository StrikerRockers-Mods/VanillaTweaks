package io.github.strikerrocker.vt.enchantments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class VeteranEnchantment extends Enchantment {
    VeteranEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }

    @SubscribeEvent
    public static void onTick(TickEvent.WorldTickEvent event) {
        if (EnchantmentInit.enableVeteran.get() && event.world != null && !event.world.isClientSide()) {
            ServerLevel world = (ServerLevel) event.world;
            world.getEntities(EntityType.EXPERIENCE_ORB, EntitySelector.ENTITY_STILL_ALIVE).forEach(experienceOrb -> EnchantmentImpl.moveXP(experienceOrb, EnchantmentInit.VETERAN.get()));
        }
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 10;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return 40;
    }

    @Override
    public int getMaxLevel() {
        return EnchantmentInit.enableVeteran.get() ? 1 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlot().equals(EquipmentSlot.HEAD) &&
                EnchantmentInit.enableVeteran.get();
    }

    @Override
    public boolean isDiscoverable() {
        return EnchantmentInit.enableVeteran.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return EnchantmentInit.veteranTreasureOnly.get();
    }

    @Override
    public boolean isTradeable() {
        return EnchantmentInit.enableVeteran.get();
    }
}
