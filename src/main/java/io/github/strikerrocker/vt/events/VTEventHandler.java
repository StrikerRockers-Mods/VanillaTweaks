package io.github.strikerrocker.vt.events;

import io.github.strikerrocker.vt.VT;
import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.capabilities.SelfPlantingProvider;
import io.github.strikerrocker.vt.compat.baubles.BaubleTools;
import io.github.strikerrocker.vt.handlers.VTConfigHandler;
import io.github.strikerrocker.vt.items.VTItems;
import io.github.strikerrocker.vt.misc.VTUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockReed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.DyeUtils;

import static io.github.strikerrocker.vt.blocks.VTBlocks.*;

/**
 * The event handler for Vanilla Tweaks
 */
@Mod.EventBusSubscriber(modid = VTModInfo.MOD_ID)
public class VTEventHandler {
    /**
     * Returns if the given chunk is an slime chunk or not
     *
     * @param world World,x int,z int
     */
    public static boolean isSlimeChunk(World world, int x, int z) {
        Chunk chunk = world.getChunkFromBlockCoords(new BlockPos(x, 0, z));
        return chunk.getRandomWithSeed(987234911L).nextInt(10) == 0;
    }


    /**
     * Returns whether the block is harvestable for hoe sickle
     *
     * @param state IBlockState
     */
    static boolean canHarvest(IBlockState state) {
        Block block = state.getBlock();
        return (block instanceof BlockBush && !(block instanceof BlockLilyPad)) || block instanceof BlockReed;
    }

    /**
     * Swaps the items in armour with player's armor
     *
     * @param player     the player
     * @param armorStand the armour stand
     * @param slot       the slots
     */
    static void swapSlot(EntityPlayer player, EntityArmorStand armorStand, EntityEquipmentSlot slot) {
        ItemStack playerItem = player.getItemStackFromSlot(slot);
        ItemStack armorStandItem = armorStand.getItemStackFromSlot(slot);
        player.setItemStackToSlot(slot, armorStandItem);
        armorStand.setItemStackToSlot(slot, playerItem);
    }

    /**
     * Sets the burn time for various Blocks
     *
     * @param event FurnaceFuelBurnTimeEvent
     */
    @SubscribeEvent
    public static void onFurnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
        Item item = event.getItemStack().getItem();
        if (item == Item.getItemFromBlock(charcoal))
            event.setBurnTime(16000);
        if (item == Item.getItemFromBlock(Blocks.TORCH))
            event.setBurnTime(400);
        if (item == Item.getItemFromBlock(acaciabark) || item == Item.getItemFromBlock(birchbark) || item == Item.getItemFromBlock(darkoakbark)
                || item == Item.getItemFromBlock(junglebark) || item == Item.getItemFromBlock(oakbark) || item == Item.getItemFromBlock(sprucebark))
            event.setBurnTime(300);
    }

    /**
     * Makes the item's colourable
     *
     * @param event AnvilUpdateEvent
     */
    @SubscribeEvent
    public static void onAnvil(AnvilUpdateEvent event) {
        //TODO Fix this
        if (DyeUtils.isDye(event.getRight()) && false) {
            ItemStack stack = event.getLeft().copy();
            stack.setStackDisplayName(VTUtils.getColorTextFromStack(event.getRight()) + event.getLeft().getDisplayName());
            event.setOutput(stack);
            event.setMaterialCost(1);
        }
    }

    /**
     * Attach the self planting capability
     *
     * @param event AttachCapabilityEvent
     */
    @SubscribeEvent
    public static void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityItem) {
            Item item = ((EntityItem) event.getObject()).getItem().getItem();
            if (item instanceof ItemSeeds || item instanceof ItemSeedFood) {
                event.addCapability(new ResourceLocation(VTModInfo.MOD_ID), new SelfPlantingProvider());
            }
        }
    }

    /**
     * Enables binoculars functionality
     *
     * @param event The FOVUpdateEvent
     */
    @SubscribeEvent
    public static void onFOVUpdate(FOVUpdateEvent event) {
        if (event.getEntity() != null) {
            if (event.getEntity() instanceof EntityPlayer) {
                ItemStack helmet = event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                if (!helmet.isEmpty() && helmet.getItem() == VTItems.binocular)
                    event.setNewfov(event.getFov() / VTConfigHandler.binocularZoomAmount);
                if (VT.baubles) if (BaubleTools.hasProbeGoggle(event.getEntity()))
                    event.setNewfov(event.getFov() / VTConfigHandler.binocularZoomAmount);
            }
        }
    }

    /**
     * Prevents potion effects from shifting your inventory to the side.
     *
     * @param event The PotionShiftEvent
     */
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onPotionShiftEvent(GuiScreenEvent.PotionShiftEvent event) {
        event.setCanceled(true);
    }
}