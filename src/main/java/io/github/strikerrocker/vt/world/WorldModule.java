package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.base.Module;
import net.minecraftforge.common.ForgeConfigSpec;

public class WorldModule extends Module {
    public WorldModule(ForgeConfigSpec.Builder builder) {
        super("world", "Dynamic changes in the world", builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("realistic_relationship", new RealisticRelationship());
        registerFeature("lava_gen", new NoMoreLavaPocketGen());
        registerFeature("self_planting", new SelfPlanting());
    }
}
