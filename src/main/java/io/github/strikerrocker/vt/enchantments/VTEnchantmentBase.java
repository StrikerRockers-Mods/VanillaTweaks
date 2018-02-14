package io.github.strikerrocker.vt.enchantments;

import com.google.common.collect.Lists;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

/**
 * Base class for all of VanillaTweaks's enchantments
 */
public abstract class VTEnchantmentBase extends Enchantment {
    /**
     * A list of all of VanillaTweaks's enchantments
     */
    public static List<VTEnchantmentBase> vtEnchantments = Lists.newArrayList();

    VTEnchantmentBase(String name, Enchantment.Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot... slots) {
        super(rarityIn, typeIn, slots);
        this.setName(name);
        vtEnchantments.add(this);
    }

    /**
     * Gets the enchantment level of this enchantment on the specified ItemStack
     *
     * @param itemstack The ItemStack to check
     * @return The enchantment level of this enchantment on the ItemStack
     */
    int getEnchantmentLevel(ItemStack itemstack) {
        return EnchantmentHelper.getEnchantmentLevel(this, itemstack);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return getMinimumEnchantability(enchantmentLevel);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return getMaximumEnchantability(enchantmentLevel);
    }

    /**
     * Performs the action this enchantment does
     *
     * @param entity    The entity to go along with the enchantment
     * @param baseEvent The event to go along with the enchantment
     */
    public abstract void performAction(Entity entity, Event baseEvent);

    protected abstract int getMinimumEnchantability(int enchantmentLevel);

    protected abstract int getMaximumEnchantability(int enchantmentLevel);

}
