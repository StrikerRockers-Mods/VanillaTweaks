package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.events.LivingEntityDeathCallback;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class BatLeather extends Feature {

    /**
     * Adds the chance of leather dropping when bat is killed
     */
    @Override
    public void initialize() {
        LivingEntityDeathCallback.EVENT.register((livingEntity, damageSource) -> {
            Level world = livingEntity.level;
            if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT) && damageSource.getEntity() instanceof Player &&
                    livingEntity instanceof Bat && world.random.nextInt(10) <= VanillaTweaksFabric.config.loot.batLeatherDropChance)
                livingEntity.spawnAtLocation(Items.LEATHER);
        });
    }
}