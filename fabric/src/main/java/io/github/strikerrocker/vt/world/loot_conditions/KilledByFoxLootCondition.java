package io.github.strikerrocker.vt.world.loot_conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import io.github.strikerrocker.vt.world.WorldModule;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class KilledByFoxLootCondition implements LootItemCondition {
    public static final KilledByFoxLootCondition INSTANCE = new KilledByFoxLootCondition();

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

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<KilledByFoxLootCondition> {
        @Override
        public void serialize(JsonObject jsonObject, KilledByFoxLootCondition object, JsonSerializationContext jsonSerializationContext) {

        }

        public KilledByFoxLootCondition deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return INSTANCE;
        }
    }
}