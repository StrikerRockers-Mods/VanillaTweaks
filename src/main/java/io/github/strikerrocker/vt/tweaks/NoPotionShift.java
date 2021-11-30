package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class NoPotionShift extends Feature {
    static ForgeConfigSpec.BooleanValue disablePotionShift;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        disablePotionShift = builder
                .translation("config.vanillatweaks:disablePotionShift")
                .comment("Want to disable shift in GUI due to potions?")
                .define("disablePotionShift", true);
    }

    @Mod.EventBusSubscriber(modid = VanillaTweaks.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvent {
        /**
         * Disable Potion GUI shifting based on config
         */
        @SubscribeEvent
        public static void onPotionShiftEvent(ScreenEvent.PotionShiftEvent event) {
            if (NoPotionShift.disablePotionShift.get())
                event.setCanceled(true);
        }
    }
}
