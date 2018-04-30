package io.github.strikerrocker.vt.events;

import io.github.strikerrocker.vt.enchantments.VTEnchantments;
import io.github.strikerrocker.vt.entities.EntitySitting;
import io.github.strikerrocker.vt.entities.EntityTntImproved;
import io.github.strikerrocker.vt.handlers.ConfigHandler;
import io.github.strikerrocker.vt.misc.VTUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static io.github.strikerrocker.vt.enchantments.VTEnchantments.Vigor;
import static io.github.strikerrocker.vt.handlers.ConfigHandler.drops;

@Mod.EventBusSubscriber
public class EntityEvents {
    /**
     * Enables the Hops enchantment functionality
     *
     * @param event The LivingJumpEvent
     */
    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        VTEnchantments.performAction("hops", event.getEntityLiving(), event);
    }

    /**
     * Enables the Vigor Functionality
     *
     * @param event The LivingEquipmentChangeEvent
     */

    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            UUID vigorUUID = UUID.fromString("18339f34-6ab5-461d-a103-9b9a3ac3eec7");
            int lvl = EnchantmentHelper.getEnchantmentLevel(Vigor, event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.CHEST));
            if (lvl > 0) {
                IAttributeInstance vigorAttribute = event.getEntityLiving().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
                if (vigorAttribute.getModifier(vigorUUID) == null) {
                    AttributeModifier vigorModifier = new AttributeModifier(vigorUUID, "Vigor", (float) lvl / 10, 1);
                    vigorAttribute.applyModifier(vigorModifier);
                }
            } else {
                IAttributeInstance vigorAttribute = event.getEntityLiving().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
                if (vigorAttribute.getModifier(vigorUUID) != null) {
                    AttributeModifier vigorModifier = new AttributeModifier(vigorUUID, "Vigor", (float) lvl / 10, 1);
                    vigorAttribute.removeModifier(vigorModifier);
                    if (event.getEntityLiving().getHealth() > event.getEntityLiving().getMaxHealth())
                        event.getEntityLiving().setHealth(event.getEntityLiving().getMaxHealth());
                }
            }
            if (EnchantmentHelper.getEnchantmentLevel(VTEnchantments.Nimble, event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET)) > 0) {
                VTEnchantments.performAction("nimble", event.getEntityLiving(), event);
            }
        }
    }

    /**
     * Prevents taking extra fall damage from the Hops enchantment
     *
     * @param event The LivingFallEvent
     */
    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        if (EnchantmentHelper.getEnchantmentLevel(VTEnchantments.Hops, event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET)) > 0)
            event.setDistance(event.getDistance() - EnchantmentHelper.getEnchantmentLevel(VTEnchantments.Hops, event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET)));
    }

    /**
     * Affects living entity drops
     *
     * @param event The LivingDropsEvent
     */
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        World world = entity.world;
        //New Drops
        if (!world.isRemote && world.getGameRules().getBoolean("doMobLoot") && event.getSource().getTrueSource() instanceof EntityPlayer && drops.nameTag) {
            //Living entities drop their name tags
            String entityNameTag = entity.getCustomNameTag();
            if (!entityNameTag.equals("")) {
                ItemStack nameTag = new ItemStack(Items.NAME_TAG);
                nameTag.setStackDisplayName(entityNameTag);
                entity.entityDropItem(nameTag, 0);
                entity.setCustomNameTag("");
            }
            if (entity instanceof EntityBat && (drops.batLeatherDropChance / 10) > Math.random())
                entity.dropItem(Items.LEATHER, 1);
            else if (entity instanceof EntityCreeper) {
                if (event.getSource().damageType != null && (drops.creeperDropTntChance / 10) > Math.random()) {
                    event.getDrops().clear();
                    entity.dropItem(Item.getItemFromBlock(Blocks.TNT), 1);
                }
            }
        }
        List<EntityItem> drops = event.getDrops();
        List<EntityItem> dropsCopy = VTUtils.copyList(drops);
        for (EntityItem dropEntity : dropsCopy) {
            ItemStack dropItem = dropEntity.getItem();
            if (event.getSource().getImmediateSource() != null) {
                Item drop = dropItem.getItem();
                Entity source = event.getSource().getImmediateSource();
                if (ConfigHandler.drops.realisticRelationship) {
                    if (source instanceof EntityWolf && entity instanceof EntitySheep) {
                        if ((drop == Items.MUTTON || drop == Items.COOKED_MUTTON))
                            drops.remove(dropEntity);
                    } else if (source instanceof EntityOcelot && entity instanceof EntityChicken) {
                        if ((drop == Items.CHICKEN || drop == Items.COOKED_CHICKEN))
                            drops.remove(dropEntity);
                    }
                }
            }
        }
    }

    /**
     * Makes creepers and baby zombies burn in daylight. <br>
     * Also gives functionality to all ticking enchantments.
     *
     * @param event The LivingUpdateEvent
     */
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase livingEntity = event.getEntityLiving();
        if (!livingEntity.world.isRemote) {
            World world = livingEntity.world;
            if (((livingEntity instanceof EntityCreeper && drops.creeperBurnInDaylight) || (livingEntity instanceof EntityZombie && livingEntity.isChild() && drops.babyZombieBurnInDaylight)) && world.isDaytime()) {
                float f = livingEntity.getBrightness();
                Random random = world.rand;
                BlockPos blockPos = new BlockPos(livingEntity.posX, Math.round(livingEntity.posY), livingEntity.posZ);
                if (f > 0.5 && random.nextFloat() * 30 < (f - 0.4) * 2 && world.canSeeSky(blockPos)) {
                    ItemStack itemstack = livingEntity.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                    boolean doSetFire = true;
                    if (!itemstack.isEmpty()) {
                        doSetFire = true;
                        if (itemstack.isItemStackDamageable()) {
                            itemstack.setItemDamage(itemstack.getItemDamage() + random.nextInt(2));
                            if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
                                livingEntity.renderBrokenItemStack(itemstack);
                                livingEntity.setItemStackToSlot(EntityEquipmentSlot.HEAD, null);
                            }
                        }
                    }
                    if (doSetFire) {
                        livingEntity.setFire(10);
                    }
                }
            }
        }
    }

    /**
     * Replaces Minecraft TNT with ImprovedTNT
     *
     * @param event EntityJoinWorldEvent
     */
    @SubscribeEvent
    public void onEntitySpawn(EntityJoinWorldEvent event) {
        if (event.getEntity().getClass() == EntityTNTPrimed.class) {
            event.setCanceled(true);
            EntityTntImproved tnt = new EntityTntImproved(event.getWorld(), event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ,
                    ((EntityTNTPrimed) event.getEntity()).getTntPlacedBy());
            tnt.setFuse(((EntityTNTPrimed) event.getEntity()).getFuse());
            event.getWorld().spawnEntity(tnt);
        }
    }

    /**
     * Allows the player to dismount from the Entity Sitting
     *
     * @param event The EntityMountEvent
     */
    @SubscribeEvent
    public void onEntityMount(EntityMountEvent event) {
        if (event.isDismounting()) {
            Entity e = event.getEntityBeingMounted();
            if (e instanceof EntitySitting) {
                e.setDead();
                EntitySitting.OCCUPIED.remove(e.getPosition());
            }
        }
    }
}