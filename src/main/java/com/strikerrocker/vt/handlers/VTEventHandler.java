package com.strikerrocker.vt.handlers;

import com.strikerrocker.vt.VTUtils;
import com.strikerrocker.vt.enchantments.EntityTickingEnchantment;
import com.strikerrocker.vt.enchantments.VTEnchantmentBase;
import com.strikerrocker.vt.enchantments.VTEnchantments;
import com.strikerrocker.vt.entities.EntitySitting;
import com.strikerrocker.vt.items.VTItems;
import com.strikerrocker.vt.vtModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

import static com.strikerrocker.vt.handlers.VTConfigHandler.*;

/**
 * The event handler for Vanilla Tweaks
 */
@SuppressWarnings({"unused", "unchecked"})
public final class VTEventHandler {
    /**
     * The singleton instance of the event handler
     */
    public static VTEventHandler instance = new VTEventHandler();
    /**
     * Whether or not to enable the potion effect overlay
     */
    private static boolean displayPotionEffects = true;


    /**
     * Returns if the given chunk is an slime chunk or not
     *
     * @param world World,x int,z int
     */
    private static boolean isSlimeChunk(World world, int x, int z) {
        Chunk chunk = world.getChunkFromBlockCoords(new BlockPos(x, 0, z));
        return chunk.getRandomWithSeed(987234911L).nextInt(10) == 0;
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
     * Enables the Siphon enchantment functionality
     *
     * @param event The HarvestDropsEvent
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        EntityPlayer harvester = event.getHarvester();
        VTEnchantments.performAction("blazing", harvester, event);
        VTEnchantments.performAction("siphon", harvester, event);
    }

