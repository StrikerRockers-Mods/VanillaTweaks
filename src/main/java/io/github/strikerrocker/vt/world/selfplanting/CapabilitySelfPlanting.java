package io.github.strikerrocker.vt.world.selfplanting;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Objects;

public class CapabilitySelfPlanting extends Feature {
    @CapabilityInject(ISelfPlanting.class)
    static Capability<ISelfPlanting> CAPABILITY_PLANTING = null;
    private boolean selfPlanting;

    @Override
    public void syncConfig(Configuration config, String category) {
        selfPlanting = config.get(category, "selfPlanting", true, "Dropped seeds/crops now plant themselves").setRequiresMcRestart(true).getBoolean();
    }

    @Override
    public void setup() {
        if (selfPlanting) {
            VanillaTweaks.logInfo("Registering self planting capability");
            CapabilityManager.INSTANCE.register(ISelfPlanting.class, new Capability.IStorage<ISelfPlanting>() {
                @Override
                public NBTBase writeNBT(Capability<ISelfPlanting> capability, ISelfPlanting instance, EnumFacing side) {
                    NBTTagCompound compound = new NBTTagCompound();
                    compound.setInteger(SelfPlanting.MIN_STEADY_TICKS_KEY, instance.getMinSteadyTicks());
                    compound.setInteger(SelfPlanting.STEADY_TICKS_KEY, instance.getSteadyTicks());
                    return compound;
                }

                @Override
                public void readNBT(Capability<ISelfPlanting> capability, ISelfPlanting instance, EnumFacing side, NBTBase nbt) {
                    NBTTagCompound compound = (NBTTagCompound) nbt;
                    instance.setMinSteadyTicks(compound.getInteger(SelfPlanting.MIN_STEADY_TICKS_KEY));
                    instance.setSteadyTicks(compound.getInteger(SelfPlanting.STEADY_TICKS_KEY));
                }
            }, SelfPlanting::new);
        }
    }

    @Override
    public boolean usesEvents() {
        return selfPlanting;
    }

    @SubscribeEvent
    public void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityItem) {
            event.addCapability(new ResourceLocation(VTModInfo.MODID, "planting"), new SelfPlantingProvider());
        }
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        World world = event.world;
        if (selfPlanting && !world.isRemote) {
            for (EntityItem entityItem : world.getEntities(EntityItem.class, EntitySelectors.IS_ALIVE)) {
                if (entityItem.hasCapability(CAPABILITY_PLANTING, null)) {
                    Objects.requireNonNull(entityItem.getCapability(CAPABILITY_PLANTING, null)).handlePlantingLogic(entityItem);
                }
            }
        }
    }
}
