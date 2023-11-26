package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class SilkSpawner extends ForgeFeature {
    private ModConfigSpec.BooleanValue enableSilkSpawner;

    @Override
    public void setupConfig(ModConfigSpec.Builder builder) {
        enableSilkSpawner = builder.translation("config.vanillatweaks:enableSilkSpawner")
                .comment("Want the ability to move spawners with silk touch?")
                .define("enableSilkSpawner", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack stack = player.getItemInHand(player.getUsedItemHand());
            TweaksImpl.triggerSpawnerPlacement(event.getEntity().getCommandSenderWorld(), event.getPos(), stack, enableSilkSpawner.get());
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBreak(BlockEvent.BreakEvent event) {
        if (TweaksImpl.triggerSpawnerBreak(event.getPlayer().level(), event.getPos(), event.getState(), event.getPlayer(), enableSilkSpawner.get(), true)) {
            event.setExpToDrop(0);
            event.setCanceled(true);
        }
    }

    @Mod.EventBusSubscriber(modid = VanillaTweaks.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onToolTipEvent(ItemTooltipEvent event) {
            TweaksClientImpl.addSilkSpawnerTooltip(event.getItemStack(), event.getToolTip());
        }
    }
}