package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.world.loot_conditions.KilledByFoxLootCondition;
import io.github.strikerrocker.vt.world.loot_conditions.KilledByOcelotLootCondition;
import io.github.strikerrocker.vt.world.loot_conditions.KilledByWolfLootCondition;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplier;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback.LootTableSetter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public class RealisticRelationship extends Feature {
    private static final ResourceLocation SHEEP_LOOT_TABLE_ID = new ResourceLocation("entities/sheep");
    private static final ResourceLocation CHICKEN_LOOT_TABLE_ID = new ResourceLocation("entities/chicken");
    private static final ResourceLocation RABBIT_LOOT_TABLE_ID = new ResourceLocation("entities/rabbit");

    @Override
    public void initialize() {
        if (VanillaTweaksFabric.config.world.realisticRelationship)
            LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, identifier, supplierBuilder, lootTableSetter) -> {
                addLootCondition(KilledByWolfLootCondition.builder().invert().build(), SHEEP_LOOT_TABLE_ID, lootManager, identifier, lootTableSetter);
                addLootCondition(KilledByFoxLootCondition.builder().or(KilledByOcelotLootCondition.builder()).invert().build(),
                        CHICKEN_LOOT_TABLE_ID, lootManager, identifier, lootTableSetter);
                addLootCondition(KilledByFoxLootCondition.builder().or(KilledByWolfLootCondition.builder()).invert().build(),
                        RABBIT_LOOT_TABLE_ID, lootManager, identifier, lootTableSetter);
            });
    }

    /**
     * Add the given LootCondition to given LootTable ID
     */
    public void addLootCondition(LootItemCondition condition, ResourceLocation idToReplace, LootTables manager, ResourceLocation lootTableId, LootTableSetter setter) {
        if (lootTableId.equals(idToReplace) && manager.get(idToReplace) instanceof FabricLootSupplier supplier) {
            LootContextParamSet contextType = supplier.getType();
            List<LootPool> pools = supplier.getPools();
            List<LootItemFunction> functions = supplier.getFunctions();
            FabricLootSupplierBuilder replacement = FabricLootSupplierBuilder.builder();
            replacement.setParamSet(contextType);
            for (LootPool pool : pools) {
                FabricLootPoolBuilder modifiablePool = FabricLootPoolBuilder.of(pool);
                modifiablePool.withCondition(condition);
                replacement.withPool(modifiablePool.build());
            }
            replacement.withFunctions(functions);
            setter.set(replacement.build());
        }
    }
}