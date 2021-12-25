package io.github.strikerrocker.vt.misc;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ConeShape {
    private static double max5(double a, double b, double c, double d, double e) {
        return Math.max(Math.max(Math.max(Math.max(a, b), c), d), e);
    }

    private static double min5(double a, double b, double c, double d, double e) {
        return Math.min(Math.min(Math.min(Math.min(a, b), c), d), e);
    }

    public static AABB getConeBoundApprox(LivingEntity livingEntity, int enchantmentLvl) {
        double length = Math.pow(2, (double) enchantmentLvl - 1) * 16;// cone length
        float radius = 2;
        float hyp = (float) Math.sqrt(radius * radius + length * length);

        Vec3 p0 = livingEntity.getEyePosition(1.0F);

        float y = livingEntity.getYRot();
        float p = livingEntity.getXRot();
        float f = (float) Math.toDegrees(Math.atan2(radius, length));

        Vec3 v0 = (getVectorFromRPY(hyp, y, p, 0 - f));
        Vec3 v1 = (getVectorFromRPY(hyp, y, p, 0 + f));
        Vec3 v2 = (getVectorFromRPY(hyp, y, p - f, 0));
        Vec3 v3 = (getVectorFromRPY(hyp, y, p + f, 0));

        Vec3 q0 = p0.add(v0);
        Vec3 q1 = p0.add(v1);
        Vec3 q2 = p0.add(v2);
        Vec3 q3 = p0.add(v3);

        float mx = (float) max5(p0.x, q0.x, q1.x, q2.x, q3.x);
        float nx = (float) min5(p0.x, q0.x, q1.x, q2.x, q3.x);
        float my = (float) max5(p0.y, q0.y, q1.y, q2.y, q3.y);
        float ny = (float) min5(p0.y, q0.y, q1.y, q2.y, q3.y);
        float mz = (float) max5(p0.z, q0.z, q1.z, q2.z, q3.z);
        float nz = (float) min5(p0.z, q0.z, q1.z, q2.z, q3.z);

        return new AABB(mx, my, mz, nx, ny, nz);
    }

    private static Vec3 getVectorFromRPY(double length, double y, double p, double r) {
        y = Math.toRadians(y + 90);
        p = Math.toRadians(180 - p);
        r = Math.toRadians(r + 180);
        double y2 = y + Math.toRadians(90);

        double ll = length * Math.cos(r);
        double ss = length * Math.sin(r);

        double xz = ll * Math.cos(p);
        double yy = ll * Math.sin(p);

        double xl = xz * Math.cos(y);
        double zl = xz * Math.sin(y);
        double xs = ss * Math.cos(y2);
        double zs = ss * Math.sin(y2);

        double xx = xl + xs;
        double zz = zl + zs;

        return new Vec3(xx, yy, zz);
    }
}
