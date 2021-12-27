package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Sickle extends ForgeFeature {
    private ForgeConfigSpec.BooleanValue hoeActsAsSickle;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
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
        //TODO not working
        TweaksImpl.triggerSickle(player, player.getItemInHand(player.getUsedItemHand()), player.getCommandSenderWorld(), event.getPos(), event.getState(), hoeActsAsSickle.get());
    }
}
