package io.github.strikerrocker.vt.mixins.enchantments;

import io.github.strikerrocker.vt.enchantments.EnchantmentImpl;
import io.github.strikerrocker.vt.enchantments.EnchantmentModule;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class MixinItem {
    //Adds glowing effect to the targeted entity.
    @Inject(method = "onUseTick", at = @At("HEAD"))
    public void usageTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks, CallbackInfo ci) {
        if (!world.isClientSide()) {
            int lvl = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentModule.HOMING, stack);
            if (lvl > 0) {
                LivingEntity target = EnchantmentImpl.getHomingTarget(world, user, lvl);
                if (target != null) {
                    target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 4, 1, true, false, false));
                }
            }
        }
    }
}
