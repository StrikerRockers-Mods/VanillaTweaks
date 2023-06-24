package io.github.strikerrocker.vt.world.loot_conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import io.github.strikerrocker.vt.world.WorldModule;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class KilledByOcelotLootCondition implements LootItemCondition {
    public static final KilledByOcelotLootCondition INSTANCE = new KilledByOcelotLootCondition();

    public static LootItemCondition.Builder builder() {
        return () -> INSTANCE;
    }

    @Override
    public LootItemConditionType getType() {
        return WorldModule.KILLED_BY_OCELOT;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.getParamOrNull(LootContextParams.KILLER_ENTITY) instanceof Ocelot;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<KilledByOcelotLootCondition> {
        @Override
        public void serialize(JsonObject jsonObject, KilledByOcelotLootCondition object, JsonSerializationContext jsonSerializationContext) {

        }

        public KilledByOcelotLootCondition deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return INSTANCE;
        }
    }
}