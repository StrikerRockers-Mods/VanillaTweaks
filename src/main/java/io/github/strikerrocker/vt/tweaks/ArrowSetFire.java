package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ArrowSetFire extends Feature {
    private boolean arrowsSetBlockOnFire;

    @Override
    public void syncConfig(Configuration config, String category) {
        arrowsSetBlockOnFire = config.get(category, "arrowsSetBlockOnFire", true, "Want the fire arrows to set fire on block it landed?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onArrowImpact(ProjectileImpactEvent.Arrow event) {
        if (!event.getArrow().world.isRemote && event.getArrow().isBurning() && arrowsSetBlockOnFire) {
            Vec3d vec3d1 = new Vec3d(event.getArrow().posX, event.getArrow().posY, event.getArrow().posZ);
            Vec3d vec3d = new Vec3d(event.getArrow().posX + event.getArrow().motionX, event.getArrow().posY + event.getArrow().motionY, event.getArrow().posZ + event.getArrow().motionZ);
            RayTraceResult raytraceresult = event.getArrow().world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
            if (raytraceresult != null && raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK && event.getArrow().world.isAirBlock(raytraceresult.getBlockPos().up())) {
                event.getArrow().world.setBlockState(raytraceresult.getBlockPos().up(), Blocks.FIRE.getDefaultState(), 11);
            }
        }
    }
}