    /**
     * Enables the Hops enchantment functionality
     *
     * @param event The LivingJumpEvent
     */
    @SubscribeEvent
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        VTEnchantments.performAction("hops", event.getEntityLiving(), event);
    }

    /**
     * Syncs up motion-affecting enchantments to the client
     *
     * @param event The ClientTickEvent
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        World world = Minecraft.getMinecraft().theWorld;
        if (world != null) {
            List<Entity> arrows = world.getEntities(EntityArrow.class, EntitySelectors.IS_ALIVE);
            for (Entity arrow : arrows)
                VTEnchantments.performAction("homing", arrow, null);
            List<Entity> xpOrbs = world.getEntities(EntityXPOrb.class, EntitySelectors.IS_ALIVE);
            for (Entity xpOrb : xpOrbs)
                VTEnchantments.performAction("veteran", xpOrb, null);
        }
    }

    /**
     * Enables the Quickdraw enchantment functionality
     *
     * @param event The ArrowNockEvent
     */
    @SubscribeEvent
    public void onArrowNock(ArrowNockEvent event) {
        VTEnchantments.performAction("quickdraw", event.getEntityPlayer(), event);
    }


    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        World world = event.world;
        List<EntityItem> entityItems = world.getEntities(EntityItem.class, EntitySelectors.IS_ALIVE);
        for (Object entityObject : world.getEntities(Entity.class, EntitySelectors.IS_ALIVE))
            VTEnchantmentBase.cppEnchantments.stream().filter(cppEnchantment -> cppEnchantment.getClass().isAnnotationPresent(EntityTickingEnchantment.class)).forEach(cppEnchantment -> cppEnchantment.performAction((Entity) entityObject, null));
    }

    /**
     * Prevents taking extra fall damage from the Hops enchantment
     *
     * @param event The LivingFallEvent
     */
    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        VTEnchantments.performAction("hops", event.getEntityLiving(), event);
    }

    @SubscribeEvent
    public void onBreak(BlockEvent.BreakEvent event) {
        if (EntitySitting.OCCUPIED.containsKey(event.getPos())) {
            EntitySitting.OCCUPIED.get(event.getPos()).setDead();
            EntitySitting.OCCUPIED.remove(event.getPos());
        }
    }

    /**
     * Allows the End Portal Frame to be broken
     *
     * @param event The BreakEvent
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
            world.spawnEntityInWorld(portalEntityItem);
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
            world.spawnEntityInWorld(spawnerEntityItem);
            event.setExpToDrop(0);
        }
    }

    /**
     * Affects living entity drops
     *
     * @param event The LivingDropsEvent
     */
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        World world = entity.worldObj;
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
            ItemStack dropItem = dropEntity.getEntityItem();
            if (event.getSource().getEntity() != null) {
                Item drop = dropItem.getItem();
                Entity source = event.getSource().getEntity();
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
     * Makes creepers and baby zombies burn in daylight. <br>
     * Also gives functionality to all ticking enchantments.
     *
     * @param event The LivingUpdateEvent
     */
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase livingEntity = event.getEntityLiving();
        if (!livingEntity.worldObj.isRemote) {
            World world = livingEntity.worldObj;
            if (((livingEntity instanceof EntityCreeper && VTConfigHandler.creeperBurnInDaylight) || (livingEntity instanceof EntityZombie && livingEntity.isChild() && VTConfigHandler.babyZombieBurnInDaylight)) && world.isDaytime()) {
                float f = livingEntity.getBrightness(1);
                Random random = world.rand;
                BlockPos blockPos = new BlockPos(livingEntity.posX, Math.round(livingEntity.posY), livingEntity.posZ);
                if (f > 0.5 && random.nextFloat() * 30 < (f - 0.4) * 2 && world.canSeeSky(blockPos)) {
                    ItemStack itemstack = livingEntity.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                    boolean doSetFire = false;
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

    /**
     * Allows the player to edit and clear signs
     *
     * @param event The RightClickBlockEvent
     */
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

    /**
     * Enables the functionality of slime finder
     *
     * @param event The RightClickItemEvent
     */
    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (!event.getWorld().isRemote && slimeChunkFinder) {
            ItemStack slimestack = new ItemStack(VTItems.slime);
            if (event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isItemEqual(slimestack)) {
                int x = MathHelper.floor_double(event.getEntityPlayer().posX);
                int y = MathHelper.floor_double(event.getEntityPlayer().posY);
                int z = MathHelper.floor_double(event.getEntityPlayer().posZ);
                boolean slime = isSlimeChunk(event.getWorld(), x, y);
                if (slime)
                    event.getEntityPlayer().addChatComponentMessage(new TextComponentString("Slime Chunk"));
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
            World world = player.worldObj;
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
            World world = player.worldObj;
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
     * Allows the sitting functionality
     *
     * @param event The RightClickBlockEvent
     */

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getWorld().isRemote && stairSit) {
            World w = event.getWorld();
            BlockPos p = event.getPos();
            IBlockState s = w.getBlockState(p);
            Block b = w.getBlockState(p).getBlock();
            EntityPlayer e = event.getEntityPlayer();

            if ((b instanceof BlockSlab || b instanceof BlockStairs) && !EntitySitting.OCCUPIED.containsKey(p) && e.getHeldItemMainhand() == null) {
                if (b instanceof BlockSlab && s.getValue(BlockSlab.HALF) != BlockSlab.EnumBlockHalf.BOTTOM)
                    return;
                else if (b instanceof BlockStairs && s.getValue(BlockStairs.HALF) != BlockStairs.EnumHalf.BOTTOM)
                    return;

                EntitySitting sit = new EntitySitting(w, p);

                w.spawnEntityInWorld(sit);
                e.startRiding(sit);
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
     * Prevents potion effects from shifting your inventory to the side.
     *
     * @param event The PotionShiftEventf
     */
    @SubscribeEvent
    public void onPotionShiftEvent(GuiScreenEvent.PotionShiftEvent event) {
        event.setCanceled(true);
    }


    /**
     * Adds an tooltip for foods to show the hunger bars
     *
     * @param event The PostTextEvent
     */
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderTooltip(RenderTooltipEvent.PostText event) {
        if (event.getStack() != null && event.getStack().getItem() instanceof ItemFood) {
            int divisor = 2;
            GlStateManager.pushMatrix();
            GlStateManager.color(1F, 1F, 1F);
            Minecraft mc = Minecraft.getMinecraft();
            mc.getTextureManager().bindTexture(GuiIngameForge.ICONS);
            ItemFood food = ((ItemFood) event.getStack().getItem());
            int pips = food.getHealAmount(event.getStack());
            PotionEffect eff = ReflectionHelper.getPrivateValue(ItemFood.class, food, VTUtils.POTION_ID);
            boolean poison = eff != null && eff.getPotion() != null && eff.getPotion().isBadEffect();
            for (int i = 0; i < Math.ceil((double) pips / divisor); i++) {
                int x = event.getX() + i * 9 - 2;
                int y = event.getY() + 15;

                if (mc.currentScreen instanceof GuiContainerCreative && ((GuiContainerCreative) mc.currentScreen).getSelectedTabIndex() == CreativeTabs.SEARCH.getTabIndex())
                    y += 10;

                int u = 16;
                if (poison)
                    u += 117;
                int v = 27;

                Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, 9, 9, 256, 256);

                u = 52;
                if (pips % 2 != 0 && i == 0)
                    u += 9;
                if (poison)
                    u += 36;

                Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, 9, 9, 256, 256);
            }

            GlStateManager.popMatrix();
        }
    }

    /**
     * Gives the player the workbenching achievement when crafting a crafting pad
     *
     * @param event The ItemCraftingEvent
     */
    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.crafting.getItem() == VTItems.pad)
            event.player.addStat(AchievementList.BUILD_WORK_BENCH);
    }

}
