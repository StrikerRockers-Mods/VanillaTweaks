package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.events.LivingEntityDeathCallback;

public class MobNametag extends Feature {


    @Override
    public void initialize() {
        LivingEntityDeathCallback.EVENT.register((livingEntity, damageSource) -> {
            LootImpl.triggerMobNameTagDrop(livingEntity, damageSource.getEntity(), VanillaTweaksFabric.config.loot.namedMobsDropNameTag);
        });
    }
}