package io.github.strikerrocker.vt.base;

import net.minecraftforge.common.ForgeConfigSpec;

public abstract class Feature {
    private String name;
    protected Module module;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setup() {
    }

    public void setupConfig(ForgeConfigSpec.Builder builder) {
    }

    public boolean usesEvents() {
        return false;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
