package io.github.strikerrocker.vt.mixins.tweaks;

import io.github.strikerrocker.vt.VTServices;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * AutoPlant Functionality
 */
@Mixin(ItemEntity.class)
public abstract class MixinItemEntity extends Entity {

    /**
     * Counter to check and perform self planting logic only every 2 seconds
     */
    int lastChecked = 0;

    public MixinItemEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getItem();

    /**
     * Handles the planting logic
     */
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V"))
    public void tick(CallbackInfo callbackInfo) {
        if (!level.isClientSide() && getItem().getItem() instanceof BlockItem blockItem
                && blockItem.getBlock() instanceof BushBlock plantBlock && VTServices.SERVICES.isSelfPlantingEnabled()
                && tickCount > 20 && !(plantBlock instanceof DoublePlantBlock)) {
            if (lastChecked > VTServices.SERVICES.getSelfPlantingInterval()) {
                lastChecked = 0;
                if (!VTServices.SERVICES.selfPlantingBlackList().contains(Registry.ITEM.getKey(blockItem).toString())) {
                    if (VTServices.SERVICES.place((ItemEntity) (Object) this, level)) getItem().shrink(1);
                }
            } else {
                lastChecked++;
            }
        }
    }
}