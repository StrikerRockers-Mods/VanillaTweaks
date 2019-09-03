package io.github.strikerrocker.vt.world.selfplanting;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilitySelfPlanting extends Feature {
    private static final LazyOptional<ISelfPlanting> holder = LazyOptional.of(SelfPlanting::new);
    @CapabilityInject(ISelfPlanting.class)
    private static Capability<ISelfPlanting> CAPABILITY_PLANTING = null;
    private ForgeConfigSpec.BooleanValue selfPlanting;

    @Override
    public boolean usesEvents() {
        return true;
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        selfPlanting = builder
                .translation("config.vanillatweaks:selfPlanting")
                .comment("Want seeds to auto-plant themselves when broken?")
                .define("selfPlanting", false);
    }

    @Override
    public void setup() {
        VanillaTweaks.LOGGER.info("Registering self planting capability");
        CapabilityManager.INSTANCE.register(ISelfPlanting.class, new Capability.IStorage<ISelfPlanting>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<ISelfPlanting> capability, ISelfPlanting instance, Direction side) {
                CompoundNBT compound = new CompoundNBT();
                compound.putInt(SelfPlanting.MIN_STEADY_TICKS_KEY, instance.getMinSteadyTicks());
                compound.putInt(SelfPlanting.STEADY_TICKS_KEY, instance.getSteadyTicks());
                return compound;
            }

            @Override
            public void readNBT(Capability<ISelfPlanting> capability, ISelfPlanting instance, Direction side, INBT nbt) {
                CompoundNBT compound = (CompoundNBT) nbt;
                instance.setMinSteadyTicks(compound.getInt(SelfPlanting.MIN_STEADY_TICKS_KEY));
                instance.setSteadyTicks(compound.getInt(SelfPlanting.STEADY_TICKS_KEY));
            }
        }, SelfPlanting::new);

    }

    @SubscribeEvent
    public void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof ItemEntity) {
            event.addCapability(new ResourceLocation(VTModInfo.MODID, "planting"), new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    return CapabilitySelfPlanting.CAPABILITY_PLANTING.orEmpty(cap, holder);
                }
            });
        }
    }

    @SubscribeEvent
    public void onEntityEvent(EntityEvent event) {
//        TODO Fix thisWorld world = event.getEntity().world;
//        if (selfPlanting.get() && !world.isRemote && event.getEntity() instanceof ItemEntity) {
//            ItemEntity entityItem = (ItemEntity) event.getEntity();
//            entityItem.getCapability(CAPABILITY_PLANTING).ifPresent(iSelfPlanting -> iSelfPlanting.handlePlantingLogic(entityItem));
//        }
    }
}