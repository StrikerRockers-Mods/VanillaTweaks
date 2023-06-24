package io.github.strikerrocker.vt.base;

import java.util.HashMap;
import java.util.Map;

public abstract class FabricModule {

    private final Map<String, Feature> features = new HashMap<>();

    protected FabricModule() {
        addFeatures();
    }

    public abstract void addFeatures();

    /**
     * Initializes the module
     */
    public void initialize() {
        features.values().forEach(Feature::initialize);
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
}