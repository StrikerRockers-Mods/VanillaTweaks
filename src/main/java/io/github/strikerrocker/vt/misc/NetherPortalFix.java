package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.vtModInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = vtModInfo.MOD_ID)
public class NetherPortalFix
{
    private static final int MAX_PORTAL_DISTANCE_SQ = 16;
    private static final String NETHER_PORTAL_FIX = "NetherPortalFix";
    private static final String SCHEDULED_TELEPORT = "NPFScheduledTeleport";
    private static final String FROM = "From";
    private static final String FROM_DIM = "FromDim";
    private static final String TO = "To";
    private static final String TO_DIM = "ToDim";

    /**
     * Taken from CoFHCore's EntityHelper (https://github.com/CoFH/CoFHCore/blob/1.12/src/main/java/cofh/core/util/helpers/EntityHelper.java) under "Don't Be a Jerk" License
     */
    private static void transferEntityToWorld(Entity entity, WorldServer oldWorld, WorldServer newWorld, BlockPos pos) {
        oldWorld.profiler.startSection("placing");
        if (entity.isEntityAlive()) {
            entity.setLocationAndAngles(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, entity.rotationYaw, entity.rotationPitch);
            entity.fallDistance = 0f;
            newWorld.spawnEntity(entity);
            newWorld.updateEntityWithOptionalForce(entity, false);
        }
        oldWorld.profiler.endSection();
        entity.setWorld(newWorld);
    }

