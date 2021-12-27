package io.github.strikerrocker.vt.loot;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class LootImpl {

    /**
     * Makes named mob drop nametag when killed
     */
    public static void triggerMobNameTagDrop(Entity entity, Entity source, boolean config) {
        Level level = entity.getLevel();
        if (config && !level.isClientSide() && level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT) && source instanceof Player && entity.hasCustomName()) {
            ItemStack nameTag = new ItemStack(Items.NAME_TAG);
            nameTag.setHoverName(entity.getCustomName());
            nameTag.getOrCreateTag().putInt("RepairCost", 0);
            entity.spawnAtLocation(nameTag);
            entity.setCustomName(null);
        }
    }

    /**
     * Adds the chance of leather dropping when bat is killed
     */
    public static void triggerBatLeatherDrop(Entity entity, Entity source, Double config) {
        Level level = entity.getLevel();
        if (!level.isClientSide() && level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)
                && entity instanceof Bat && config / 10 > Math.random() && source instanceof Player) {
            entity.spawnAtLocation(Items.LEATHER);
        }
    }
}
