package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.base.ForgeModule;
import net.neoforged.neoforge.common.ModConfigSpec;

public class WorldModule extends ForgeModule {
    public WorldModule(ModConfigSpec.Builder builder) {
        super("world", "Dynamic changes in the world", builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("realistic_relationship", new RealisticRelationship());
        registerFeature("lava_gen", new NoMoreLavaPocketGen());
        registerFeature("self_planting", new SelfPlanting());
    }
}