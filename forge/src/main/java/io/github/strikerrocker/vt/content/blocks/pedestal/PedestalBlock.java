package io.github.strikerrocker.vt.content.blocks.pedestal;

import io.github.strikerrocker.vt.content.BasePedestalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class PedestalBlock extends BasePedestalBlock implements SimpleWaterloggedBlock, EntityBlock {

    public PedestalBlock() {
        super();
    }

    private static PedestalBlockEntity getPedestalBE(LevelReader world, BlockPos pos) {
        return (PedestalBlockEntity) world.getBlockEntity(pos);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!worldIn.isClientSide()) {
            ItemStack heldItem = player.getItemInHand(handIn);
            PedestalBlockEntity tile = getPedestalBE(worldIn, pos);
            if (!player.isCrouching()) {
                tile.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
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
                NetworkHooks.openScreen((ServerPlayer) player, new SimpleMenuProvider((id, playerInv, playerIn) -> new PedestalContainer(id, playerInv, pos), Component.translatable("block.vanillatweaks.pedestal")), pos);
            }
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!worldIn.isClientSide()) {
            PedestalBlockEntity tile = getPedestalBE(worldIn, pos);
            tile.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.NORTH).ifPresent(itemHandler -> {
                ItemStack stack = itemHandler.getStackInSlot(0);
                if (!stack.isEmpty()) {
                    ItemEntity item = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
                    worldIn.addFreshEntity(item);
                }
            });
            worldIn.removeBlockEntity(pos);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }
}