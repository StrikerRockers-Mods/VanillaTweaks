package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.entities.EntityDynamite;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BehaviorDispenseDynamite extends BehaviorProjectileDispense {
    @Override
    protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
        return new EntityDynamite(worldIn, position.getX(), position.getY(), position.getZ());
    }
}
