package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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

    /**
     * Adds the chance of leather dropping when bat is killed
     */
    @SubscribeEvent
    public void onLivingDrop(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        Level world = entity.level;
        if (!world.isClientSide() && world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT) && event.getSource().getEntity() instanceof Player &&
                entity instanceof Bat && batLeatherDropChance.get() / 10 > Math.random()) {
            entity.spawnAtLocation(Items.LEATHER);
        }
    }
}
