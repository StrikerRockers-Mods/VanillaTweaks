package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.base.FabricModule;
import io.github.strikerrocker.vt.world.loot_conditions.KilledByFoxLootCondition;
import io.github.strikerrocker.vt.world.loot_conditions.KilledByOcelotLootCondition;
import io.github.strikerrocker.vt.world.loot_conditions.KilledByWolfLootCondition;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class WorldModule extends FabricModule {
    public static final LootItemConditionType KILLED_BY_WOLF = register("killed_by_wolf", new KilledByWolfLootCondition.Serializer());
    public static final LootItemConditionType KILLED_BY_OCELOT = register("killed_by_ocelot", new KilledByOcelotLootCondition.Serializer());
    public static final LootItemConditionType KILLED_BY_FOX = register("killed_by_fox", new KilledByFoxLootCondition.Serializer());

    /**
     * Registers LootConditionType
     */
    private static LootItemConditionType register(String id, Serializer<? extends LootItemCondition> serializer) {
        return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, new ResourceLocation(id), new LootItemConditionType(serializer));
    }

    @Override
    public void addFeatures() {
        registerFeature("realistic_relationship", new RealisticRelationship());
        registerFeature("lava_pocket", new NoMoreLavaPocketGen());
    }
}