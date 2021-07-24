package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
            if (event.getSource().getDirectEntity() != null) {
                Item drop = dropItem.getItem();
                Entity source = event.getSource().getDirectEntity();
                if (realisticRelationship.get() &&
                        ((source instanceof Wolf && entity instanceof Sheep && (drop == Items.MUTTON || drop == Items.COOKED_MUTTON))
                                || (source instanceof Ocelot && entity instanceof Chicken && (drop == Items.CHICKEN || drop == Items.COOKED_CHICKEN)))) {
                    event.getDrops().remove(dropEntity);
                }
            }
        }
    }
}
