package io.github.strikerrocker.vt.world;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.data.worldgen.Features;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;

public class NoMoreLavaPocketGen extends Feature {
    private ForgeConfigSpec.BooleanValue disableLavaPocketGen;

    /**
     * Serializes the given features and then compares them
     */
    private static boolean serializeAndCompareFeature(ConfiguredFeature<?, ?> configuredFeature1, ConfiguredFeature<?, ?> configuredFeature2) {

        Optional<JsonElement> configuredFeatureJSON1 = ConfiguredFeature.DIRECT_CODEC.encode(configuredFeature1, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().left();
        Optional<JsonElement> configuredFeatureJSON2 = ConfiguredFeature.DIRECT_CODEC.encode(configuredFeature2, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().left();

        // One of the configured features cannot be serialized
        if (configuredFeatureJSON1.isEmpty() || configuredFeatureJSON2.isEmpty()) {
            return false;
        }

        // Compare the JSON to see if it's the same ConfiguredFeature in the end.
        return configuredFeatureJSON1.equals(configuredFeatureJSON2);
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
    public void biomeCreationEvent(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.BiomeCategory.NETHER && disableLavaPocketGen.get()) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION).removeIf(configuredFeatureSupplier -> serializeAndCompareFeature(Features.SPRING_CLOSED, configuredFeatureSupplier.get()) || serializeAndCompareFeature(Features.SPRING_CLOSED_DOUBLE, configuredFeatureSupplier.get()));
        }
    }
}