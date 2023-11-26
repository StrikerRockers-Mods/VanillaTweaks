package io.github.strikerrocker.vt.base;

import io.github.strikerrocker.vt.VanillaTweaks;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for Module's
 */
public abstract class ForgeModule {
    private final Map<String, ForgeFeature> features = new HashMap<>();
    private final ModConfigSpec.Builder configBuilder;
    private final String name;
    private final String comments;

    protected ForgeModule(String name, String comments, ModConfigSpec.Builder configBuilder) {
        this.name = name;
        this.comments = comments;
        this.configBuilder = configBuilder;
        addFeatures();
    }

    public abstract void addFeatures();

    /**
     * Called when FMLCommonSetupEvent is called
     */
    public void setup(FMLCommonSetupEvent event) {
        features.values().stream().filter(ForgeFeature::usesEvents).forEach(feature -> {
            NeoForge.EVENT_BUS.register(feature);
            VanillaTweaks.LOGGER.debug("Registered Event Handler for class {}", feature.getClass().getName());
        });
        features.values().forEach(feature -> feature.setup(event));
    }

    /**
     * Setups config
     */
    public void setupConfig() {
        configBuilder.comment(comments).push(name);
        features.values().forEach(feature -> feature.setupConfig(configBuilder));
        configBuilder.pop();
    }

    /**
     * Register the given feature
     *
     * @param name    The name of the feature
     * @param feature The feature
     */
    protected void registerFeature(String name, ForgeFeature feature) {
        features.put(name, feature);
    }
}