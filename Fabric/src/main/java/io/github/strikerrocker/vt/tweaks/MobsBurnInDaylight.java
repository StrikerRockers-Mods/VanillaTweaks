package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.events.LivingEntityTickCallback;

public class MobsBurnInDaylight extends Feature {

    @Override
    public void initialize() {
        LivingEntityTickCallback.EVENT.register(livingEntity -> TweaksImpl.triggerMobsBurnInSun(livingEntity,
                VanillaTweaksFabric.config.tweaks.creeperBurnInDaylight, VanillaTweaksFabric.config.tweaks.babyZombieBurnInDaylight));
    }
}