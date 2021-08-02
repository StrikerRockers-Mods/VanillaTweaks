package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class NoPotionShift extends Feature {
    protected static NoPotionShift INSTANCE;
    private ForgeConfigSpec.BooleanValue disablePotionShift;

    NoPotionShift() {
        INSTANCE = this;
    }

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
        public static void onPotionShiftEvent(GuiScreenEvent.PotionShiftEvent event) {
            if (NoPotionShift.INSTANCE.disablePotionShift.get())
                event.setCanceled(true);
        }
    }
}
