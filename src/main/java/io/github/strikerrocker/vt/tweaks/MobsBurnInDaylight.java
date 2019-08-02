package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class MobsBurnInDaylight extends Feature {
    private boolean babyZombieBurnInDaylight;
    private boolean creeperBurnInDaylight;

    @Override
    public void syncConfig(Configuration config, String category) {
        babyZombieBurnInDaylight = config.get(category, "babyZombieBurnInDaylight", true, "Want baby zombies to burn by the light of the day?").getBoolean();
        creeperBurnInDaylight = config.get(category, "creeperBurnInDaylight", true, "Want creeper's to burn by daylight").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase livingEntity = event.getEntityLiving();
        if (!livingEntity.world.isRemote) {
            World world = livingEntity.world;
            if (((livingEntity instanceof EntityCreeper && creeperBurnInDaylight) || (livingEntity instanceof EntityZombie && livingEntity.isChild() &&
                    babyZombieBurnInDaylight)) && world.isDaytime()) {
                float brightness = livingEntity.getBrightness();
                Random random = world.rand;
                BlockPos blockPos = new BlockPos(livingEntity.posX, Math.round(livingEntity.posY), livingEntity.posZ);
                if (brightness > 0.5 && random.nextFloat() * 30 < (brightness - 0.4) * 2 && world.canSeeSky(blockPos)) {
                    ItemStack itemstack = livingEntity.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                    boolean setFire = true;
                    if (!itemstack.isEmpty()) {
                        setFire = true;
                        if (itemstack.isItemStackDamageable()) {
                            itemstack.setItemDamage(itemstack.getItemDamage() + random.nextInt(2));
                            if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
                                livingEntity.renderBrokenItemStack(itemstack);
                                livingEntity.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
                            }
                        }
                    }
                    if (setFire) {
                        livingEntity.setFire(10);
                    }
                }
            }
        }
    }
}
