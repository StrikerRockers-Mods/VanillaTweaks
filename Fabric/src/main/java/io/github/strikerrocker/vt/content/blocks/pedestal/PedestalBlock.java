package io.github.strikerrocker.vt.content.blocks.pedestal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final VoxelShape BASE = Block.box(0.5, 0.0, 0.5, 15.5, 1.0, 15.5);
    private final VoxelShape DECO1 = Block.box(2.0, 1.0, 2.0, 14.0, 2.0, 14.0);
    private final VoxelShape PILLAR = Block.box(4.5, 2.0, 4.5, 11.5, 12.0, 11.5);
    private final VoxelShape DECO2 = Block.box(2.0, 12.0, 2.0, 14.0, 13.0, 14.0);
    private final VoxelShape TOP = Block.box(1, 13.0, 1, 15.0, 15.0, 15.0);
    private final VoxelShape PEDESTAL_VOXEL_SHAPE = Shapes.or(BASE, DECO1, PILLAR, DECO2, TOP);

    public PedestalBlock() {
        super(Properties.of(Material.STONE, MaterialColor.TERRACOTTA_GRAY).strength(2.0f, 10.0f));
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide) {
            ItemStack heldItem = player.getItemInHand(hand);
            PedestalBlockEntity blockEntity = (PedestalBlockEntity) world.getBlockEntity(pos);
            if (blockEntity != null) {
                if (player.isShiftKeyDown()) {
                    MenuProvider screenHandlerFactory = state.getMenuProvider(world, pos);
                    if (screenHandlerFactory != null) {
                        player.openMenu(screenHandlerFactory);
                    }
                } else {
                    if (heldItem.isEmpty()) {
                        player.getInventory().placeItemBackInInventory(blockEntity.getItem(0));
                        blockEntity.removeItemNoUpdate(0);
                    } else {
                        player.setItemInHand(hand, blockEntity.getItem(0).copy());
                        blockEntity.setItem(0, heldItem);
                    }
                    if (blockEntity.hasLevel() && !blockEntity.getLevel().isClientSide()) {
                        ((ServerLevel) world).getChunkSource().blockChanged(pos);
                    }
                    return InteractionResult.SUCCESS;
                }
                if (blockEntity.hasLevel() && !blockEntity.getLevel().isClientSide()) {
                    ((ServerLevel) world).getChunkSource().blockChanged(pos);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PedestalBlockEntity) {
                Containers.dropContents(world, pos, (PedestalBlockEntity) blockEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return PEDESTAL_VOXEL_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor world, BlockPos pos, BlockPos posFrom) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }
}