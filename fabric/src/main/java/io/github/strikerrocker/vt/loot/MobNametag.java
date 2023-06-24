package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

public class MobNametag extends Feature {


    @Override
    public void initialize() {
        ServerLivingEntityEvents.AFTER_DEATH.register((livingEntity, damageSource) -> {
            LootImpl.triggerMobNameTagDrop(livingEntity, damageSource.getEntity(), VanillaTweaksFabric.config.loot.namedMobsDropNameTag);
        });
    }
}