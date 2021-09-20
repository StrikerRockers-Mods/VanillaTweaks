package io.github.strikerrocker.vt.enchantments;

import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class BlazingModifier extends LootModifier {
    public BlazingModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    private static ItemStack smelt(ItemStack stack, LootContext context) {
        return context.getLevel().getRecipeManager().getRecipeFor(IRecipeType.SMELTING, new Inventory(stack), context.getLevel())
                .map(FurnaceRecipe::getResultItem)
                .filter(itemStack -> !itemStack.isEmpty())
                .map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount()))
                .orElse(stack);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        ItemStack tool = context.getParamOrNull(LootParameters.TOOL);
        if (tool == null) return generatedLoot;
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(tool);
        if (enchantments.containsKey(Enchantments.BLOCK_FORTUNE) && enchantments.containsKey(EnchantmentFeature.enchantments.get("blazing").getA())) {
            return generatedLoot;
        }
        ArrayList<ItemStack> ret = new ArrayList<>();
        generatedLoot.forEach(stack -> ret.add(smelt(stack, context)));
        return ret;
    }

    public static class Serializer extends GlobalLootModifierSerializer<BlazingModifier> {
        @Override
        public BlazingModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
            return new BlazingModifier(conditionsIn);
        }

        @Override
        public JsonObject write(BlazingModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
