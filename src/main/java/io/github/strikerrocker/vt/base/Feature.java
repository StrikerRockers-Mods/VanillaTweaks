package io.github.strikerrocker.vt.base;

import net.minecraftforge.common.ForgeConfigSpec;

public abstract class Feature {

    public void setup() {

    }

    public void setupConfig(ForgeConfigSpec.Builder builder) {

    }

    public boolean usesEvents() {
        return false;
    }
}
