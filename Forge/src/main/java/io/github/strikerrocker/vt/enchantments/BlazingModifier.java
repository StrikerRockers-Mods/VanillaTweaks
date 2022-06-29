package io.github.strikerrocker.vt.enchantments;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

/**
 * Handles the functionality of Blazing enchantment
 */
class BlazingModifier extends LootModifier {
    public BlazingModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> dropList, LootContext context) {
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
        Entity entity = context.getParam(LootContextParams.THIS_ENTITY);
        if (tool == null || !(entity instanceof Player)) return dropList;
        ObjectArrayList<ItemStack> newDropList = new ObjectArrayList<>();
        dropList.forEach(stack -> newDropList.add(EnchantmentImpl.smelt(stack, context.getLevel())));
        return newDropList;
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