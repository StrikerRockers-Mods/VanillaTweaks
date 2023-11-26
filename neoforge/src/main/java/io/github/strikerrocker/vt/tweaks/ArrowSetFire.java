package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

public class ArrowSetFire extends ForgeFeature {
    private ModConfigSpec.BooleanValue arrowsSetBlockOnFire;

    @Override
    public void setupConfig(ModConfigSpec.Builder builder) {
        arrowsSetBlockOnFire = builder
                .translation("config.vanillatweaks:arrowsSetBlockOnFire")
                .comment("Want the fire arrows to set fire on block it landed?")
                .define("arrowsSetBlockOnFire", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    /**
     * Sets the block on which an burning arrow lands on
     */
    @SubscribeEvent
    public void onArrowImpact(ProjectileImpactEvent event) {
        if (event.getProjectile() instanceof AbstractArrow arrow && arrowsSetBlockOnFire.get() &&
                !arrow.level().isClientSide() && arrow.isOnFire()) {
            BlockPos pos = arrow.blockPosition();
            Vec3 vec3d = new Vec3(pos.getX(), pos.getY(), pos.getZ()).add(arrow.getDeltaMovement());
            BlockHitResult blockHitResult = arrow.level().clip(new ClipContext(arrow.position(), vec3d, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, arrow));
            BlockPos pos1 = pos.relative(blockHitResult.getDirection());
            if (arrow.level().getBlockState(pos1).isAir()) {
                arrow.level().setBlock(pos1, BaseFireBlock.getState(arrow.level(), pos1), 11);
            }
        }
    }
}