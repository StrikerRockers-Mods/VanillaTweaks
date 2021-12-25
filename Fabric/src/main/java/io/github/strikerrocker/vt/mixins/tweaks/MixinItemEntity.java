package io.github.strikerrocker.vt.mixins.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
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
        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof BushBlock plantBlock && VanillaTweaksFabric.config.world.selfPlanting && tickCount > 20) {
            if (!(plantBlock instanceof DoublePlantBlock)) {
                if (lastChecked > 40) {
                    lastChecked = 0;
                    BlockPos pos = blockPosition();
                    if (level.getBlockState(pos).getBlock() instanceof FarmBlock)
                        pos = pos.relative(Direction.UP);
                    BlockState state = level.getBlockState(pos);
                    if (plantBlock.canSurvive(state, level, pos) && state.getBlock() instanceof AirBlock) {
                        level.setBlockAndUpdate(pos, ((BlockItem) stack.getItem()).getBlock().defaultBlockState());
                        stack.shrink(1);
                    }
                } else {
                    lastChecked++;
                }
            }
        }
    }
}