    /**
     * Taken from CoFHCore's EntityHelper (https://github.com/CoFH/CoFHCore/blob/1.12/src/main/java/cofh/core/util/helpers/EntityHelper.java) under "Don't Be a Jerk" License
     */
    private static void transferPlayerToDimension(EntityPlayerMP player, int dimension, PlayerList manager, BlockPos pos) {
        player.setEntityInvulnerable(true);
        int oldDim = player.dimension;
        WorldServer oldWorld = manager.getServerInstance().getWorld(player.dimension);
        player.dimension = dimension;
        WorldServer newWorld = manager.getServerInstance().getWorld(player.dimension);
        player.connection.sendPacket(new SPacketRespawn(player.dimension, newWorld.getDifficulty(), newWorld.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));
        oldWorld.removeEntityDangerously(player);
        if (player.isBeingRidden()) {
            player.removePassengers();
        }
        if (player.isRiding()) {
            player.dismountRidingEntity();
        }
        player.isDead = false;
        transferEntityToWorld(player, oldWorld, newWorld, pos);
        manager.preparePlayer(player, oldWorld);
        player.connection.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
        player.interactionManager.setWorld(newWorld);
        manager.updateTimeAndWeatherForPlayer(player, newWorld);
        manager.syncPlayerInventory(player);
        for (PotionEffect potioneffect : player.getActivePotionEffects()) {
            player.connection.sendPacket(new SPacketEntityEffect(player.getEntityId(), potioneffect));
        }
        FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, oldDim, dimension);
        player.setEntityInvulnerable(false);
    }

    @SubscribeEvent
    public void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        if (event.getEntity() instanceof EntityPlayerMP) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            if (player.getEntityData().hasKey(SCHEDULED_TELEPORT)) {
                return;
            }

            BlockPos fromPos = player.lastPortalPos;
            if (fromPos == null || player.getPosition().getDistance(fromPos.getX(), fromPos.getY(), fromPos.getZ()) > 2) {
                player.lastPortalPos = null;
                return;
            }

            int fromDim = event.getEntity().dimension;
            int toDim = event.getDimension();
            if ((fromDim == 0 && toDim == -1) || (fromDim == -1 && toDim == 0)) {
                NBTTagList portalList = getPlayerPortalList(player);
                NBTTagCompound returnPortal = findReturnPortal(portalList, fromPos, fromDim);
                if (returnPortal != null) {
                    MinecraftServer server = player.getEntityWorld().getMinecraftServer();
                    if (server != null) {
                        World toWorld = server.getWorld(toDim);
                        BlockPos toPos = BlockPos.fromLong(returnPortal.getLong(TO));

                        // Find the lowest possible portal block to prevent any (literal) headaches
                        BlockPos tryPos;
                        while (true) {
                            tryPos = toPos.offset(EnumFacing.DOWN);
                            if (toWorld.getBlockState(tryPos).getBlock() == Blocks.PORTAL) {
                                toPos = tryPos;
                            } else {
                                break;
                            }
                        }

                        if (toWorld.getBlockState(toPos).getBlock() == Blocks.PORTAL) {
                            NBTTagCompound tagCompound = new NBTTagCompound();
                            tagCompound.setInteger(TO_DIM, toDim);
                            tagCompound.setLong(TO, toPos.toLong());
                            player.getEntityData().setTag(SCHEDULED_TELEPORT, tagCompound);
                            event.setCanceled(true);
                        } else {
                            player.sendStatusMessage(new TextComponentTranslation("vt:portal_destroyed"), false);
                            removeReturnPortal(portalList, returnPortal);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == Side.SERVER) {
            NBTTagCompound entityData = event.player.getEntityData();
            if (entityData.hasKey(SCHEDULED_TELEPORT)) {
                NBTTagCompound data = entityData.getCompoundTag(SCHEDULED_TELEPORT);
                int toDim = data.getInteger(TO_DIM);

                // Fire Forge event - our event handler will ignore it due to SCHEDULED_TELEPORT tag. If this is cancelled, do not teleport at all.
                EntityTravelToDimensionEvent travelEvent = new EntityTravelToDimensionEvent(event.player, toDim);
                if (MinecraftForge.EVENT_BUS.post(travelEvent)) {
                    entityData.removeTag(SCHEDULED_TELEPORT);
                    return;
                }

                MinecraftServer server = event.player.getEntityWorld().getMinecraftServer();
                if (server != null) {
                    transferPlayerToDimension((EntityPlayerMP) event.player, toDim, server.getPlayerList(), BlockPos.fromLong(data.getLong(TO)));
                }
                entityData.removeTag(SCHEDULED_TELEPORT);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if ((event.fromDim == 0 && event.toDim == -1) || (event.fromDim == -1 && event.toDim == 0)) {
            EntityPlayer player = event.player;
            BlockPos fromPos = player.lastPortalPos;
            if (fromPos == null) {
                return;
            }
            BlockPos toPos = new BlockPos(player.posX, player.posY, player.posZ);
            NBTTagList portalList = getPlayerPortalList(player);
            storeReturnPortal(portalList, toPos, event.toDim, fromPos);
        }
    }

    private NBTTagList getPlayerPortalList(EntityPlayer player) {
        NBTTagCompound data = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        NBTTagList list = data.getTagList(NETHER_PORTAL_FIX, Constants.NBT.TAG_COMPOUND);
        data.setTag(NETHER_PORTAL_FIX, list);
        player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
        return list;
    }

    @Nullable
    private NBTTagCompound findReturnPortal(NBTTagList portalList, BlockPos triggerPos, int triggerDim) {
        for (NBTBase entry : portalList) {
            NBTTagCompound portal = (NBTTagCompound) entry;
            if (portal.getInteger(FROM_DIM) == triggerDim) {
                BlockPos portalTrigger = BlockPos.fromLong(portal.getLong(FROM));
                if (portalTrigger.distanceSq(triggerPos) <= MAX_PORTAL_DISTANCE_SQ) {
                    return portal;
                }
            }
        }
        return null;
    }

    private void storeReturnPortal(NBTTagList portalList, BlockPos triggerPos, int triggerDim, BlockPos returnPos) {
        NBTTagCompound found = findReturnPortal(portalList, triggerPos, triggerDim);
        if (found == null) {
//            System.out.println("New connection: " + triggerPos + " => " + returnPos);
            NBTTagCompound portalCompound = new NBTTagCompound();
            portalCompound.setLong(FROM, triggerPos.toLong());
            portalCompound.setInteger(FROM_DIM, triggerDim);
            portalCompound.setLong(TO, returnPos.toLong());
            portalList.appendTag(portalCompound);
        } else {
            BlockPos portalReturnPos = BlockPos.fromLong(found.getLong(TO));
            if (portalReturnPos.distanceSq(returnPos) > MAX_PORTAL_DISTANCE_SQ) {
//                System.out.println("Updated connection: " + triggerPos + " => " + returnPos);
                found.setLong(TO, returnPos.toLong());
            } else {
                System.out.println("Used existing connection.");
            }
        }
    }

    private void removeReturnPortal(NBTTagList portalList, NBTTagCompound portal) {
        for (int i = 0; i < portalList.tagCount(); i++) {
            if (portalList.get(i) == portal) {
                portalList.removeTag(i);
                break;
            }
        }
    }
}
