package io.github.strikerrocker.vt.content.blocks.pedestal;

import io.github.strikerrocker.vt.content.blocks.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PedestalBlockEntity extends BlockEntity {
    long lastChangeTime;
    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            if (level != null && !level.isClientSide()) {
                lastChangeTime = level.getGameTime();
                BlockState state = level.getBlockState(worldPosition);
                level.sendBlockUpdated(worldPosition, state, state, 3);
            }
        }
    };
    private final LazyOptional<IItemHandler> holder = LazyOptional.of(() -> inventory);

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.PEDESTAL_TYPE.get(), pos, state);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos(), getBlockPos().offset(1, 2, 1));
    }

    @Override
    public CompoundTag getUpdateTag() {
        return save(new CompoundTag());
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(worldPosition, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.put("inventory", inventory.serializeNBT());
        compound.putLong("lastChangeTime", lastChangeTime);
        return super.save(compound);
    }

    @Override
    public void load(CompoundTag nbtIn) {
        inventory.deserializeNBT(nbtIn.getCompound("inventory"));
        lastChangeTime = nbtIn.getLong("lastChangeTime");
        super.load(nbtIn);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return holder.cast();
        } else return super.getCapability(cap, side);
    }
}
