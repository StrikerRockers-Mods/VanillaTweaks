package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ArmorStandSwap extends Feature {

    @Override
    public void initialize() {
        //Swaps all the armor slots of armor stand and player when shift right-clicked
        UseEntityCallback.EVENT.register(((player, world, hand, target, entityHitResult) -> {
            if (player.isShiftKeyDown() && VanillaTweaksFabric.config.tweaks.enableArmorStandSwapping && !world.isClientSide() && !player.isSpectator()
                    && target instanceof ArmorStand armorStand) {
                for (EquipmentSlot equipmentSlotType : EquipmentSlot.values()) {
                    if (equipmentSlotType.getType() == EquipmentSlot.Type.ARMOR) {
                        TweaksImpl.swapSlot(player, armorStand, equipmentSlotType);
                    }
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }));
    }
}