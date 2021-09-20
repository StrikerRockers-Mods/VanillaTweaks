package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BatLeather extends Feature {
    private ForgeConfigSpec.DoubleValue batLeatherDropChance;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        batLeatherDropChance = builder
                .translation("config.vanillatweaks:batLeatherDropChance")
                .comment("Adds a chance for bats to drop leather when killed by a player.")
                .defineInRange("batLeatherDropChance", 1D, 0, 10);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        World world = entity.level;
        if (!world.isClientSide() && world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT) && event.getSource().getEntity() instanceof PlayerEntity && entity instanceof BatEntity && batLeatherDropChance.get() / 10 > Math.random()) {
            entity.spawnAtLocation(Items.LEATHER);
        }
    }
}
