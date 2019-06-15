package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.Module;

public class LootModule extends Module {
    public LootModule() {
        super("Loot", "Mob Drops", false);
    }

    @Override
    public void addFeatures() {
        registerFeature(new CreeperTNT());
        registerFeature(new BatLeather());
        registerFeature(new MobNametag());
    }
}
