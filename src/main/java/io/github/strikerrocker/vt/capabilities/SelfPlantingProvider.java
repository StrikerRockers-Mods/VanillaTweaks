package io.github.strikerrocker.vt.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class SelfPlantingProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {
    private final SelfPlantingHandler handler = new SelfPlantingHandler();
    private ISelfPlanting instance = CapabilitySelfPlanting.PLANTING.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilitySelfPlanting.PLANTING;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilitySelfPlanting.PLANTING) {
            return CapabilitySelfPlanting.PLANTING.cast(instance);
        }
        return null;
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