package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public class EnchantmentBlazing extends Enchantment {
    EnchantmentBlazing(String name) {
        super(Rarity.VERY_RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName(name);
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

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ToolItem;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void harvestDropEvent(BlockEvent.HarvestDropsEvent event) {
        PlayerEntity player = event.getHarvester();
        if (player != null && EnchantmentHelper.getEnchantmentLevel(this, player.getHeldItemMainhand()) > 0) {
            List<ItemStack> dropsCopy = new ArrayList<>(event.getDrops());
            event.getDrops().clear();
            for (ItemStack drop : dropsCopy) {
                if (!drop.isEmpty()) {
                    ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(drop);
                    if (!smeltingResult.isEmpty()) {
                        smeltingResult = smeltingResult.copy();
                        smeltingResult.setCount(smeltingResult.getCount() * drop.getCount());
                        if (!(smeltingResult.getItem() instanceof BlockItem))
                            smeltingResult.setCount(smeltingResult.getCount() * (new SplittableRandom().nextInt(event.getFortuneLevel() + 1) + 1));
                        event.getDrops().add(smeltingResult);
                    } else
                        event.getDrops().add(drop);
                }
            }
        }
    }
}
