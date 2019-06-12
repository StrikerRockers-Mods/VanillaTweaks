package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public class EnchantmentBlazing extends Enchantment {
    EnchantmentBlazing(String name) {
        super(Rarity.VERY_RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName(name);
        this.setName(name);
    }

    @Override
    public int getMaxLevel() {
        return 1;
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
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.SILK_TOUCH;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void harvestDropEvent(BlockEvent.HarvestDropsEvent event) {
        EntityPlayer player = event.getHarvester();
        if (player != null && EnchantmentHelper.getEnchantmentLevel(this, player.getHeldItemMainhand()) > 0) {
            List<ItemStack> dropsCopy = new ArrayList<>(event.getDrops());
            event.getDrops().clear();
            for (ItemStack drop : dropsCopy) {
                if (!drop.isEmpty()) {
                    ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(drop);
                    if (!smeltingResult.isEmpty()) {
                        smeltingResult = smeltingResult.copy();
                        smeltingResult.setCount(smeltingResult.getCount() * drop.getCount());
                        if (!(smeltingResult.getItem() instanceof ItemBlock))
                            smeltingResult.setCount(smeltingResult.getCount() * (new SplittableRandom().nextInt(event.getFortuneLevel() + 1) + 1));
                        event.getDrops().add(smeltingResult);
                    } else
                        event.getDrops().add(drop);
                }
            }
        }
    }
}
