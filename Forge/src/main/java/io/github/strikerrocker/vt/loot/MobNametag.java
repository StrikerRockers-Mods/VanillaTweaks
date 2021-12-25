package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
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

    /**
     * Makes named mob drop nametag when killed
     */
    @SubscribeEvent
    public void onLivingDrop(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        Level world = entity.level;
        if (!world.isClientSide() && world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT) && event.getSource().getEntity() instanceof Player &&
                namedMobsDropNameTag.get() && entity.hasCustomName()) {
            ItemStack nameTag = new ItemStack(Items.NAME_TAG);
            nameTag.setHoverName(entity.getCustomName());
            nameTag.getOrCreateTag().putInt("RepairCost", 0);
            entity.spawnAtLocation(nameTag);
            entity.setCustomName(null);
        }
    }
}