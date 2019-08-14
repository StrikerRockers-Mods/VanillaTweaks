package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.Module;
import net.minecraftforge.common.ForgeConfigSpec;

public class LootModule extends Module {
    public LootModule(ForgeConfigSpec.Builder builder) {
        super("Loot", "Mob Drops", false, builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("creeper_tnt", new CreeperTNT());
        registerFeature("bat_leather", new BatLeather());
        registerFeature("mob_nametag", new MobNametag());
    }
}
