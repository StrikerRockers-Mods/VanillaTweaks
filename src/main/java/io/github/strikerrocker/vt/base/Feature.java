package io.github.strikerrocker.vt.base;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Base class for Feature's
 */
public abstract class Feature {

    /**
     * Called when FMLCommonSetupEvent is called
     */
    public void setup(FMLCommonSetupEvent event) {
    }

    /**
     * Method for creating configs
     */
    public void setupConfig(ForgeConfigSpec.Builder builder) {
    }

    /**
     * Whether or not if the feature uses events.
     * If true the feature is register to Event Bus
     */
    public boolean usesEvents() {
        return false;
    }
}
