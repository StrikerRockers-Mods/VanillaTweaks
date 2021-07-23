package io.github.strikerrocker.vt.content.blocks.pedestal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
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
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class PedestalBlock extends Block implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final VoxelShape base = Block.box(0.5, 0.0, 0.5, 15.5, 1.0, 15.5);
    private final VoxelShape deco1 = Block.box(2.0, 1.0, 2.0, 14.0, 2.0, 14.0);
    private final VoxelShape pillar = Block.box(4.5, 2.0, 4.5, 11.5, 12.0, 11.5);
    private final VoxelShape deco2 = Block.box(2.0, 12.0, 2.0, 14.0, 13.0, 14.0);
    private final VoxelShape top = Block.box(1, 13.0, 1, 15.0, 15.0, 15.0);
    private final VoxelShape PEDESTAL_VOXEL_SHAPE = VoxelShapes.or(base, deco1, pillar, deco2, top);

    public PedestalBlock() {
        super(Block.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_GRAY).strength(2.0f, 10.0f));
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
        this.setRegistryName("pedestal");
    }

    private static PedestalTileEntity getPedestalTE(IWorldReader world, BlockPos pos) {
        return (PedestalTileEntity) world.getBlockEntity(pos);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isClientSide()) {
            ItemStack heldItem = player.getItemInHand(handIn);
            PedestalTileEntity tile = getPedestalTE(worldIn, pos);
            if (!player.isCrouching()) {
                tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
                    if (heldItem.isEmpty()) {
                        ItemStack stack = itemHandler.extractItem(0, 64, false);
                        player.setItemInHand(handIn, stack);
                    } else {
                        player.setItemInHand(handIn, itemHandler.insertItem(0, heldItem, false));
                    }
                });
                tile.setChanged();
                return ActionResultType.SUCCESS;
            } else {
                NetworkHooks.openGui((ServerPlayerEntity) player, new SimpleNamedContainerProvider((id, playerInv, playerIn) -> new PedestalContainer(id, playerInv, pos), new TranslationTextComponent("block.vanillatweaks.pedestal")), pos);
            }
        }
        return ActionResultType.SUCCESS;
    }


    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!worldIn.isClientSide()) {
            PedestalTileEntity tile = getPedestalTE(worldIn, pos);
            tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.NORTH).ifPresent(itemHandler -> {
                ItemStack stack = itemHandler.getStackInSlot(0);
                if (!stack.isEmpty()) {
                    ItemEntity item = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
                    worldIn.addFreshEntity(item);
                }
            });
            worldIn.removeBlockEntity(pos);
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

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}