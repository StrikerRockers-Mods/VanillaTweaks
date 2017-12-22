package com.strikerrocker.vt.enchantments;

import com.google.common.collect.Lists;
import com.strikerrocker.vt.vtModInfo;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * Base class for all of Craft++'s enchantments
 */
public abstract class VTEnchantmentBase extends Enchantment {
    /**
     * A list of all of Craft++'s enchantments
     */
    public static List<VTEnchantmentBase> cppEnchantments = Lists.newArrayList();

    protected VTEnchantmentBase(String name, Enchantment.Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot... slots) {
        super(rarityIn, typeIn, slots);
        this.setName(name);
        REGISTRY.register(findFreeEnchantmentID(name), new ResourceLocation(vtModInfo.MOD_ID + ":" + name), this);
        cppEnchantments.add(this);
    }

    /**
     * Finds the first free enchantment ID to register this enchantment
     *
     * @param enchantmentName the name of the enchantment
     * @return The enchantment ID for this enchantment to use
     */
    private static int findFreeEnchantmentID(String enchantmentName) {
        OptionalInt freeEnchantmentID = IntStream.range(0, 256).filter(i -> Enchantment.getEnchantmentByID(i) == null).findFirst();
        if (!freeEnchantmentID.isPresent())
            throw new NoFreeEnchantmentIDException(enchantmentName);
        return freeEnchantmentID.getAsInt();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    /**
     * Gets the enchantment level of this enchantment on the specified ItemStack
     *
     * @param itemstack The ItemStack to check
     * @return The enchantment level of this enchantment on the ItemStack
     */
    protected int getEnchantmentLevel(ItemStack itemstack) {
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

    public abstract int getMinimumEnchantability(int enchantmentLevel);

    public abstract int getMaximumEnchantability(int enchantmentLevel);

    @SideOnly(Side.CLIENT)
    private static class NoFreeEnchantmentIDException extends RuntimeException {
        private NoFreeEnchantmentIDException(String enchantmentName) {
            super("Could not find a free enchantment ID for " + I18n.format("enchantment." + enchantmentName));
        }
    }
}
