package io.github.strikerrocker.vt.blocks.pedestal;

import io.github.strikerrocker.vt.blocks.BlockTileEntity;
import io.github.strikerrocker.vt.blocks.VTBlocks;
import io.github.strikerrocker.vt.handlers.VTGuiHandler;
import io.github.strikerrocker.vt.vt;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class BlockPedestal extends BlockTileEntity<TileEntityPedestal>
{

    public BlockPedestal() {
        super(Material.ROCK, "pedestal", MapColor.GRAY_STAINED_HARDENED_CLAY);
        String name = "pedestal";
        this.setUnlocalizedName(name);
    }


    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            ItemStack heldItem = player.getHeldItem(hand);
            TileEntityPedestal tile = getTileEntity(world, pos);
            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            if (!player.isSneaking()) {
                if (heldItem.isEmpty()) {
                    player.setHeldItem(hand, itemHandler.extractItem(0, 64, false));
                } else {
                    player.setHeldItem(hand, itemHandler.insertItem(0, heldItem, false));
                }
                tile.markDirty();
            } else {
                player.openGui(vt.instance, VTGuiHandler.PEDESTAL, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityPedestal tile = getTileEntity(world, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        ItemStack ped = new ItemStack(VTBlocks.pedestal);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            EntityItem pedestal = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), ped);
            world.spawnEntity(item);
            world.spawnEntity(pedestal);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public Class<TileEntityPedestal> getTileEntityClass() {
        return TileEntityPedestal.class;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntityPedestal createTileEntity(World world, IBlockState state) {
        return new TileEntityPedestal();
    }

}