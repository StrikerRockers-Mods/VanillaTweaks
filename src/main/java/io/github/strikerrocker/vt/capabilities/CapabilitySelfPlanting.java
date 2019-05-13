package io.github.strikerrocker.vt.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilitySelfPlanting {
    @CapabilityInject(ISelfPlanting.class)
    public static Capability<ISelfPlanting> CAPABILITY_PLANTING = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(ISelfPlanting.class, new Capability.IStorage<ISelfPlanting>() {
            @Override
            public NBTBase writeNBT(Capability<ISelfPlanting> capability, ISelfPlanting instance, EnumFacing side) {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setInteger(SelfPlantingHandler.MIN_STEADY_TICKS_KEY, instance.getMinSteadyTicks());
                compound.setInteger(SelfPlantingHandler.STEADY_TICKS_KEY, instance.getSteadyTicks());
                return compound;
            }

            @Override
            public void readNBT(Capability<ISelfPlanting> capability, ISelfPlanting instance, EnumFacing side, NBTBase nbt) {
                NBTTagCompound compound = (NBTTagCompound) nbt;
                instance.setMinSteadyTicks(compound.getInteger(SelfPlantingHandler.MIN_STEADY_TICKS_KEY));
                instance.setSteadyTicks(compound.getInteger(SelfPlantingHandler.STEADY_TICKS_KEY));
            }
        }, SelfPlantingHandler::new);
    }
}
