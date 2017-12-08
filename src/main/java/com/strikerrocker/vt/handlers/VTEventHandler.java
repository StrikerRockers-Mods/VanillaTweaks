package com.strikerrocker.vt.handlers;

import com.strikerrocker.vt.VTUtils;
import com.strikerrocker.vt.enchantments.VTEnchantments;
import com.strikerrocker.vt.items.VTItems;
import com.strikerrocker.vt.vtModInfo;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.strikerrocker.vt.enchantments.VTEnchantments.*;
import static com.strikerrocker.vt.handlers.VTConfigHandler.clearSigns;
import static com.strikerrocker.vt.handlers.VTConfigHandler.editSigns;

/**
 * The event handler for Vanilla Tweaks
 */
@SuppressWarnings({"unused", "unchecked"})
public final class VTEventHandler {
    //There is no need to compile the pattern everytime we want to use it, compile it once and reuse it
    private static final Pattern pattern = Pattern.compile("(?i)" + '\u00a7' + "[0-9A-FK-OR]");
    /**
     * The singleton instance of the event handler
     */
    public static VTEventHandler instance = new VTEventHandler();
    /**
     * Whether or not to enable the potion effect overlay
     */
    private static boolean displayPotionEffects = true;

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
        if (!world.isRemote && world.getGameRules().getBoolean("doMobLoot")) {
            //Living entities drop their name tags
            String entityNameTag = entity.getCustomNameTag();
            if (!entityNameTag.equals("")) {
                ItemStack nameTag = new ItemStack(Items.NAME_TAG);
                nameTag.setStackDisplayName(entityNameTag);
                entity.entityDropItem(nameTag, 0);
                entity.setCustomNameTag("");
            }
            //Bats drop leather
            if (entity instanceof EntityBat && VTConfigHandler.batLeatherDropChance > Math.random())
                entity.dropItem(Items.LEATHER, 1);
            else if (entity instanceof EntityCreeper) {
                if (event.getSource().damageType != null && VTConfigHandler.creeperDropTntChance > Math.random()) {
                    event.getDrops().clear();
                    //noinspection ConstantConditions
                    entity.dropItem(Item.getItemFromBlock(Blocks.TNT), 1);
                }
            }
        }
        //Drop removals
        List<EntityItem> drops = event.getDrops();
        List<EntityItem> dropsCopy = VTUtils.copyList(drops);
        for (EntityItem dropEntity : dropsCopy) {
            ItemStack dropItem = dropEntity.getItem();
            if (event.getSource().getImmediateSource() != null) {
                Item drop = dropItem.getItem();
                Entity source = event.getSource().getImmediateSource();
                if (source instanceof EntityWolf && entity instanceof EntitySheep) {
                    if (drop == Items.MUTTON || drop == Items.COOKED_MUTTON)
                        drops.remove(dropEntity);
                } else if (source instanceof EntityOcelot && entity instanceof EntityChicken) {
                    if (drop == Items.CHICKEN || drop == Items.COOKED_CHICKEN)
                        drops.remove(dropEntity);
                }
            }
        }
    }

    /**
     * Strips the input text of its formatting codes
     *
     * @param text The text
     * @return The text without its formatting codes
     */
    private String getTextWithoutFormattingCodes(String text) {
        return pattern.matcher(text).replaceAll("");
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
            if (((livingEntity instanceof EntityCreeper && VTConfigHandler.creeperBurnInDaylight) || (livingEntity instanceof EntityZombie && livingEntity.isChild() && VTConfigHandler.babyZombieBurnInDaylight)) && world.isDaytime()) {
                float f = livingEntity.getBrightness();
                Random random = world.rand;
                BlockPos blockPos = new BlockPos(livingEntity.posX, Math.round(livingEntity.posY), livingEntity.posZ);
                if (f > 0.5 && random.nextFloat() * 30 < (f - 0.4) * 2 && world.canSeeSky(blockPos)) {
                    ItemStack itemstack = livingEntity.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                    boolean doSetFire = true;
                    if (itemstack != null) {
                        doSetFire = true;
                        if (itemstack.isItemStackDamageable()) {
                            itemstack.setItemDamage(itemstack.getItemDamage() + random.nextInt(2));
                            if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
                                livingEntity.renderBrokenItemStack(itemstack);
                                livingEntity.setItemStackToSlot(EntityEquipmentSlot.HEAD, null);
                            }
                        }
                    }
                    if (doSetFire)
                        livingEntity.setFire(10);
                }
            }
        }
    }

    /**
     * Allows a player to shear name tags off living entities
     *
     * @param event The EntityInteractEvent
     */
    @SubscribeEvent
    public void entityRightclick(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntityPlayer().getHeldItemMainhand() != null) {
            EntityPlayer player = event.getEntityPlayer();
            ItemStack heldItem = player.getHeldItemMainhand();
            World world = player.world;
            Entity target = event.getTarget();
            if (heldItem != null && heldItem.getItem() instanceof ItemShears && target instanceof EntityLivingBase && target.hasCustomName() && !world.isRemote) {
                target.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1, 1);
                ItemStack nameTag = new ItemStack(Items.NAME_TAG).setStackDisplayName(target.getCustomNameTag());
                target.entityDropItem(nameTag, 0);
                target.setCustomNameTag("");
                heldItem.damageItem(1, player);
            }
        }
        if (event.getEntityPlayer().getHeldItemOffhand() != null) {
            EntityPlayer player = event.getEntityPlayer();
            ItemStack heldItem = player.getHeldItemOffhand();
            World world = player.world;
            Entity target = event.getTarget();
            if (heldItem != null && heldItem.getItem() instanceof ItemShears && target instanceof EntityLivingBase && target.hasCustomName() && !world.isRemote) {
                target.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1, 1);
                ItemStack nameTag = new ItemStack(Items.NAME_TAG).setStackDisplayName(target.getCustomNameTag());
                target.entityDropItem(nameTag, 0);
                target.setCustomNameTag("");
                heldItem.damageItem(1, player);
            }
        }
    }


    /**
     * Adds tooltips for monster spawners
     *
     * @param event The ItemTooltipEvent
     */
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Block block = Block.getBlockFromItem(stack.getItem());
        if (block == Blocks.MOB_SPAWNER) {
            NBTTagCompound stackTagCompound = stack.getTagCompound();
            if (stackTagCompound != null) {
                NBTTagCompound blockEntityTagCompound = stackTagCompound.getCompoundTag("BlockEntityTag");
                String entityName = blockEntityTagCompound.getCompoundTag("SpawnData").getString("id");
                Class entityClass = EntityList.getEntityNameList().getClass();
                if (entityClass != null) {
                    TextFormatting color = IMob.class.isAssignableFrom(entityClass) ? TextFormatting.RED : TextFormatting.BLUE;
                    String unlocalizedEntityName = "entity." + entityName + ".name";
                    String localizedEntityName = I18n.format(unlocalizedEntityName);
                    if (localizedEntityName.equals(unlocalizedEntityName))
                        event.getToolTip().add(color + entityName);
                    else
                        event.getToolTip().add(color + localizedEntityName);
                }
            }
        }
    }

    /**
     * may return someday if Mojang adjusts for proper placing of spawners
     * Enables mob spawners to drop themselves when harvested with silk touch
     *
     * @param event The (Block) BreakEvent
     */
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        if (VTConfigHandler.mobSpawnerSilkTouchDrop && !player.capabilities.isCreativeMode && event.getState().getBlock() == Blocks.MOB_SPAWNER && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player.getHeldItemMainhand()) != 0 && player.canHarvestBlock(Blocks.MOB_SPAWNER.getDefaultState())) {
            World world = event.getWorld();
            BlockPos blockPos = event.getPos();
            TileEntityMobSpawner spawnerTileEntity = (TileEntityMobSpawner) world.getTileEntity(blockPos);
            NBTTagCompound spawnerTagCompound = new NBTTagCompound();
            if (spawnerTileEntity != null) {
                spawnerTileEntity.getSpawnerBaseLogic().writeToNBT(spawnerTagCompound);
                System.out.println("SWAG");
            }
            NBTTagCompound stackTagCompound = new NBTTagCompound();
            stackTagCompound.setTag("BlockEntityTag", spawnerTagCompound);
            ItemStack spawnerStack = new ItemStack(Blocks.MOB_SPAWNER);
            spawnerStack.setTagCompound(stackTagCompound);
            EntityItem spawnerEntityItem = new EntityItem(world, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, spawnerStack);
            spawnerEntityItem.setDefaultPickupDelay();
            world.spawnEntity(spawnerEntityItem);
            event.setExpToDrop(0);
        }
    }


    /**
     * Enables the Siphon enchantment functionality
     *
     * @param event The HarvestDropsEvent
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHarvestDrops(HarvestDropsEvent event) {
        EntityPlayer harvester = event.getHarvester();
        if (harvester != null && EnchantmentHelper.getEnchantmentLevel(Siphon, harvester.getHeldItemMainhand()) > 0) {
            List<ItemStack> drops = event.getDrops();
            drops.removeAll(drops.stream().filter(event.getHarvester().inventory::addItemStackToInventory).collect(Collectors.toList()));
        }
    }

    /**
     * Enables the Homing enchantment functionality
     *
     * @param event The ArrowNockEvent
     */
    @SubscribeEvent

    public void onArrowNock(ArrowNockEvent event) {
        if (event.getEntity() instanceof EntityArrow) {
            EntityArrow arrow = (EntityArrow) event.getEntity();
            EntityLivingBase shooter = (EntityLivingBase) arrow.shootingEntity;
            if (shooter != null && EnchantmentHelper.getEnchantmentLevel(Homing, shooter.getHeldItemMainhand()) > 0) {
                int homingLevel = EnchantmentHelper.getEnchantmentLevel(Homing, shooter.getHeldItemMainhand());
                double distance = Math.pow(2, homingLevel - 1) * 32;
                World world = arrow.world;

                @SuppressWarnings("unchecked") List<EntityLivingBase> livingEntities = world.getEntities(EntityLivingBase.class, EntitySelectors.NOT_SPECTATING);
                EntityLivingBase target = null;
                for (EntityLivingBase livingEntity : livingEntities) {
                    double distanceToArrow = livingEntity.getDistance(arrow);

                    if (distanceToArrow < distance && shooter.canEntityBeSeen(livingEntity) && !livingEntity.getPersistentID().equals(shooter.getPersistentID())) {
                        distance = distanceToArrow;
                        target = livingEntity;
                    }
                }
                if (target != null) {
                    double x = target.posX - arrow.posX;
                    double y = target.getEntityBoundingBox().minY + target.height / 2 - (arrow.posY + arrow.height / 2);
                    double z = target.posZ - arrow.posZ;
                    arrow.setPositionAndRotation(x, y, z, 1.25F, 0);
                }
            }
        }
    }

    /**
     * Enables the Hops enchantment functionality
     *
     * @param event The LivingJumpEvent
     */
    @SubscribeEvent
    public void onLivingJump(LivingJumpEvent event) {
        if (EnchantmentHelper.getEnchantmentLevel(Hops, event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET)) > 0) {
            float enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Hops, event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET));
            event.getEntity().motionY = event.getEntity().motionY + enchantmentLevel / 10;
        }
    }

    /**
     * Prevents taking extra fall damage from the Hops enchantment
     *
     * @param event The LivingFallEvent
     */
    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        if (EnchantmentHelper.getEnchantmentLevel(Hops, event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET)) > 0)
            event.setDistance(event.getDistance() - EnchantmentHelper.getEnchantmentLevel(VTEnchantments.Hops, event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET)));
    }


    /**
     * Syncs the config file if it changes
     *
     * @param event The OnConfigChangedEvent
     */
    @SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event) {
        if (event.getModID().equals(vtModInfo.MOD_ID))
            VTConfigHandler.syncConfig();
    }

    /**
     * Enables binoculars functionality
     *
     * @param event The FOVUpdateEvent
     */
    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent event) {
        ItemStack helmet = event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if (helmet != null && helmet.getItem() == VTItems.binoculars)
            event.setNewfov(event.getFov() / VTConfigHandler.binocularZoomAmount);
    }

    /**
     * Syncs up Veteran enchantment to the client
     *
     * @param event The ClientTickEvent
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        World world = Minecraft.getMinecraft().world;
        if (world != null) {
            List<Entity> xpOrbs = world.getEntities(EntityXPOrb.class, EntitySelectors.IS_ALIVE);
            for (Entity xpOrb : xpOrbs)
                if (xpOrbs instanceof EntityXPOrb) {
                    double range = 32;
                    EntityPlayer closestPlayer = world.getClosestPlayerToEntity(xpOrb, range);
                    if (closestPlayer != null && EnchantmentHelper.getEnchantmentLevel(Veteran, closestPlayer.getItemStackFromSlot(EntityEquipmentSlot.HEAD)) > 0) {
                        double xDiff = (closestPlayer.posX - xpOrb.posX) / range;
                        double yDiff = (closestPlayer.posY + closestPlayer.getEyeHeight() - xpOrb.posY) / range;
                        double zDiff = (closestPlayer.posZ - xpOrb.posZ) / range;
                        double movementFactor = Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
                        double invertedMovementFactor = 1 - movementFactor;
                        if (invertedMovementFactor > 0) {
                            invertedMovementFactor *= invertedMovementFactor;
                            xpOrb.motionX += xDiff / movementFactor * invertedMovementFactor * 0.1;
                            xpOrb.motionY += yDiff / movementFactor * invertedMovementFactor * 0.1;
                            xpOrb.motionZ += zDiff / movementFactor * invertedMovementFactor * 0.1;
                        }
                    }
                }

            List<Entity> arrows = world.getEntities(EntityArrow.class, EntitySelectors.IS_ALIVE);
            for (Entity arrow : arrows)
                if (arrows instanceof EntityArrow) {
                    EntityArrow ar = (EntityArrow) arrows;
                    EntityLivingBase shooter = (EntityLivingBase) ar.shootingEntity;
                    if (shooter != null && EnchantmentHelper.getEnchantmentLevel(Homing, shooter.getHeldItemMainhand()) > 0) {
                        int homingLevel = EnchantmentHelper.getEnchantmentLevel(Homing, shooter.getHeldItemMainhand());
                        double distance = Math.pow(2, homingLevel - 1) * 32;

                        @SuppressWarnings("unchecked") List<EntityLivingBase> livingEntities = world.getEntities(EntityLivingBase.class, EntitySelectors.NOT_SPECTATING);
                        EntityLivingBase target = null;
                        for (EntityLivingBase livingEntity : livingEntities) {
                            double distanceToArrow = livingEntity.getDistance(arrow);

                            if (distanceToArrow < distance && shooter.canEntityBeSeen(livingEntity) && !livingEntity.getPersistentID().equals(shooter.getPersistentID())) {
                                distance = distanceToArrow;
                                target = livingEntity;
                            }
                        }
                        if (target != null) {
                            double x = target.posX - arrow.posX;
                            double y = target.getEntityBoundingBox().minY + target.height / 2 - (arrow.posY + arrow.height / 2);
                            double z = target.posZ - arrow.posZ;
                            arrow.setPositionAndRotationDirect(x, y, z, 1.25F, 0, 0, false);
                        }
                    }
                }
        }
    }


    /**
     * Allows the End_portal_Frame drops
     *
     * @param event The LivingDropsEvent
     */

    @SubscribeEvent
    public void onPortalBreak(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        World world = event.getWorld();
        BlockPos blockPos = event.getPos();
        if (event.getState().getBlock() == Blocks.END_PORTAL_FRAME) {
            ItemStack portalStack = new ItemStack(Blocks.END_PORTAL_FRAME);
            EntityItem portalEntityItem = new EntityItem(world, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, portalStack);
            portalEntityItem.setDefaultPickupDelay();
            world.spawnEntity(portalEntityItem);
        }
    }

    /**
     * Enables the Vigor Functionality
     *
     * @param event The LivingEquipmentChangeEvent
     */

    @SubscribeEvent
    public void onLivingUpdate(LivingEquipmentChangeEvent event) {
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
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        boolean success = false;

        TileEntity te = event.getWorld().getTileEntity(event.getPos());
        if (te instanceof TileEntitySign) {
            TileEntitySign sign = (TileEntitySign) te;
            if (event.getEntityPlayer().isSneaking() && clearSigns) {
                ITextComponent[] text = new ITextComponent[]{new TextComponentString(""), new TextComponentString(""), new TextComponentString(""), new TextComponentString("")};
                ObfuscationReflectionHelper.setPrivateValue(TileEntitySign.class, sign, text, "signText", "field_145915_a");
                success = true;
            } else if (editSigns) {
                event.getEntityPlayer().openEditSign(sign);
                success = true;
            }
        }

        if (success) {
            event.setCanceled(true);
            event.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
        }
    }


}
