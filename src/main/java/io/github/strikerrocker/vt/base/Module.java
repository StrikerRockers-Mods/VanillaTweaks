package io.github.strikerrocker.vt.base;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private static List<Feature> features = new ArrayList<>();
    private String name;
    private boolean requiresRestart;
    private String comments;

    public Module(String name, String comments, boolean requiresRestart) {
        this.name = name;
        this.requiresRestart = requiresRestart;
        this.comments = comments;
        addFeatures();
    }

    public void addFeatures() {

    }

    public void preInit() {
        features.stream().filter(Feature::usesEvents).forEach(MinecraftForge.EVENT_BUS::register);
        features.forEach(Feature::preInit);
    }

    public void init() {
        features.forEach(Feature::init);
    }

    public void postInit() {
        features.forEach(Feature::postInit);
    }

    public void syncConfig(Configuration config) {
        String module = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + name;
        features.forEach(feature -> feature.syncConfig(config, module));
        config.setCategoryComment(module, comments);
        config.setCategoryRequiresMcRestart(module, requiresRestart);
    }

    protected void registerFeature(Feature feature) {
        features.add(feature);
    }
}
