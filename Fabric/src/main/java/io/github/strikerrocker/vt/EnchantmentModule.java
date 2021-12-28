package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.base.FabricModule;
import io.github.strikerrocker.vt.enchantments.*;
import io.github.strikerrocker.vt.events.EntityEquipmentChangeCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.enchantment.Enchantment;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

public class EnchantmentModule extends FabricModule {

    public static final Enchantment BLAZING = Registry.register(Registry.ENCHANTMENT, new ResourceLocation(MOD_ID, "blazing"),
            new BlazingEnchantment(() -> VanillaTweaksFabric.config.enchanting.enableBlazing, () -> VanillaTweaksFabric.config.enchanting.blazingTreasureOnly));
    public static final Enchantment HOPS = Registry.register(Registry.ENCHANTMENT, new ResourceLocation(MOD_ID, "hops"),
            new HopsEnchantment(() -> VanillaTweaksFabric.config.enchanting.enableHops, () -> VanillaTweaksFabric.config.enchanting.hopsTreasureOnly));
    public static final Enchantment NIMBLE = Registry.register(Registry.ENCHANTMENT, new ResourceLocation(MOD_ID, "nimble"),
            new NimbleEnchantment(() -> VanillaTweaksFabric.config.enchanting.enableNimble, () -> VanillaTweaksFabric.config.enchanting.nimbleTreasureOnly));
    public static final Enchantment SIPHON = Registry.register(Registry.ENCHANTMENT, new ResourceLocation(MOD_ID, "siphon"),
            new SiphonEnchantment(() -> VanillaTweaksFabric.config.enchanting.enableSiphon, () -> VanillaTweaksFabric.config.enchanting.siphonTreasureOnly));
    public static final Enchantment VETERAN = Registry.register(Registry.ENCHANTMENT, new ResourceLocation(MOD_ID, "veteran"),
            new VeteranEnchantment(() -> VanillaTweaksFabric.config.enchanting.enableVeteran, () -> VanillaTweaksFabric.config.enchanting.veteranTreasureOnly));
    public static final Enchantment VIGOR = Registry.register(Registry.ENCHANTMENT, new ResourceLocation(MOD_ID, "vigor"),
            new VigorEnchantment(() -> VanillaTweaksFabric.config.enchanting.enableVigor, () -> VanillaTweaksFabric.config.enchanting.vigorTreasureOnly));
    public static final Enchantment HOMING = Registry.register(Registry.ENCHANTMENT, new ResourceLocation(MOD_ID, "homing"),
            new HomingEnchantment(() -> VanillaTweaksFabric.config.enchanting.enableHoming, () -> VanillaTweaksFabric.config.enchanting.homingTreasureOnly));

    @Override
    public void initialize() {
        EntityEquipmentChangeCallback.EVENT.register(((entity, slot, from, to) -> {
            //Nimble functionality
            if (VanillaTweaksFabric.config.enchanting.enableNimble) {
                EnchantmentImpl.triggerNimble(entity, NIMBLE);
            }
            //Vigor Functionality
            if (VanillaTweaksFabric.config.enchanting.enableVigor) {
                EnchantmentImpl.triggerVigor(entity, VIGOR);
            }
            //Hops functionality
            if (VanillaTweaksFabric.config.enchanting.enableHops) {
                EnchantmentImpl.triggerHops(entity, HOPS);
            }
        }));
        //Veteran functionality
        ServerTickEvents.START_WORLD_TICK.register(serverWorld -> {
            if (VanillaTweaksFabric.config.enchanting.enableVeteran && serverWorld != null && !serverWorld.isClientSide) {
                serverWorld.getEntities(EntityType.EXPERIENCE_ORB, EntitySelector.ENTITY_STILL_ALIVE)
                        .forEach(experienceOrb -> EnchantmentImpl.moveXP(experienceOrb, VETERAN));
            }
        });
        //Homing functionality
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof Arrow arrow && VanillaTweaksFabric.config.enchanting.enableHoming &&
                    arrow.getOwner() instanceof LivingEntity shooter) {
                EnchantmentImpl.triggerHoming(arrow, shooter, HOMING);
            }
        });
    }

    @Override
    public void addFeatures() {
    }
}