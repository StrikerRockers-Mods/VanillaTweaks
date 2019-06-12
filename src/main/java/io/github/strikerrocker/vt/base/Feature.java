package io.github.strikerrocker.vt.base;

import net.minecraftforge.common.config.Configuration;

public abstract class Feature {
    private Module module;

    public Feature(Module module) {
        this.module = module;
    }

    public void preInit() {

    }

    public void init() {

    }

    public void postInit() {

    }

    public abstract void syncConfig(Configuration config, String module);

    public boolean usesEvents() {
        return false;
    }
}
