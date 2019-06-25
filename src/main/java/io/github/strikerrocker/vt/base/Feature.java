package io.github.strikerrocker.vt.base;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class Feature {

    public void preInit() {

    }

    public void init() {

    }

    public void postInit() {

    }

    public abstract void syncConfig(Configuration config, String category);

    public boolean usesEvents() {
        return false;
    }

    public void registerPacket(SimpleNetworkWrapper network) {
    }
}
