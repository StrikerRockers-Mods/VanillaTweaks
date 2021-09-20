package io.github.strikerrocker.vt.enchantments;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the functionality of Blazing enchantment
 */
class BlazingModifier extends LootModifier {
    public BlazingModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    /**
     * Returns the result of the smelting recipe
     *
     * @param stack   The input stack
     * @param context The loot context
     * @return The output stack or the input stack if an recipe doesn't exist
     */
    private static ItemStack smelt(ItemStack stack, LootContext context) {
        return context.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), context.getLevel())
                .map(SmeltingRecipe::getResultItem)
                .filter(itemStack -> !itemStack.isEmpty())
                .map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount()))
                .orElse(stack);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
        if (tool == null) return generatedLoot;
        ArrayList<ItemStack> ret = new ArrayList<>();
        generatedLoot.forEach(stack -> ret.add(smelt(stack, context)));
        return ret;
    }

    public static class Serializer extends GlobalLootModifierSerializer<BlazingModifier> {
        @Override
        public BlazingModifier read(ResourceLocation name, JsonObject json, LootItemCondition[] conditionsIn) {
            return new BlazingModifier(conditionsIn);
        }

        @Override
        public JsonObject write(BlazingModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
