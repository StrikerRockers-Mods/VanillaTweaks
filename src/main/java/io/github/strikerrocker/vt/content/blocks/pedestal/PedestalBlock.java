package io.github.strikerrocker.vt.content.blocks.pedestal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class PedestalBlock extends Block {
    public PedestalBlock() {
        super(Block.Properties.create(Material.ROCK, MaterialColor.GRAY_TERRACOTTA).hardnessAndResistance(2.0f, 10.0f));
        this.setRegistryName("pedestal");
    }

    private static PedestalTileEntity getPedestalTE(IWorldReader world, BlockPos pos) {
        return (PedestalTileEntity) world.getTileEntity(pos);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            ItemStack heldItem = player.getHeldItem(handIn);
            PedestalTileEntity tile = getPedestalTE(worldIn, pos);
            if (!player.isSneaking()) {
                tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, hit.getFace()).ifPresent(itemHandler -> {
                    if (heldItem.isEmpty()) {
                        player.setHeldItem(handIn, itemHandler.extractItem(0, 64, false));
                    } else {
                        player.setHeldItem(handIn, itemHandler.insertItem(0, heldItem, false));
                    }
                });
                tile.markDirty();
            } else {
                //Gui doesnt open?

                NetworkHooks.openGui((ServerPlayerEntity) player, new SimpleNamedContainerProvider((id, playerInv, playerIn) -> new PedestalContainer(id, playerInv, pos), new TranslationTextComponent("block.vanillatweaks.pedestal")));
            }
        }
        return true;
    }


    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!worldIn.isRemote()) {
            PedestalTileEntity tile = getPedestalTE(worldIn, pos);
            tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.NORTH).ifPresent(itemHandler -> {
                ItemStack stack = itemHandler.getStackInSlot(0);
                if (stack.isEmpty() && !worldIn.isRemote) {
                    ItemEntity item = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
                    worldIn.addEntity(item);
                }
            });
        }
    }

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
