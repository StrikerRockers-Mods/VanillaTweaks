package io.github.strikerrocker.vt.base;

import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.config.ModConfig;

import java.util.HashMap;
import java.util.Map;

public abstract class Module {
    private final ForgeConfigSpec.Builder configBuilder;
    private final Map<String, Feature> features = new HashMap<>();
    private final String name;
    private final String comments;
    private ForgeConfigSpec forgeConfigSpec;

    public Module(String name, String comments, boolean requiresMCRestart, ForgeConfigSpec.Builder configBuilder) {
        this.name = name;
        this.comments = comments;
        this.configBuilder = configBuilder;
        addFeatures();
        setupConfig();
    }

    public ForgeConfigSpec getConfigSpec() {
        return forgeConfigSpec;
    }

    public void setConfigSpec(ForgeConfigSpec forgeConfigSpec) {
        this.forgeConfigSpec = forgeConfigSpec;
    }

    public abstract void addFeatures();

    public void setup() {
        features.values().stream().filter(Feature::usesEvents).forEach(feature -> {
            MinecraftForge.EVENT_BUS.register(feature);
            VanillaTweaks.LOGGER.debug("Registered Event Handler for class " + feature.getClass().getName());
        });
        features.values().forEach(Feature::setup);
    }

    public void configChanged(ModConfig.ModConfigEvent event) {
        features.values().forEach(feature -> feature.configChanged(event));
    }

    public void setupConfig() {
        configBuilder.comment(comments).push(name);
        features.values().forEach(feature -> feature.setupConfig(configBuilder));
        configBuilder.pop();
    }

    protected void registerFeature(String name, Feature feature) {
        features.put(name, feature);
        feature.setName(name);
        feature.setModule(this);
    }

    public Feature getFeature(String name) {
        return features.get(name);
    }

    public String getName() {
        return name;
    }
}