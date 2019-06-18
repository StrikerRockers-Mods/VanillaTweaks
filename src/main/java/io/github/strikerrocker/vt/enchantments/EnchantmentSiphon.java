package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.stream.Collectors;

public class EnchantmentSiphon extends Enchantment {
    EnchantmentSiphon(String name) {
        super(Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName(name);
        this.setName(name);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void harvestDropEvent(BlockEvent.HarvestDropsEvent event) {
        if (event.getHarvester() != null && EnchantmentHelper.getEnchantmentLevel(this, event.getHarvester().getHeldItemMainhand()) > 0) {
            event.getDrops().removeAll(event.getDrops().stream().filter(event.getHarvester().inventory::addItemStackToInventory).collect(Collectors.toList()));
        }
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 61;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ItemTool;
    }
}
