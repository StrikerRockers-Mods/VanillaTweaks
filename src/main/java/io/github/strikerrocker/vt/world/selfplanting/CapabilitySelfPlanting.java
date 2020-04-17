package io.github.strikerrocker.vt.world.selfplanting;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilitySelfPlanting extends Feature {
    private static final LazyOptional<ISelfPlanting> holder = LazyOptional.of(SelfPlanting::new);
    @CapabilityInject(ISelfPlanting.class)
    private static Capability<ISelfPlanting> CAPABILITY_PLANTING = null;
    private ForgeConfigSpec.BooleanValue selfPlanting;
    public ForgeConfigSpec.IntValue minTicks;

    @Override
    public boolean usesEvents() {
        return true;
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        selfPlanting = builder
                .translation("config.vanillatweaks:selfPlanting")
                .comment("Want seeds to auto-plant themselves when broken?")
                .define("selfPlanting", true);
        minTicks = builder
                .translation("config.vanillatweaks:minTicks")
                .comment("No of minimum ticks after auto-planting occurs.")
                .defineInRange("minTicks", 75, 0, Integer.MAX_VALUE);
    }

    @Override
    public void setup() {
        VanillaTweaks.LOGGER.info("Registering self planting capability");
        CapabilityManager.INSTANCE.register(ISelfPlanting.class, new Capability.IStorage<ISelfPlanting>() {
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
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (selfPlanting.get() && !event.world.isRemote()) {
            ServerWorld world = (ServerWorld) event.world;
            for (Entity entity : world.getEntities(EntityType.ITEM, EntityPredicates.IS_ALIVE)) {
                entity.getCapability(CAPABILITY_PLANTING).ifPresent(iSelfPlanting -> iSelfPlanting.handlePlantingLogic((ItemEntity) entity));
            }
        }
    }
}