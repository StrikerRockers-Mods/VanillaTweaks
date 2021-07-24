package io.github.strikerrocker.vt.content.blocks.pedestal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
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
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class PedestalBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final VoxelShape base = Block.box(0.5, 0.0, 0.5, 15.5, 1.0, 15.5);
    private final VoxelShape deco1 = Block.box(2.0, 1.0, 2.0, 14.0, 2.0, 14.0);
    private final VoxelShape pillar = Block.box(4.5, 2.0, 4.5, 11.5, 12.0, 11.5);
    private final VoxelShape deco2 = Block.box(2.0, 12.0, 2.0, 14.0, 13.0, 14.0);
    private final VoxelShape top = Block.box(1, 13.0, 1, 15.0, 15.0, 15.0);
    private final VoxelShape PEDESTAL_VOXEL_SHAPE = Shapes.or(base, deco1, pillar, deco2, top);

    public PedestalBlock() {
        super(Block.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_GRAY).strength(2.0f, 10.0f));
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
        this.setRegistryName("pedestal");
    }

    private static PedestalBlockEntity getPedestalTE(LevelReader world, BlockPos pos) {
        return (PedestalBlockEntity) world.getBlockEntity(pos);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!worldIn.isClientSide()) {
            ItemStack heldItem = player.getItemInHand(handIn);
            PedestalBlockEntity tile = getPedestalTE(worldIn, pos);
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
                return InteractionResult.SUCCESS;
            } else {
                NetworkHooks.openGui((ServerPlayer) player, new SimpleMenuProvider((id, playerInv, playerIn) -> new PedestalContainer(id, playerInv, pos), new TranslatableComponent("block.vanillatweaks.pedestal")), pos);
            }
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!worldIn.isClientSide()) {
            PedestalBlockEntity tile = getPedestalTE(worldIn, pos);
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
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return PEDESTAL_VOXEL_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
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