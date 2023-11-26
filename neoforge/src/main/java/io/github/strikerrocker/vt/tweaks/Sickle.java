package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.level.BlockEvent;

public class Sickle extends ForgeFeature {
    private ModConfigSpec.BooleanValue hoeActsAsSickle;

    @Override
    public void setupConfig(ModConfigSpec.Builder builder) {
        hoeActsAsSickle = builder
                .translation("config.vanillatweaks:hoeActsAsSickle")
                .comment("Want hoe to act like a sickle?")
                .define("hoeActsAsSickle", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        TweaksImpl.triggerSickle(player, player.getItemInHand(player.getUsedItemHand()), player.getCommandSenderWorld(), event.getPos(), event.getState(), hoeActsAsSickle.get());
    }
}