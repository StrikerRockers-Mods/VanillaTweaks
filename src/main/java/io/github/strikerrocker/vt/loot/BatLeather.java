package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.misc.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BatLeather extends Feature {
    private double batLeatherDropChance;

    @Override
    public void syncConfig(Configuration config, String module) {
        batLeatherDropChance = Utils.get(config, module, "batLeatherDropChance", 1D, "The chance of bats dropping leather, out of 10.", true).setMaxValue(10).setMinValue(0).getDouble();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        World world = entity.world;
        if (!world.isRemote && world.getGameRules().getBoolean("doMobLoot") && event.getSource().getTrueSource() instanceof EntityPlayer && entity instanceof EntityBat && event.getSource().damageType != null && (batLeatherDropChance / 10) > Math.random()) {
            entity.dropItem(Items.LEATHER, 1);
        }
    }
}
