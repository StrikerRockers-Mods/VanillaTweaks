package io.github.strikerrocker.vt.world;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;

public class NoMoreLavaPocketGen extends Feature {
    private ForgeConfigSpec.BooleanValue disableLavaPocketGen;

    private static boolean serializeAndCompareFeature(ConfiguredFeature<?, ?> configuredFeature1, ConfiguredFeature<?, ?> configuredFeature2) {

        Optional<JsonElement> configuredFeatureJSON1 = ConfiguredFeature.DIRECT_CODEC.encode(configuredFeature1, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().left();
        Optional<JsonElement> configuredFeatureJSON2 = ConfiguredFeature.DIRECT_CODEC.encode(configuredFeature2, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().left();

        // One of the configuredfeatures cannot be serialized
        if (!configuredFeatureJSON1.isPresent() || !configuredFeatureJSON2.isPresent()) {
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

    @SubscribeEvent
    public void biomeCreationEvent(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.NETHER && disableLavaPocketGen.get()) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION).removeIf(configuredFeatureSupplier -> serializeAndCompareFeature(Features.SPRING_CLOSED, configuredFeatureSupplier.get()) || serializeAndCompareFeature(Features.SPRING_CLOSED_DOUBLE, configuredFeatureSupplier.get()));
        }
    }
}