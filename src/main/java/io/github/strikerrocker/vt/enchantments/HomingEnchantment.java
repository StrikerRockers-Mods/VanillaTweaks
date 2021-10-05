package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.misc.ConeShape;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;

public class HomingEnchantment extends Enchantment {

    HomingEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.BOW, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName("homing");
    }

    /**
     * Returns the target entity for homing enchantment
     */
    @Nullable
    private static LivingEntity getTarget(World world, LivingEntity shooter, int homingLevel) {
        LivingEntity target = null;
        AxisAlignedBB coneBound = ConeShape.getConeBoundApprox(shooter, homingLevel);
        List<Entity> livingEntities = world.getEntities((EntityType<Entity>) null,
                coneBound,
                (entity -> !entity.getUUID().equals(shooter.getUUID())));
        for (Entity entity : livingEntities) {
            if (entity instanceof LivingEntity && shooter.canSee(entity)) {
                if (entity instanceof TameableEntity && ((TameableEntity) entity).getOwnerUUID() == shooter.getUUID())
                    continue;
                target = (LivingEntity) entity;
            }
        }
        VanillaTweaks.LOGGER.debug(coneBound);
        VanillaTweaks.LOGGER.debug(target);
        return target;
    }

    /**
     * Retargets the arrow towards the targeted entity
     */
    @SubscribeEvent
    public void entityJoin(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide() && EnchantmentFeature.enableHoming.get() && event.getEntity() instanceof AbstractArrowEntity) {
            AbstractArrowEntity arrow = (AbstractArrowEntity) event.getEntity();
            LivingEntity shooter = (LivingEntity) arrow.getOwner();
            if (shooter != null && EnchantmentHelper.getItemEnchantmentLevel(this, shooter.getMainHandItem()) > 0) {
                int homingLevel = EnchantmentHelper.getItemEnchantmentLevel(this, shooter.getMainHandItem());
                LivingEntity target = getTarget(event.getWorld(), shooter, homingLevel);
                if (target != null) {
                    double x = target.getX() - arrow.getX();
                    double y = target.getEyeY() - arrow.getY();
                    double z = target.getZ() - arrow.getZ();
                    arrow.setNoGravity(true);
                    arrow.shoot(x, y, z, (float) arrow.getDeltaMovement().length(), 0);
                }
            }
        }
    }

    /**
     * Adds the glowing effect to the targeting entity when using the bow
     */
    @SubscribeEvent
    public void useItem(LivingEntityUseItemEvent event) {
        if (!event.getEntityLiving().getCommandSenderWorld().isClientSide()) {
            LivingEntity player = event.getEntityLiving();
            int homingLvl = EnchantmentHelper.getItemEnchantmentLevel(this, event.getItem());
            if (homingLvl > 0) {
                LivingEntity target = getTarget(event.getEntityLiving().getCommandSenderWorld(), player, homingLvl);
                if (target != null)
                    target.addEffect(new EffectInstance(Effects.GLOWING, 4, 1, true, false, false));
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
