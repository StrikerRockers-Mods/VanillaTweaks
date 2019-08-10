package io.github.strikerrocker.vt.world.selfplanting;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;

public class CapabilitySelfPlanting extends Feature {
    @CapabilityInject(ISelfPlanting.class)
    static Capability<ISelfPlanting> CAPABILITY_PLANTING = null;
    private ForgeConfigSpec.BooleanValue selfPlanting;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        selfPlanting = builder
                .translation("config.vanillatweaks:selfPlanting")
                .comment("Want seeds to auto-plant themselves when broken?")
                .define("selfPlanting", true);
    }

    @Override
    public void setup() {
        if (selfPlanting.get()) {
            VanillaTweaks.logInfo("Registering self planting capability");
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
    }

    @Override
    public boolean usesEvents() {
        return selfPlanting.get();
    }

    @SubscribeEvent
    public void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof ItemEntity) {
            event.addCapability(new ResourceLocation(VTModInfo.MODID, "planting"), new SelfPlantingProvider());
        }
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        World world = event.world;
        if (selfPlanting.get() && !world.isRemote) {
            for (ItemEntity entityItem : world.getEntities(ItemEntity.class, EntityPredicates.IS_ALIVE)) {
                if (entityItem.getCapability(CAPABILITY_PLANTING).isPresent()) {
                    entityItem.getCapability(CAPABILITY_PLANTING).ifPresent(iSelfPlanting -> iSelfPlanting.handlePlantingLogic(entityItem));
                }
            }
        }
    }
}
