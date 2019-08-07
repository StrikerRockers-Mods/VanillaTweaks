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
                .comment()
                .defineInRange("batLeatherDropChance", 1D, 0, 10);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        World world = entity.world;
        if (!world.isRemote && world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && event.getSource().getTrueSource() instanceof PlayerEntity && entity instanceof BatEntity && event.getSource().damageType != null && (batLeatherDropChance.get() / 10) > Math.random()) {
            entity.entityDropItem(Items.LEATHER);
        }
    }
}
