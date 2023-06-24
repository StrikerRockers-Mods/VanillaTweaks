package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.ForgeModule;
import net.minecraftforge.common.ForgeConfigSpec;

public class LootModule extends ForgeModule {
    public LootModule(ForgeConfigSpec.Builder builder) {
        super("loot", "Mob Drops", builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("bat_leather", new BatLeather());
        registerFeature("mob_nametag", new MobNametag());
    }
}