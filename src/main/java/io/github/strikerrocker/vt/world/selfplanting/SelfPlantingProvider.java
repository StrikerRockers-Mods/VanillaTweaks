package io.github.strikerrocker.vt.world.selfplanting;

import net.minecraft.util.Direction;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SelfPlantingProvider implements ICapabilityProvider {
    private ISelfPlanting instance = CapabilitySelfPlanting.CAPABILITY_PLANTING.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilitySelfPlanting.CAPABILITY_PLANTING;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? CapabilitySelfPlanting.CAPABILITY_PLANTING.cast(instance) : null;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return null;
    }
}
