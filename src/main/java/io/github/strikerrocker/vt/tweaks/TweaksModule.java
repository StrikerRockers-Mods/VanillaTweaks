package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.tweaks.silkspawner.SilkSpawner;
import net.minecraftforge.common.ForgeConfigSpec;

public class TweaksModule extends Module {
    public TweaksModule(ForgeConfigSpec.Builder builder) {
        super("Tweaks", "Tweaks", false, builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("sign_editing", new SignEditing());
        registerFeature("arrow_set_fire", new ArrowSetFire());
        registerFeature("tnt_ignition", new TNTIgnition());
        registerFeature("sickle", new Sickle());
        registerFeature("modified_item_group", new ModifiedItemGroups());
        registerFeature("silk_spawner", new SilkSpawner());
        registerFeature("armor_stand_swap", new ArmorStandSwap());
        registerFeature("shear_nametag", new ShearNameTag());
        registerFeature("mobs_burn_daylight", new MobsBurnInDaylight());
        registerFeature("item_frame_reverse_rotate", new ItemFrameReverse());
        registerFeature("squishy_sponges", new SquishySponges());
        registerFeature("disable_potion_shift", new NoPotionShift());
    }
}