package io.github.strikerrocker.vt.content.blocks.pedestal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPedestal extends Block {
    public BlockPedestal() {
        super(Block.Properties.create(Material.ROCK, MaterialColor.GRAY_TERRACOTTA).hardnessAndResistance(2.0f, 10.0f));
        this.setRegistryName("pedestal");
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            ItemStack heldItem = player.getHeldItem(handIn);
            PedestalTileEntity tile = (PedestalTileEntity) worldIn.getTileEntity(pos);
            /*IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, hit.getFace());
            if (!player.isSneaking()) {
                if (heldItem.isEmpty()) {
                    player.setHeldItem(handIn, itemHandler.extractItem(0, 64, false));
                } else {
                    player.setHeldItem(handIn, itemHandler.insertItem(0, heldItem, false));
                }
                tile.markDirty();
            } else {
                player.openGui(VanillaTweaks.instance, GuiHandler.PEDESTAL, world, pos.getX(), pos.getY(), pos.getZ());
            }*/
        }
        return true;
    }

    /*@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        ItemStack stack = world.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            world.spawnEntity(item);
        }
        super.breakBlock(world, pos, state);
    }*/

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new PedestalTileEntity();
    }
}
