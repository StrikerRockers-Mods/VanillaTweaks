package io.github.strikerrocker.vt.events;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.enchantments.VTEnchantments;
import io.github.strikerrocker.vt.entities.EntitySitting;
import io.github.strikerrocker.vt.handlers.ConfigHandler;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static io.github.strikerrocker.vt.events.VTEventHandler.canHarvest;
import static net.minecraft.block.BlockTNT.EXPLODE;


@Mod.EventBusSubscriber(modid = VTModInfo.MODID)
public class BlockEvents {

    private static Item mobSpawnerItem = Item.getItemFromBlock(Blocks.MOB_SPAWNER);

    /**
     * @param event The (Block) BreakEvent
     */
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        World world = event.getWorld();
        BlockPos blockPos = event.getPos();
        /*
        Allows the End Portal Frame to be broken
         */
        if (event.getState().getBlock() == Blocks.END_PORTAL_FRAME && ConfigHandler.miscellanious.endFrameBroken) {
            ItemStack portalStack = new ItemStack(Blocks.END_PORTAL_FRAME);
            EntityItem portalEntityItem = new EntityItem(world, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, portalStack);
            portalEntityItem.setDefaultPickupDelay();
            world.spawnEntity(portalEntityItem);
        }
        /*
        Makes Hoe act as Sickle
         */
        ItemStack stack = player.getHeldItemMainhand();
        if (!stack.isEmpty() && stack.getItem() instanceof ItemHoe && canHarvest(event.getState()) && ConfigHandler.drops.hoeAsSickle) {
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
        /*
        Removes the siiting function
         */
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
        IBlockState blockState = event.getPlacedBlock();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        ItemStack stack = event.getPlayer().getHeldItem(event.getHand());
        if (blockState == Blocks.SPONGE.getDefaultState().withProperty(BlockSponge.WET, true) &&
                BiomeDictionary.getTypes(world.getBiome(pos)).contains(BiomeDictionary.Type.NETHER) && ConfigHandler.drops.spongeDryInNether) {
            world.playSound(null, event.getPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 2.4F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.9F);
            world.setBlockState(event.getPos(), Blocks.SPONGE.getDefaultState().withProperty(BlockSponge.WET, false));
        }
        if (blockState.getBlock() instanceof BlockTNT) {
            for (EnumFacing f : EnumFacing.values())
                if (world.getBlockState(pos.offset(f, 1)).getBlock() instanceof BlockMagma || world.getBlockState(pos.offset(f, 1)).getMaterial() == Material.LAVA) {
                    BlockTNT blockTNT = (BlockTNT) blockState.getBlock();
                    blockTNT.explode(world, pos, blockState.withProperty(EXPLODE, true), event.getPlayer());
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
                }
        }
        //Handles mob spawner placement logic
        if (stack.getItem() == mobSpawnerItem && stack.hasTagCompound()) {
            NBTTagCompound stackTag = stack.getTagCompound();
            assert stackTag != null;
            NBTTagCompound spawnerDataNBT = stackTag.getCompoundTag("SilkSpawnerData");
            if (!spawnerDataNBT.isEmpty()) {
                TileEntity tile = event.getWorld().getTileEntity(event.getPos());
                if (tile instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner) tile).getSpawnerBaseLogic().readFromNBT(spawnerDataNBT);
                }
            }
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

    /**
     * Handles mod spawner breaking
     *
     * @param event The BreakEvent
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBreak(BlockEvent.BreakEvent event) {
        IBlockState state = event.getState();
        World world = event.getWorld();
        TileEntity tile = world.getTileEntity(event.getPos());
        EntityPlayer player = event.getPlayer();
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player.getHeldItem(player.getActiveHand()));
        if (state.getBlock() instanceof BlockMobSpawner && !world.isRemote && tile instanceof TileEntityMobSpawner && ConfigHandler.drops.mobSpawnerSilkTouchDrop && lvl >= 1) {
            event.setExpToDrop(0);
            ItemStack drop = new ItemStack(Blocks.MOB_SPAWNER);
            NBTTagCompound spawnerData = ((TileEntityMobSpawner) tile).getSpawnerBaseLogic().writeToNBT(new NBTTagCompound());
            NBTTagCompound stackTag = new NBTTagCompound();
            spawnerData.removeTag("Delay");
            stackTag.setTag("SilkSpawnerData", spawnerData);
            drop.setTagCompound(stackTag);

            Block.spawnAsEntity(world, event.getPos(), drop);
            //TODO: does this cause problems w/ block protection?
            world.destroyBlock(event.getPos(), false);
            world.removeTileEntity(event.getPos());
            event.setCanceled(true);
        }
    }
}
