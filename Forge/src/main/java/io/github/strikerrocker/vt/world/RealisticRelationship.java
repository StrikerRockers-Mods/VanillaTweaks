package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class RealisticRelationship extends ForgeFeature {
    BiPredicate<Entity, Entity> wolfSheep = (source, entity) -> source instanceof Wolf && entity instanceof Sheep;
    BiPredicate<Entity, Entity> foxChicken = (source, entity) -> source instanceof Fox && entity instanceof Chicken;
    BiPredicate<Entity, Entity> ocelotChicken = (source, entity) -> source instanceof Ocelot && entity instanceof Chicken;
    BiPredicate<Entity, Entity> foxRabbit = (source, entity) -> source instanceof Fox && entity instanceof Rabbit;
    BiPredicate<Entity, Entity> wolfRabbit = (source, entity) -> source instanceof Wolf && entity instanceof Rabbit;
    private ForgeConfigSpec.BooleanValue enableRealisticRelationship;

    @Override
    public boolean usesEvents() {
        return true;
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enableRealisticRelationship = builder
                .translation("config.vanillatweaks:realisticRelationship")
                .comment("Is realistic predator/prey relationships activated?")
                .define("realisticRelationship", true);
    }

    /**
     * Removes meat drops if killed by the predator
     */
    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        //CME If not copied to a new list
        List<ItemEntity> dropsCopy = new ArrayList<>(event.getDrops());
        for (ItemEntity dropEntity : dropsCopy) {
            ItemStack dropItem = dropEntity.getItem();
            if (event.getSource().getDirectEntity() != null) {
                Item drop = dropItem.getItem();
                Entity source = event.getSource().getDirectEntity();
                if (enableRealisticRelationship.get()) {
                    if (wolfSheep.test(source, entity) && (drop == Items.MUTTON || drop == Items.COOKED_MUTTON))
                        event.getDrops().remove(dropEntity);
                    if ((ocelotChicken.test(source, entity) || foxChicken.test(source, entity)) && (drop == Items.CHICKEN || drop == Items.COOKED_CHICKEN))
                        event.getDrops().remove(dropEntity);
                    if ((foxRabbit.test(source, entity) || wolfRabbit.test(source, entity)) && (drop == Items.RABBIT || drop == Items.COOKED_RABBIT))
                        event.getDrops().remove(dropEntity);
                }
            }
        }
    }
}
