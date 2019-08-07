package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.Blocks;
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
        if (!event.getArrow().world.isRemote && event.getArrow().isBurning() && arrowsSetBlockOnFire.get()) {
            Vec3d vec3d1 = new Vec3d(event.getArrow().posX, event.getArrow().posY, event.getArrow().posZ);
            Vec3d vec3d = new Vec3d(event.getArrow().posX + event.getArrow().getMotion().x, event.getArrow().posY + event.getArrow().getMotion().y, event.getArrow().posZ + event.getArrow().getMotion().z);
            RayTraceResult raytraceresult = event.getArrow().world.rayTraceBlocks(new RayTraceContext(vec3d1, vec3d, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, event.getArrow()));
            BlockPos hitBlock = new BlockPos(raytraceresult.getHitVec()).up();
            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK && event.getArrow().world.isAirBlock(hitBlock)) {
                event.getArrow().world.setBlockState(hitBlock, Blocks.FIRE.getDefaultState(), 11);
            }
        }
    }
}
