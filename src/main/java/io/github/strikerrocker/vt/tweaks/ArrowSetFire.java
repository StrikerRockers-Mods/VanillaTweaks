package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.Blocks;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ArrowSetFire extends Feature {
    private ForgeConfigSpec.BooleanValue arrowsSetBlockOnFire;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        arrowsSetBlockOnFire = builder
                .translation("config.vanillatweaks:arrowsSetBlockOnFire")
                .comment("Want the fire arrows to set fire on block it landed?")
                .define("arrowsSetBlockOnFire", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onArrowImpact(ProjectileImpactEvent.Arrow event) {
        AbstractArrowEntity arrow = event.getArrow();
        if (!arrow.world.isRemote && arrow.isBurning() && arrowsSetBlockOnFire.get()) {
            Vec3d vec3d1 = new Vec3d(arrow.posX, arrow.posY, arrow.posZ);
            Vec3d vec3d = new Vec3d(arrow.posX + arrow.getMotion().x, arrow.posY + arrow.getMotion().y, arrow.posZ + arrow.getMotion().z);
            RayTraceResult raytraceresult = arrow.world.rayTraceBlocks(new RayTraceContext(vec3d1, vec3d, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, arrow));
            BlockPos hitPos = new BlockPos(raytraceresult.getHitVec()).up();
            if (arrow.world.isAirBlock(hitPos)) {
                arrow.world.setBlockState(hitPos, Blocks.FIRE.getDefaultState());
            }
        }
    }
}
