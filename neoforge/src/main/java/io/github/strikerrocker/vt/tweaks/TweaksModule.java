package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.ForgeModule;
import net.neoforged.neoforge.common.ModConfigSpec;

public class TweaksModule extends ForgeModule {
    public TweaksModule(ModConfigSpec.Builder builder) {
        super("tweaks", "Tweaks", builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("arrow_set_fire", new ArrowSetFire());
        registerFeature("tnt_ignition", new TNTIgnition());
        registerFeature("sickle", new Sickle());
        registerFeature("silk_spawner", new SilkSpawner());
        registerFeature("armor_stand_swap", new ArmorStandSwap());
        registerFeature("shear_nametag", new ShearNameTag());
        registerFeature("mobs_burn_daylight", new MobsBurnInDaylight());
        registerFeature("item_frame_reverse_rotate", new ItemFrameReverse());
        registerFeature("beehive_tooltips", new BeehiveTooltips());
    }
}