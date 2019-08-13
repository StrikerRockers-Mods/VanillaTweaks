package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class RealisticRelationship extends Feature {
    private ForgeConfigSpec.BooleanValue realisticRelationship;

    @Override
    public boolean usesEvents() {
        return true;
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        realisticRelationship = builder
                .translation("config.vanillatweaks:realisticRelationship")
                .comment("Is realistic predator/prey relationships activated?")
                .define("realisticRelationship", true);
    }

    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        List<ItemEntity> dropsCopy = new ArrayList<>(event.getDrops());
        for (ItemEntity dropEntity : dropsCopy) {
            ItemStack dropItem = dropEntity.getItem();
            if (event.getSource().getImmediateSource() != null) {
                Item drop = dropItem.getItem();
                Entity source = event.getSource().getImmediateSource();
                if (realisticRelationship.get() && (source instanceof WolfEntity && entity instanceof SheepEntity
                        && (drop == Items.MUTTON || drop == Items.COOKED_MUTTON)) || (source instanceof WolfEntity && entity instanceof ChickenEntity && (drop == Items.CHICKEN || drop == Items.COOKED_CHICKEN))) {
                    event.getDrops().remove(dropEntity);
                }
            }
        }
    }
}
