package io.github.strikerrocker.vt.events;

import io.github.strikerrocker.vt.enchantments.VTEnchantments;
import io.github.strikerrocker.vt.entities.EntitySitting;
import io.github.strikerrocker.vt.handlers.VTConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSponge;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static io.github.strikerrocker.vt.events.VTEventHandler.canHarvest;
import static io.github.strikerrocker.vt.handlers.VTConfigHandler.hoeAsSickle;
import static io.github.strikerrocker.vt.handlers.VTConfigHandler.spongeDryInNether;

@Mod.EventBusSubscriber
public class BlockEvents
{
    /**
     * Allows the End Portal Frame to be broken
     *
     * @param event The BreakEvent
     */
    @SubscribeEvent
    public static void onPortalBreak(BlockEvent.BreakEvent event) {
        World world = event.getWorld();
        BlockPos blockPos = event.getPos();
        if (event.getState().getBlock() == Blocks.END_PORTAL_FRAME && VTConfigHandler.endFrameBroken) {
            ItemStack portalStack = new ItemStack(Blocks.END_PORTAL_FRAME);
            EntityItem portalEntityItem = new EntityItem(world, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, portalStack);
            portalEntityItem.setDefaultPickupDelay();
            world.spawnEntity(portalEntityItem);
        }
    }

    /**
     * may return someday if Mojang adjusts for proper placing of spawners
     * Enables mob spawners to drop themselves when harvested with silk touch
     *
     * @param event The (Block) BreakEvent
     */
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
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
     * Makes Hoe act as Sickle
     *
     * @param event BreakEvent
     */

    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        ItemStack stack = event.getPlayer().getHeldItemMainhand();
        if (!stack.isEmpty() && stack.getItem() instanceof ItemHoe && canHarvest(event.getState()) && hoeAsSickle) {
            World world = event.getWorld();
            EntityPlayer player = event.getPlayer();
            BlockPos basePos = event.getPos();

            int range = 1;
            if (stack.getItem() == Items.DIAMOND_HOE)
                range++;

            for (int i = -range; i < range + 1; i++)
                for (int k = -range; k < range + 1; k++) {
                    if (i == 0 && k == 0)
                        continue;

                    BlockPos pos = basePos.add(i, 0, k);
                    IBlockState state = world.getBlockState(pos);
                    if (canHarvest(state)) {
                        Block block = state.getBlock();
                        if (block.canHarvestBlock(world, pos, player))
                            block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), stack);
                        world.setBlockToAir(pos);
                        world.playEvent(2001, pos, Block.getIdFromBlock(block) + (block.getMetaFromState(state) << 12));
                    }
                }

            stack.damageItem(1, player);
        }
    }

    /**
     * Removes the siiting function
     *
     * @param event BreakEvent
     */
    @SubscribeEvent
    public static void onBreak(BlockEvent.BreakEvent event) {
        if (EntitySitting.OCCUPIED.containsKey(event.getPos())) {
            EntitySitting.OCCUPIED.get(event.getPos()).setDead();
            EntitySitting.OCCUPIED.remove(event.getPos());
        }
    }

    /**
     * Enables the sponges dry in nether functionality
     *
     * @param event The PlaceEvent
     */
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.PlaceEvent event) {
        if (event.getPlacedBlock().equals(Blocks.SPONGE.getDefaultState().withProperty(BlockSponge.WET, true)) &&
                BiomeDictionary.getTypes(event.getWorld().getBiome(event.getPos())).contains(BiomeDictionary.Type.NETHER) && spongeDryInNether) {
            World world = event.getWorld();
            world.playSound(null, event.getPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 2.4F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.9F);
            world.setBlockState(event.getPos(), Blocks.SPONGE.getDefaultState().withProperty(BlockSponge.WET, false));
        }
    }


    /**
     * Enables the Siphon enchantment functionality
     *
     * @param event The HarvestDropsEvent
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        EntityPlayer harvester = event.getHarvester();
        VTEnchantments.performAction("blazing", harvester, event);
        VTEnchantments.performAction("siphon", harvester, event);
    }
}
