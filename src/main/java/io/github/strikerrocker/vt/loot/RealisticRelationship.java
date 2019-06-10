package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.misc.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class RealisticRelationship extends Feature {
    private boolean realisticRelationship;

    RealisticRelationship(Module module) {
        super(module);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @Override
    public void syncConfig(Configuration config, String module) {
        realisticRelationship = Utils.get(config, module, "realisticRelationship", true, "Is realistic predator/prey relationships activated?");
    }

    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        List<EntityItem> dropsCopy = new ArrayList<>(event.getDrops());
        for (EntityItem dropEntity : dropsCopy) {
            ItemStack dropItem = dropEntity.getItem();
            if (event.getSource().getImmediateSource() != null) {
                Item drop = dropItem.getItem();
                Entity source = event.getSource().getImmediateSource();
                if (realisticRelationship && (source instanceof EntityWolf && entity instanceof EntitySheep && (drop == Items.MUTTON || drop == Items.COOKED_MUTTON)) || (source instanceof EntityOcelot && entity instanceof EntityChicken && (drop == Items.CHICKEN || drop == Items.COOKED_CHICKEN))) {
                    event.getDrops().remove(dropEntity);
                }
            }
        }
    }
}
