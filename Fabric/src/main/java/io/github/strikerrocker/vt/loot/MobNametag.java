package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.events.LivingEntityDeathCallback;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class MobNametag extends Feature {

    /**
     * Makes named mob drop nametag when killed
     */
    @Override
    public void initialize() {
        LivingEntityDeathCallback.EVENT.register((livingEntity, damageSource) -> {
            Level world = livingEntity.level;
            if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT) && damageSource.getEntity() instanceof Player &&
                    VanillaTweaksFabric.config.loot.namedMobsDropNameTag && livingEntity.hasCustomName()) {
                ItemStack nameTag = new ItemStack(Items.NAME_TAG);
                nameTag.setHoverName(livingEntity.getCustomName());
                nameTag.getOrCreateTag().putInt("RepairCost", 0);
                livingEntity.spawnAtLocation(nameTag);
                livingEntity.setCustomName(null);
            }
        });
    }
}