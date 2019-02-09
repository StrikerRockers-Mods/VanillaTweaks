package io.github.strikerrocker.vt.capabilities;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class SelfPlantingProvider implements ICapabilityProvider {
    private ISelfPlanting instance = CapabilitySelfPlanting.PLANTING.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilitySelfPlanting.PLANTING;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == CapabilitySelfPlanting.PLANTING ? CapabilitySelfPlanting.PLANTING.cast(this.instance) : null;
    }
}