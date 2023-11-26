package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class MobNametag extends ForgeFeature {
    private ModConfigSpec.BooleanValue namedMobsDropNameTag;

    @Override
    public void setupConfig(ModConfigSpec.Builder builder) {
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