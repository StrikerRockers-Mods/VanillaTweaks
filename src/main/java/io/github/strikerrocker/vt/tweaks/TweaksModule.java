package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.tweaks.silkspawner.SilkSpawner;
import io.github.strikerrocker.vt.tweaks.sit.Sit;
import net.minecraftforge.common.ForgeConfigSpec;

public class TweaksModule extends Module {
    public TweaksModule(ForgeConfigSpec.Builder builder) {
        super("Tweaks", "Tweaks", false, builder);
    }

    @Override
    public void addFeatures() {
        registerFeature(new Sit());
        registerFeature(new SignEditing());
        registerFeature(new SpongeInNether());
        registerFeature(new ArrowSetFire());
        registerFeature(new TNTIgnition());
        registerFeature(new Sickle());
        //TODO registerFeature(new ModifiedCreativeTab());
        registerFeature(new SilkSpawner());
        registerFeature(new ArmorStandSwap());
        registerFeature(new ShearNameTag());
        registerFeature(new MobsBurnInDaylight());
        registerFeature(new ItemFrameReverse());
        registerFeature(new StackSizes());
    }
}