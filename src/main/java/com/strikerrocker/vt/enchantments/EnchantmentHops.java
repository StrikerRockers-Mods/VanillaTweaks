package com.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Gives the wearer jump boost
 */
@SuppressWarnings("unused")
public class EnchantmentHops extends VTEnchantmentBase {
	public EnchantmentHops() {
		super("hops", Rarity.UNCOMMON, EnumEnchantmentType.ARMOR_FEET, EntityEquipmentSlot.FEET);
	}

	@Override
	public void performAction(Entity entity, Event baseEvent) {
		float enchantmentLevel = this.getEnchantmentLevel(((EntityLivingBase) entity).getItemStackFromSlot(EntityEquipmentSlot.FEET));
		if (baseEvent instanceof LivingJumpEvent)
			entity.motionY += enchantmentLevel / 10;
		else if (baseEvent instanceof LivingFallEvent) {
			LivingFallEvent fallEvent = (LivingFallEvent) baseEvent;
			fallEvent.setDistance(fallEvent.getDistance() - enchantmentLevel);
		}
	}

	@Override
	public int getMinimumEnchantability(int enchantmentLevel) {
		return 5 + (enchantmentLevel - 1) * 8;
	}

	@Override
	public int getMaximumEnchantability(int enchantmentLevel) {
		return enchantmentLevel * 10 + 51;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}
}
