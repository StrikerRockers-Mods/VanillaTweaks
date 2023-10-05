package io.github.strikerrocker.vt.world.loot_conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import io.github.strikerrocker.vt.world.WorldModule;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class KilledByFoxLootCondition implements LootItemCondition {
    public static final KilledByFoxLootCondition INSTANCE = new KilledByFoxLootCondition();
    public static final Codec<KilledByFoxLootCondition> CODEC = Codec.unit(INSTANCE);

    public static LootItemCondition.Builder builder() {
        return () -> INSTANCE;
    }

    @Override
    public LootItemConditionType getType() {
        return WorldModule.KILLED_BY_FOX;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.getParamOrNull(LootContextParams.KILLER_ENTITY) instanceof Fox;
    }
}