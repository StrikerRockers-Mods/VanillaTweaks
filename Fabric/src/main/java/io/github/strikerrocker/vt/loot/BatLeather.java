package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

public class BatLeather extends Feature {

    @Override
    public void initialize() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> LootImpl.triggerBatLeatherDrop(entity, damageSource.getEntity(), VanillaTweaksFabric.config.loot.batLeatherDropChance));
    }
}