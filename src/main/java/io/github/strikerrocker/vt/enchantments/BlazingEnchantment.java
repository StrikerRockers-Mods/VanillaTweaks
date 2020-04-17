package io.github.strikerrocker.vt.enchantments;

import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BlazingEnchantment extends Enchantment {
    BlazingEnchantment(String name) {
        super(Rarity.VERY_RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName(name);
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

    @Override
    public boolean isAllowedOnBooks() {
        return EnchantmentFeature.enableBlazing.get();
    }

    private static class BlazingModifier extends LootModifier {
        public BlazingModifier(ILootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        private static ItemStack smelt(ItemStack stack, LootContext context) {
            System.out.println("called blazing");
            return context.getWorld().getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(stack), context.getWorld())
                    .map(FurnaceRecipe::getRecipeOutput)
                    .filter(itemStack -> !itemStack.isEmpty())
                    .map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount()))
                    .orElse(stack);
        }

        @Nonnull
        @Override
        public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            ArrayList<ItemStack> ret = new ArrayList<>();
            generatedLoot.forEach((stack) -> ret.add(smelt(stack, context)));
            return ret;
        }
    }

    public static class Serializer extends GlobalLootModifierSerializer<BlazingModifier> {
        @Override
        public BlazingModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
            return new BlazingModifier(conditionsIn);
        }
    }
}
