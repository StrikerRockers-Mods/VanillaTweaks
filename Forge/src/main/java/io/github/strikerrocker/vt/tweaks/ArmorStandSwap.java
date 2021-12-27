package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ArmorStandSwap extends ForgeFeature {
    private ForgeConfigSpec.BooleanValue enableArmorStandSwapping;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enableArmorStandSwapping = builder
                .translation("config.vanillatweaks:enableArmorStandSwapping")
                .comment("Want an way to swap armor with armor stand?")
                .define("enableArmorStandSwapping", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        Player player = event.getPlayer();
        if (TweaksImpl.triggerArmorStandSwap(player, event.getTarget(), enableArmorStandSwapping.get()))
            event.setCanceled(true);
    }
}