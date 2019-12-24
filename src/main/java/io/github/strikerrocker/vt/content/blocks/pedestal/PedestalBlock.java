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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class PedestalBlock extends Block {
    private VoxelShape base = Block.makeCuboidShape(0.5, 0.0, 0.5, 15.5, 1.0, 15.5);
    private VoxelShape deco1 = Block.makeCuboidShape(2.0, 1.0, 2.0, 14.0, 2.0, 14.0);
    private VoxelShape pillar = Block.makeCuboidShape(4.5, 2.0, 4.5, 11.5, 12.0, 11.5);
    private VoxelShape deco2 = Block.makeCuboidShape(2.0, 12.0, 2.0, 14.0, 13.0, 14.0);
    private VoxelShape top = Block.makeCuboidShape(1, 13.0, 1, 15.0, 15.0, 15.0);
    private VoxelShape PEDESTAL_VOXEL_SHAPE = VoxelShapes.or(base, deco1, pillar, deco2, top);

    public PedestalBlock() {
        super(Block.Properties.create(Material.ROCK, MaterialColor.GRAY_TERRACOTTA).hardnessAndResistance(2.0f, 10.0f));
        this.setRegistryName("pedestal");
    }

    private static PedestalTileEntity getPedestalTE(IWorldReader world, BlockPos pos) {
        return (PedestalTileEntity) world.getTileEntity(pos);
    }

    @Override
    public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            ItemStack heldItem = player.getHeldItem(handIn);
            PedestalTileEntity tile = getPedestalTE(worldIn, pos);
            if (!player.isCrouching()) {
                tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, hit.getFace()).ifPresent(itemHandler -> {
                    if (heldItem.isEmpty()) {
                        player.setHeldItem(handIn, itemHandler.extractItem(0, 64, false));
                    } else {
                        player.setHeldItem(handIn, itemHandler.insertItem(0, heldItem, false));
                    }
                });
                tile.markDirty();
            } else {
                NetworkHooks.openGui((ServerPlayerEntity) player, new SimpleNamedContainerProvider((id, playerInv, playerIn) -> new PedestalContainer(id, playerInv, pos), new TranslationTextComponent("block.vanillatweaks.pedestal")), pos);
            }
        }
        return ActionResultType.PASS;
    }


    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!worldIn.isRemote()) {
            PedestalTileEntity tile = getPedestalTE(worldIn, pos);
            tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.NORTH).ifPresent(itemHandler -> {
                ItemStack stack = itemHandler.getStackInSlot(0);
                if (!stack.isEmpty() && !worldIn.isRemote) {
                    ItemEntity item = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
                    worldIn.addEntity(item);
                }
            });
            worldIn.removeTileEntity(pos);
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

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return PEDESTAL_VOXEL_SHAPE;
    }
}
