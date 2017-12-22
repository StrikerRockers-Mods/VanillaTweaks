package com.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EntitySelectors;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;
import java.util.Optional;

/**
 * Makes arrows shot out of bows track down their targets
 */
@SuppressWarnings("unused")
@EntityTickingEnchantment
public class EnchantmentHoming extends VTEnchantmentBase {
	public EnchantmentHoming() {
		super("homing", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.BOW, EntityEquipmentSlot.MAINHAND);
	}

	@Override
	public void performAction(Entity entity, Event baseEvent) {
		if (entity instanceof EntityArrow) {
			EntityArrow arrow = (EntityArrow) entity;
			EntityLivingBase shooter = (EntityLivingBase) arrow.shootingEntity;
			if (shooter != null && this.getEnchantmentLevel(shooter.getHeldItemMainhand()) > 0) {
				int homingLevel = this.getEnchantmentLevel(shooter.getHeldItemMainhand());
				double distance = Math.pow(2, homingLevel - 1) * 32;
				World world = arrow.worldObj;
				@SuppressWarnings("unchecked")
				List<EntityLivingBase> livingEntities = world.getEntities(EntityLivingBase.class, EntitySelectors.NOT_SPECTATING);
				EntityLivingBase target = null;
				for (EntityLivingBase livingEntity : livingEntities) {
					double distanceToArrow = livingEntity.getDistanceToEntity(arrow);
					if (distanceToArrow < distance && shooter.canEntityBeSeen(livingEntity) && !livingEntity.getPersistentID().equals(shooter.getPersistentID())) {
						distance = distanceToArrow;
						target = livingEntity;
					}
				}
				if (target != null) {
					double x = target.posX - arrow.posX;
					double y = target.getEntityBoundingBox().minY + target.height / 2 - (arrow.posY + arrow.height / 2);
					double z = target.posZ - arrow.posZ;
					arrow.setThrowableHeading(x, y, z, 1.25F, 0);
				}
			}
		}
	}

	@Override
	public int getMinimumEnchantability(int enchantmentLevel) {
		return (enchantmentLevel - 1) * 10 + 10;
	}

	@Override
	public int getMaximumEnchantability(int enchantmentLevel) {
		return enchantmentLevel * 10 + 51;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		return super.canApplyTogether(enchantment) && !Optional.of(enchantment).equals(VTEnchantments.getByName("quickdraw"));
	}
}
