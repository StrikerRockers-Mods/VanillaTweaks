package io.github.strikerrocker.vt.mixins.tweaks;

import io.github.strikerrocker.vt.VTServices;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
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
        ItemStack stack = this.getItem();
        if (!level().isClientSide() && stack.getItem() instanceof BlockItem blockItem
                && blockItem.getBlock() instanceof BushBlock plantBlock
                && VTServices.SERVICES.isSelfPlantingEnabled() && tickCount > 20) {
            if (lastChecked > VTServices.SERVICES.getSelfPlantingInterval()) {
                lastChecked = 0;
                if (!VTServices.SERVICES.selfPlantingBlackList().contains(BuiltInRegistries.ITEM.getKey(blockItem).toString()) && !(plantBlock instanceof DoublePlantBlock)) {
                    if (level() instanceof ServerLevel serverLevel) {
                        Player fakePlayer = VTServices.SERVICES.getFakePlayer(serverLevel);
                        Vec3 entityVec = new Vec3(getX(), getY(), getZ());
                        BlockHitResult rayTraceResult = level().clip(
                                new ClipContext(entityVec.add(0, 2, 0), entityVec.add(0, -1, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
                        InteractionResult result = getItem().getItem().useOn(new UseOnContext(fakePlayer, InteractionHand.MAIN_HAND, rayTraceResult));
                        if (result == InteractionResult.SUCCESS || result == InteractionResult.CONSUME)
                            stack.shrink(1);
                    }
                }
            } else {
                lastChecked++;
            }
        }
    }
}