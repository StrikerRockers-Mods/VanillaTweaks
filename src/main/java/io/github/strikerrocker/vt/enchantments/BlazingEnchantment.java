package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SplittableRandom;

public class BlazingEnchantment extends Enchantment {
    BlazingEnchantment(String name) {
        super(Rarity.VERY_RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName(name);
    }

    public static void harvestDropEvent(List<ItemStack> drops, PlayerEntity player, ItemStack tool) {
        if (EnchantmentFeature.enableBlazing.get()) {
            List<ItemStack> dropsCopy = new ArrayList<>(drops);
            drops.clear();
            int fortuneLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool);
            for (ItemStack drop : dropsCopy) {
                if (!drop.isEmpty()) {
                    Optional<FurnaceRecipe> optional = player.world.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(drop), player.world);
                    if (optional.isPresent()) {
                        ItemStack smeltingResult = optional.get().getRecipeOutput();
                        if (!smeltingResult.isEmpty()) {
                            smeltingResult = smeltingResult.copy();
                            smeltingResult.setCount(smeltingResult.getCount() * drop.getCount());
                            if (!(smeltingResult.getItem() instanceof BlockItem))
                                smeltingResult.setCount(smeltingResult.getCount() * (new SplittableRandom().nextInt(fortuneLvl + 1) + 1));
                            drops.add(smeltingResult);
                        } else
                            drops.add(drop);
                    }
                }
            }
        }
        //TODO remove asm when https://github.com/MinecraftForge/MinecraftForge/pull/5871 is merged
    }

    @Override
    public int getMaxLevel() {
        return EnchantmentFeature.enableBlazing.get() ? 1 : 0;
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
        return stack.getItem() instanceof ToolItem && EnchantmentFeature.enableBlazing.get();
    }

    @Override
    public boolean isTreasureEnchantment() {
        return EnchantmentFeature.enableBlazing.get();
    }
}
