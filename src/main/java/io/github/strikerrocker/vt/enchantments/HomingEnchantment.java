package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.misc.ConeShape;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class HomingEnchantment extends Enchantment {
    private final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

    HomingEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.BOW, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName("homing");
    }

    @SubscribeEvent
    public void entityJoin(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide() && EnchantmentFeature.enableHoming.get() && event.getEntity() instanceof AbstractArrowEntity) {
            onArrowJoinWorld((AbstractArrowEntity) event.getEntity(), (ServerWorld) event.getWorld());
        }
    }

    private void onArrowJoinWorld(AbstractArrowEntity arrow, ServerWorld world) {
        LivingEntity shooter = (LivingEntity) arrow.getOwner();
        if (shooter != null && EnchantmentHelper.getItemEnchantmentLevel(this, shooter.getMainHandItem()) > 0) {
            int homingLevel = EnchantmentHelper.getItemEnchantmentLevel(this, shooter.getMainHandItem());
            LivingEntity target = null;
            AxisAlignedBB coneBound = ConeShape.getConeBounds(shooter, homingLevel);
            List<Entity> livingEntities = world.getEntities((EntityType<Entity>) null,
                    coneBound,
                    (entity -> !entity.getUUID().equals(shooter.getUUID())));
            System.out.println(coneBound);
            for (Entity entity : livingEntities) {
                if (entity instanceof LivingEntity && shooter.canSee(entity)) {
                    target = (LivingEntity) entity;
                }
            }
            System.out.println(target);
            if (target != null) {
                double x = target.getX() - arrow.getX();
                double y = target.getEyeY() - arrow.getY();
                double z = target.getZ() - arrow.getZ();
                arrow.shoot(x, y, z, (float) arrow.getDeltaMovement().length(), 0);
            }
        }
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return (enchantmentLevel - 1) * 10 + 10;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return enchantmentLevel * 10 + 51;
    }

    @Override
    public int getMaxLevel() {
        return EnchantmentFeature.enableHoming.get() ? 3 : 0;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof BowItem && EnchantmentFeature.enableHoming.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return EnchantmentFeature.enableHoming.get();
    }
}
