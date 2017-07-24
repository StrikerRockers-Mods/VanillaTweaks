package com.strikerrocker.vt.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class SelfPlantingProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {
    private final SelfPlantingHandler handler = new SelfPlantingHandler();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilitySelfPlanting.CAPABILITY_SELF_PLANTING;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return (T) handler;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return handler.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        handler.deserializeNBT(nbt);
    }
}
