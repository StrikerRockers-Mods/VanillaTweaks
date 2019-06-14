package io.github.strikerrocker.vt.base;

import net.minecraftforge.common.config.Configuration;

public abstract class Feature {

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
