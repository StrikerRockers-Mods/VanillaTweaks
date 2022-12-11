package io.github.strikerrocker.vt.mixins.events;

import io.github.strikerrocker.vt.events.LivingEntityTickCallback;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    /**
     * Fires living entity tick callback
     */
    @Inject(at = @At(value = "INVOKE_STRING",
            target = "Lnet/minecraft/util/profiling/ProfilerFiller;push(Ljava/lang/String;)V",
            args = "ldc=livingEntityBaseTick"
    ), method = "baseTick")
    public void updateLogic(CallbackInfo info) {
        LivingEntityTickCallback.EVENT.invoker().update((LivingEntity) (Object) this);
    }
}