package io.github.strikerrocker.vt.content.blocks.pedestal;

import io.github.strikerrocker.vt.content.blocks.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PedestalTileEntity extends TileEntity {
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

    public PedestalTileEntity() {
        super(Blocks.PEDESTAL_TYPE);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getBlockPos(), getBlockPos().offset(1, 2, 1));
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(getBlockState(), pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        load(state, tag);
        level.sendBlockUpdated(worldPosition, state, state, 2);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.put("inventory", inventory.serializeNBT());
        compound.putLong("lastChangeTime", lastChangeTime);
        return super.save(compound);
    }

    @Override
    public void load(BlockState stateIn, CompoundNBT nbtIn) {
        inventory.deserializeNBT(nbtIn.getCompound("inventory"));
        lastChangeTime = nbtIn.getLong("lastChangeTime");
        super.load(stateIn, nbtIn);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return holder.cast();
        } else return super.getCapability(cap, side);
    }
}
