package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.misc.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CreeperTNT extends Feature {
    private double creeperDropTntChance;

    @Override
    public boolean usesEvents() {
        return true;
    }

    @Override
    public void syncConfig(Configuration config, String category) {
        creeperDropTntChance = Utils.get(config, category, "creeperDropTNTChance", 1D, "The chance of creepers dropping TNT, out of 10.", true).setMaxValue(10).setMinValue(0).getDouble();
    }

    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        World world = entity.world;
        if (!world.isRemote && world.getGameRules().getBoolean("doMobLoot") && event.getSource().getTrueSource() instanceof EntityPlayer && entity instanceof EntityCreeper && event.getSource().damageType != null && (creeperDropTntChance / 10) > Math.random()) {
            event.getDrops().clear();
            entity.dropItem(Item.getItemFromBlock(Blocks.TNT), 1);
        }
    }
}
