package io.github.strikerrocker.vt.enchantments;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

class SiphonModifier extends LootModifier {
    public SiphonModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        Entity e = context.get(LootParameters.THIS_ENTITY);
        if (e instanceof PlayerEntity)
            generatedLoot.removeIf(((PlayerEntity) e)::addItemStackToInventory);
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<SiphonModifier> {
        @Override
        public SiphonModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
            return new SiphonModifier(conditionsIn);
        }

        @Override
        public JsonObject write(SiphonModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
