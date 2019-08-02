package io.github.strikerrocker.vt.base;

import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    private List<Feature> features = new ArrayList<>();
    private String name;
    private boolean requiresMCRestart;
    private String comments;

    public Module(String name, String comments, boolean requiresMCRestart) {
        this.name = name;
        this.requiresMCRestart = requiresMCRestart;
        this.comments = comments;
        addFeatures();
    }

    public abstract void addFeatures();

    public void setup() {
        features.stream().filter(Feature::usesEvents).forEach(MinecraftForge.EVENT_BUS::register);
        features.stream().filter(Feature::usesEvents).forEach(feature -> VanillaTweaks.logger.debug("Registered Event Handler class" + feature.getClass().getName()));
        features.forEach(Feature::setup);
    }

    public void postInit() {
        features.forEach(Feature::postInit);
    }

    protected void registerFeature(Feature feature) {
        features.add(feature);
    }
}