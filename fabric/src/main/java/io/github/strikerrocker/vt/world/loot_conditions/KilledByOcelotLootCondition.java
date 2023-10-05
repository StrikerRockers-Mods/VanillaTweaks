package io.github.strikerrocker.vt.world.loot_conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import io.github.strikerrocker.vt.world.WorldModule;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;

public class KilledByOcelotLootCondition implements LootItemCondition {
    public static final KilledByOcelotLootCondition INSTANCE = new KilledByOcelotLootCondition();
    public static final Codec<KilledByOcelotLootCondition> CODEC = Codec.unit(INSTANCE);
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

}