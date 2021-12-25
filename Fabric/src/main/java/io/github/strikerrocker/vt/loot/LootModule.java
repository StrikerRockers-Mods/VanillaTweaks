package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.FabricModule;

public class LootModule extends FabricModule {
    @Override
    public void addFeatures() {
        registerFeature("bat_leather", new BatLeather());
        registerFeature("mob_nametag", new MobNametag());
    }
}