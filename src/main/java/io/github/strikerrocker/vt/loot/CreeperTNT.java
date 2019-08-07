package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CreeperTNT extends Feature {
    private ForgeConfigSpec.DoubleValue creeperDropTntChance;

    @Override
    public boolean usesEvents() {
        return true;
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        creeperDropTntChance = builder
                .translation("config.vanillatweaks:creeperDropTntChance")
                .comment("The chance of creepers dropping TNT, out of 10.")
                .defineInRange("creeperDropTntChance", 1D, 0, 10);
    }

    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        World world = entity.world;
        if (!world.isRemote && world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && event.getSource().getTrueSource() instanceof PlayerEntity && entity instanceof CreeperEntity && event.getSource().damageType != null && (creeperDropTntChance.get() / 10) > Math.random()) {
            event.getDrops().clear();
            entity.entityDropItem(Item.getItemFromBlock(Blocks.TNT));
        }
    }
}
