package com.strikerrocker.vt.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilitySelfPlanting {
    @CapabilityInject(SelfPlanting.class)
    public static Capability<SelfPlanting> CAPABILITY_SELF_PLANTING = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(SelfPlanting.class, new IStorage<SelfPlanting>() {
            @Override
            public NBTBase writeNBT(Capability<SelfPlanting> capability, SelfPlanting instance, EnumFacing side) {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setInteger(SelfPlantingHandler.MINSTEADYTICKS_KEY, instance.getMinSteadyTicks());
                compound.setInteger(SelfPlantingHandler.STEADYTICKS_KEY, instance.getSteadyTicks());
                return compound;
            }

            @Override
            public void readNBT(Capability<SelfPlanting> capability, SelfPlanting instance, EnumFacing side, NBTBase nbt) {
                NBTTagCompound compound = (NBTTagCompound) nbt;
                instance.setMinSteadyTicks(compound.getInteger(SelfPlantingHandler.MINSTEADYTICKS_KEY));
                instance.setSteadyTicks(compound.getInteger(SelfPlantingHandler.STEADYTICKS_KEY));
            }

        }, SelfPlantingHandler::new);
    }

}
