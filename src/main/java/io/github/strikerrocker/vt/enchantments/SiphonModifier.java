package io.github.strikerrocker.vt.enchantments;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Handles the functionality of Siphon enchantment
 */
class SiphonModifier extends LootModifier {
    public SiphonModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        Entity e = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (e instanceof Player)
            generatedLoot.removeIf(((Player) e)::addItem);
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<SiphonModifier> {
        @Override
        public SiphonModifier read(ResourceLocation name, JsonObject json, LootItemCondition[] conditionsIn) {
            return new SiphonModifier(conditionsIn);
        }

        @Override
        public JsonObject write(SiphonModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
