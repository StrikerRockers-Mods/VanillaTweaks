package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class BlazingEnchantment extends Enchantment {
    BlazingEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public static List<ItemStack> blazingLogic(ServerLevel world, Entity entity, ItemStack tool, List<ItemStack> dropList) {
        RecipeManager recipeManager = world.getRecipeManager();
        Container simpleInv = new SimpleContainer(1);
        ItemStack itemToBeChecked;
        Optional<SmeltingRecipe> smeltingResult;
        ArrayList<ItemStack> newDropList = new ArrayList<>(dropList);
        if (!newDropList.isEmpty())
            for (int i = 0; i < newDropList.size(); i++) {
                itemToBeChecked = newDropList.get(i);
                simpleInv.setItem(0, itemToBeChecked);
                smeltingResult = recipeManager.getRecipeFor(RecipeType.SMELTING, simpleInv, entity.level);
                if (smeltingResult.isPresent() && entity instanceof Player playerEntity) {
                    int count = itemToBeChecked.getCount();
                    if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool) > 0) {
                        count = getFortuneCount(world.getRandom(), count, EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool));
                        tool.hurtAndBreak(Math.max(count - 1, 3), playerEntity, player -> player.broadcastBreakEvent(player.getUsedItemHand()));
                    }
                    newDropList.set(i, new ItemStack(smeltingResult.get().getResultItem().getItem(), count));
                    playerEntity.giveExperiencePoints((int) (smeltingResult.get().getExperience() * itemToBeChecked.getCount()));
                }
            }
        return newDropList;
    }

    public static int getFortuneCount(Random random, int initialCount, int lvl) {
        if (lvl > 0) {
            int i = random.nextInt(lvl + 2) - 1;
            if (i < 0) {
                i = 0;
            }
            return initialCount * (i + 1);
        } else {
            return initialCount;
        }
    }

    @Override
    public int getMaxLevel() {
        return VanillaTweaksFabric.config.enchanting.enableBlazing ? 1 : 0;
    }

    @Override
    public int getMinCost(int level) {
        return 15;
    }

    @Override
    public int getMaxCost(int level) {
        return 61;
    }

    @Override
    protected boolean checkCompatibility(Enchantment other) {
        return super.checkCompatibility(other) && other != Enchantments.SILK_TOUCH;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof TieredItem && VanillaTweaksFabric.config.enchanting.enableBlazing;
    }

    @Override
    public boolean isTreasureOnly() {
        return VanillaTweaksFabric.config.enchanting.blazingTreasureOnly;
    }

    @Override
    public boolean isTradeable() {
        return VanillaTweaksFabric.config.enchanting.enableBlazing;
    }
}