package io.github.strikerrocker.vt.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;

public interface LivingEntityTickCallback {
    Event<LivingEntityTickCallback> EVENT = EventFactory.createArrayBacked(LivingEntityTickCallback.class,
            listeners -> (livingEntity -> {
                for (LivingEntityTickCallback livingEntityTickCallback : listeners) {
                    livingEntityTickCallback.update(livingEntity);
                }
            })
    );

    void update(LivingEntity livingEntity);
}