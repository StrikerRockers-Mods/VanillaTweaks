package io.github.strikerrocker.vt.base;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public abstract class Feature {
    protected Module module;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setup() {
    }

    public void configChanged(ModConfigEvent event) {
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
