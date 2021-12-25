package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.FabricModule;
import io.github.strikerrocker.vt.tweaks.silkspawner.SilkSpawner;

public class TweaksModule extends FabricModule {

    @Override
    public void addFeatures() {
        registerFeature("silk_spawner", new SilkSpawner());
        registerFeature("armor_stand_swap", new ArmorStandSwap());
        registerFeature("item_frame_reverse_rotate", new ItemFrameReverse());
        registerFeature("mobs_burn_daylight", new MobsBurnInDaylight());
        registerFeature("shear_nametag", new ShearNameTag());
        registerFeature("sickle", new Sickle());
        registerFeature("sign_editing", new SignEditing());
        registerFeature("tnt_ignition", new TNTIgnition());
    }
}