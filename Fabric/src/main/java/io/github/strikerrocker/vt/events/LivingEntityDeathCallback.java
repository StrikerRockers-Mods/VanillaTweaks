package io.github.strikerrocker.vt.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface LivingEntityDeathCallback {
    Event<LivingEntityDeathCallback> EVENT = EventFactory.createArrayBacked(LivingEntityDeathCallback.class,
            listeners -> ((livingEntity, damageSource) -> {
                for (LivingEntityDeathCallback livingEntityDeathCallback : listeners) {
                    livingEntityDeathCallback.onDeath(livingEntity, damageSource);
                }
            })
    );

    void onDeath(LivingEntity livingEntity, DamageSource damageSource);
}
