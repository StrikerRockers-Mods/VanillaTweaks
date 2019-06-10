package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.Module;

public class Loot extends Module {
    public Loot() {
        super("Loot", "Mob Drops", false);
    }

    @Override
    public void addFeatures() {
        registerFeature(new CreeperTNT(this));
        registerFeature(new BatLeather(this));
        registerFeature(new MobNametag(this));
        registerFeature(new RealisticRelationship(this));
    }
}
