package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.world.loot_conditions.KilledByFoxLootCondition;
import io.github.strikerrocker.vt.world.loot_conditions.KilledByOcelotLootCondition;
import io.github.strikerrocker.vt.world.loot_conditions.KilledByWolfLootCondition;
import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class RealisticRelationship extends Feature {
    private static final ResourceLocation SHEEP_LOOT_TABLE_ID = new ResourceLocation("entities/sheep");
    private static final ResourceLocation CHICKEN_LOOT_TABLE_ID = new ResourceLocation("entities/chicken");
    private static final ResourceLocation RABBIT_LOOT_TABLE_ID = new ResourceLocation("entities/rabbit");

    @Override
    public void initialize() {
        if (VanillaTweaksFabric.config.world.realisticRelationship) {
            LootTableEvents.REPLACE.register(((resourceManager, lootManager, id, original, source) -> {
                if (source == LootTableSource.VANILLA) {
                    if (id.equals(SHEEP_LOOT_TABLE_ID))
                        return addConditionToPool(KilledByWolfLootCondition.builder().invert().build(), original);
                    if (id.equals(CHICKEN_LOOT_TABLE_ID))
                        return addConditionToPool(KilledByFoxLootCondition.builder().or(KilledByOcelotLootCondition.builder()).invert().build(), original);
                    if (id.equals(RABBIT_LOOT_TABLE_ID))
                        return addConditionToPool(KilledByFoxLootCondition.builder().or(KilledByWolfLootCondition.builder()).invert().build(), original);
                }
                return null;
            }));
        }
    }

    /**
     * Add the given LootCondition to given LootTable and return new instance of it
     */
    public LootTable addConditionToPool(LootItemCondition condition, LootTable original) {
        LootTable.Builder builder = new LootTable.Builder();
        for (LootPool pool : original.pools) {
            builder.withPool(FabricLootPoolBuilder.copyOf(pool).conditionally(condition));
        }
        return builder.build();
    }
}