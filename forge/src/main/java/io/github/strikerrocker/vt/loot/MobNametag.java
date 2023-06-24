package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MobNametag extends ForgeFeature {
    private ForgeConfigSpec.BooleanValue namedMobsDropNameTag;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        namedMobsDropNameTag = builder
                .translation("config.vanillatweaks:namedMobsDropNameTag")
                .comment("Does a nametag drop when named mob dies?")
                .define("namedMobsDropNameTag", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onLivingDrop(LivingDeathEvent event) {
        LootImpl.triggerMobNameTagDrop(event.getEntity(), event.getSource().getEntity(), namedMobsDropNameTag.get());
    }
}