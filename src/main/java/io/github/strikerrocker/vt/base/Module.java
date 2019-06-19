package io.github.strikerrocker.vt.base;

import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

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

    public void preInit() {
        features.stream().filter(Feature::usesEvents).forEach(MinecraftForge.EVENT_BUS::register);
        features.stream().filter(Feature::usesEvents).forEach(feature -> VanillaTweaks.logger.debug("Registered Event Handler class" + feature.getClass().getName()));
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
        config.setCategoryRequiresMcRestart(module, requiresMCRestart);
    }

    protected void registerFeature(Feature feature) {
        features.add(feature);
    }

    public void registerPacket(SimpleNetworkWrapper network) {
        features.forEach(feature -> feature.registerPacket(network));
    }
}
