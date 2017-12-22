package com.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Gives you the drops directly when harvesting blocks
 */
@SuppressWarnings("unused")
public class EnchantmentSiphon extends VTEnchantmentBase {
	public EnchantmentSiphon() {
		super("siphon", Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, EntityEquipmentSlot.MAINHAND);
	}

	@Override
	public void performAction(Entity entity, Event baseEvent) {
		if (entity != null && this.getEnchantmentLevel(((EntityLivingBase) entity).getHeldItemMainhand()) > 0) {
			HarvestDropsEvent event = (HarvestDropsEvent) baseEvent;
			List<ItemStack> drops = event.getDrops();
			drops.removeAll(drops.stream().filter(event.getHarvester().inventory::addItemStackToInventory).collect(Collectors.toList()));
		}
	}

	@Override
	public int getMinimumEnchantability(int enchantmentLevel) {
		return 15;
	}

	@Override
	public int getMaximumEnchantability(int enchantmentLevel) {
		return 61;
	}
}
