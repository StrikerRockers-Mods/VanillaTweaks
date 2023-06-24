package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class SilkSpawner extends ForgeFeature {
    private ForgeConfigSpec.BooleanValue enableSilkSpawner;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
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