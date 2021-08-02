package io.github.strikerrocker.vt.base;

import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for Module's
 */
public abstract class Module {
    private final ForgeConfigSpec.Builder configBuilder;
    private final Map<String, Feature> features = new HashMap<>();
    private final String name;
    private final String comments;

    public Module(String name, String comments, ForgeConfigSpec.Builder configBuilder) {
        this.name = name;
        this.comments = comments;
        this.configBuilder = configBuilder;
        addFeatures();
    }

    /**
     * Method to register the features
     */
    public abstract void addFeatures();

    /**
     * Called when FMLCommonSetupEvent is called
     */
    public void setup(FMLCommonSetupEvent event) {
        features.values().stream().filter(Feature::usesEvents).forEach(feature -> {
            MinecraftForge.EVENT_BUS.register(feature);
            VanillaTweaks.LOGGER.debug("Registered Event Handler for class " + feature.getClass().getName());
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
    protected void registerFeature(String name, Feature feature) {
        features.put(name, feature);
    }

    /**
     * Returns feature from name
     */
    public Feature getFeature(String name) {
        return features.get(name);
    }
}