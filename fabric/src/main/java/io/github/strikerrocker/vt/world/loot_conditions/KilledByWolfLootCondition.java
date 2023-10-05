package io.github.strikerrocker.vt.world.loot_conditions;

import com.mojang.serialization.Codec;
import io.github.strikerrocker.vt.world.WorldModule;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class KilledByWolfLootCondition implements LootItemCondition {
    public static final KilledByWolfLootCondition INSTANCE = new KilledByWolfLootCondition();
    public static final Codec<KilledByWolfLootCondition> CODEC = Codec.unit(INSTANCE);

    private KilledByWolfLootCondition() {
    }

    public static LootItemCondition.Builder builder() {
        return () -> INSTANCE;
    }

    @Override
    public LootItemConditionType getType() {
        return WorldModule.KILLED_BY_WOLF;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.getParamOrNull(LootContextParams.KILLER_ENTITY) instanceof Wolf;
    }
}