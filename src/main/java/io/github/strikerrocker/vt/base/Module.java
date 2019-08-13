package io.github.strikerrocker.vt.base;

import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    private ForgeConfigSpec.Builder configBuilder;
    private List<Feature> features = new ArrayList<>();
    private String name;

    public Module(String name, String comments, boolean requiresMCRestart, ForgeConfigSpec.Builder configBuilder) {
        this.name = name;
        this.configBuilder = configBuilder;
        addFeatures();
    }

    public abstract void addFeatures();

    public void setup() {
        features.stream().filter(Feature::usesEvents).forEach(MinecraftForge.EVENT_BUS::register);
        features.stream().filter(Feature::usesEvents).forEach(feature -> VanillaTweaks.LOGGER.debug("Registered Event Handler class" + feature.getClass().getName()));
        features.forEach(Feature::setup);
    }

    public void setupConfig() {
        features.forEach(feature -> feature.setupConfig(configBuilder));
    }

    protected void registerFeature(Feature feature) {
        features.add(feature);
    }

    public String getName() {
        return name;
    }
}