package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.tweaks.sit.Sit;

public class TweaksModule extends Module {
    public TweaksModule() {
        super("Tweaks", "Tweaks", false);
    }

    @Override
    public void addFeatures() {
        registerFeature(new Sit());
        registerFeature(new SignEditing());
        registerFeature(new SpongeInNether());
        registerFeature(new ArrowSetFire());
        registerFeature(new TNTIgnition());
        registerFeature(new Sickle());
        registerFeature(new ModifiedCreativeTab());
    }
}
