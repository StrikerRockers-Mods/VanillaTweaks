package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.world.selfplanting.CapabilitySelfPlanting;

public class WorldModule extends Module {

    public WorldModule() {
        super("World", "Dynamic changes in the world", false);
    }

    @Override
    public void addFeatures() {
        registerFeature(new RealisticRelationship());
        registerFeature(new NoMoreLavaGen());
        registerFeature(new CapabilitySelfPlanting());
    }
}
