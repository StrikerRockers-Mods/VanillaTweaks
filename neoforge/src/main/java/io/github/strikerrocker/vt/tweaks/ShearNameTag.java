package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class ShearNameTag extends ForgeFeature {
    private ModConfigSpec.BooleanValue shearOffNameTag;

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        ItemStack heldItem = player.getItemInHand(event.getHand());
        TweaksImpl.triggerShearNametag(player, heldItem, target, event.getLevel(), shearOffNameTag.get());
    }

    @Override
    public void setupConfig(ModConfigSpec.Builder builder) {
        shearOffNameTag = builder
                .translation("config.vanillatweaks:shearOffNameTag")
                .comment("Want to shear nametag of named entities?")
                .define("shearOffNameTag", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

}