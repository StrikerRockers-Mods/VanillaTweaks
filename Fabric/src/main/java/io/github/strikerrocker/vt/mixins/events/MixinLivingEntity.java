package io.github.strikerrocker.vt.mixins.events;

import io.github.strikerrocker.vt.events.EntityEquipmentChangeCallback;
import io.github.strikerrocker.vt.events.LivingEntityDeathCallback;
import io.github.strikerrocker.vt.events.LivingEntityTickCallback;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

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

    /**
     * Fires living entity death callback
     */
    @Inject(method = "die", at = @At("RETURN"))
    public void onDeath(DamageSource damageSource, CallbackInfo callbackInfo) {
        LivingEntityDeathCallback.EVENT.invoker().onDeath((LivingEntity) (Object) this, damageSource);
    }

    /**
     * Fires living entity change equipment callback
     */
    @Inject(method = "collectEquipmentChanges", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;matches(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z", shift = At.Shift.BY, by = 2),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void entityEquip(CallbackInfoReturnable<Map<EquipmentSlot, ItemStack>> cir, Map map, EquipmentSlot[] var2, int var3, int var4, EquipmentSlot equipmentSlot, ItemStack itemStack3, ItemStack itemStack4) {
        EntityEquipmentChangeCallback.EVENT.invoker().onEntityEquipmentChange((LivingEntity) (Object) this, equipmentSlot, itemStack3, itemStack4);
    }
}