package io.github.strikerrocker.vt.content.blocks.pedestal;

import io.github.strikerrocker.vt.content.BasePedestalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BasePedestalBlock implements EntityBlock {

    public PedestalBlock() {
        super();
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
    @Nullable
    public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        return blockEntity instanceof MenuProvider ? (MenuProvider) blockEntity : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }
}