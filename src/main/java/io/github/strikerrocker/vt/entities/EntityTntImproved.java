package io.github.strikerrocker.vt.entities;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityTntImproved extends EntityTNTPrimed
{
    @SuppressWarnings("unused")
    public EntityTntImproved(World worldIn)
    {
        super(worldIn);
    }

    public EntityTntImproved(World worldIn, double x, double y, double z, EntityLivingBase igniter)
    {
        super(worldIn, x, y, z, igniter);

    }

    @Override
    public String getName()
    {
        if (this.hasCustomName())
        {
            return this.getCustomNameTag();
        } else
        {
            return I18n.translateToLocal("entity.PrimedTnt.name");
        }
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn)
    {
        return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        } else
        {
            Entity entity = source.getImmediateSource();

            if (entity != null)
            {
                double d1 = entity.posX - this.posX;
                double d0;

                for (d0 = entity.posZ - this.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
                {
                    d1 = (Math.random() - Math.random()) * 0.01D;
                }

                if (entity instanceof EntityLivingBase && !source.isProjectile())
                {
                    int i = EnchantmentHelper.getKnockbackModifier((EntityLivingBase) entity) * 2 + (entity.isSprinting() ? 1 : 0);
                    d1 = (double) MathHelper.sin(entity.rotationYaw * 0.017453292F);
                    d0 = (double) (-MathHelper.cos(entity.rotationYaw * 0.017453292F));
                    this.knockBack(entity, i * 0.033F + 0.1F, d1, d0);
                } else
                {
                    this.knockBack(entity, 0.4F, d1, d0);
                }

                this.markVelocityChanged();
            }

            return true;
        }
    }

    public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio)
    {
        this.isAirBorne = true;
        float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
        this.motionX /= 2.0D;
        this.motionZ /= 2.0D;
        this.motionX -= xRatio / (double) f * (double) strength;
        this.motionZ -= zRatio / (double) f * (double) strength;

        if (this.onGround)
        {
            this.motionY /= 2.0D;
            this.motionY += (double) strength;

            if (this.motionY > 0.4000000059604645D)
            {
                this.motionY = 0.4000000059604645D;
            }
        }
    }

}
