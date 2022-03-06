package io.github.strikerrocker.vt.world;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;

public class NoMoreLavaPocketGen extends ForgeFeature {
    private ForgeConfigSpec.BooleanValue disableLavaPocketGen;

    /**
     * Serializes the given features and then compares them
     */
    private static boolean compareFeature(PlacedFeature placedFeature1, PlacedFeature placedFeature2) {
        Optional<JsonElement> placedFeatureJSON1 = PlacedFeature.DIRECT_CODEC.encode(placedFeature1, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().left();
        Optional<JsonElement> placedFeatureJSON2 = PlacedFeature.DIRECT_CODEC.encode(placedFeature2, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().left();
        // One of the configured features cannot be serialized
        if (placedFeatureJSON1.isEmpty() || placedFeatureJSON2.isEmpty()) {
            return false;
        }

        // Compare the JSON to see if it's the same ConfiguredFeature in the end.
        return placedFeatureJSON1.equals(placedFeatureJSON2);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        disableLavaPocketGen = builder
                .translation("config.vanillatweaks:disableLavaPocketGen")
                .comment("Don't want that that pesky lava pockets in nether to kill you?")
                .define("disableLavaPocketGen", true);
    }

    /**
     * Remove lava pocket feature if enabled
     */
    @SubscribeEvent
    public void biomesLoadingEvent(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.BiomeCategory.NETHER && disableLavaPocketGen.get()) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION).removeIf(featureSupplier -> {
                PlacedFeature feature = featureSupplier.value();
                return compareFeature(NetherPlacements.SPRING_CLOSED.value(), feature) || compareFeature(NetherPlacements.SPRING_CLOSED_DOUBLE.value(), feature) || compareFeature(NetherPlacements.SPRING_OPEN.value(), feature);
            });
        }
    }
}