package io.github.strikerrocker.vt.enchantments;

import com.mojang.serialization.Codec;
import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

public class EnchantmentInit extends ForgeFeature {

    // Deferred Registries
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, MOD_ID);
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);
    // GlobalLootModifierSerializer
    public static final Supplier<Codec<BlazingModifier>> BLAZING_MODIFIER = GLM.register("blazing", BlazingModifier.CODEC);
    public static final Supplier<Codec<SiphonModifier>> SIPHON_MODIFIER = GLM.register("siphon", SiphonModifier.CODEC);
    public static ModConfigSpec.BooleanValue enableHops;
    public static ModConfigSpec.BooleanValue enableNimble;
    public static ModConfigSpec.BooleanValue enableSiphon;
    public static ModConfigSpec.BooleanValue enableVeteran;
    public static ModConfigSpec.BooleanValue enableVigor;
    public static ModConfigSpec.BooleanValue enableHoming;
    public static ModConfigSpec.BooleanValue blazingTreasureOnly;
    public static ModConfigSpec.BooleanValue hopsTreasureOnly;
    public static final Supplier<Enchantment> HOPS = ENCHANTMENTS.register("hops", () -> new HopsEnchantment(() -> enableHops.get(), () -> hopsTreasureOnly.get()));
    public static ModConfigSpec.BooleanValue nimbleTreasureOnly;
    public static final Supplier<Enchantment> NIMBLE = ENCHANTMENTS.register("nimble", () -> new NimbleEnchantment(() -> enableNimble.get(), () -> nimbleTreasureOnly.get()));
    public static ModConfigSpec.BooleanValue siphonTreasureOnly;
    public static final Supplier<Enchantment> SIPHON = ENCHANTMENTS.register("siphon", () -> new SiphonEnchantment(() -> enableSiphon.get(), () -> siphonTreasureOnly.get()));
    public static ModConfigSpec.BooleanValue veteranTreasureOnly;
    public static final Supplier<Enchantment> VETERAN = ENCHANTMENTS.register("veteran", () -> new VeteranEnchantment(() -> enableVeteran.get(), () -> veteranTreasureOnly.get()));
    public static ModConfigSpec.BooleanValue vigorTreasureOnly;
    public static final Supplier<Enchantment> VIGOR = ENCHANTMENTS.register("vigor", () -> new VigorEnchantment(() -> enableVigor.get(), () -> vigorTreasureOnly.get()));
    // Configs & Enchantments
    public static ModConfigSpec.BooleanValue enableBlazing;
    public static final Supplier<Enchantment> BLAZING = ENCHANTMENTS.register("blazing", () -> new BlazingEnchantment(() -> enableBlazing.get(), () -> blazingTreasureOnly.get()));
    public static ModConfigSpec.BooleanValue homingTreasureOnly;
    public static final Supplier<Enchantment> HOMING = ENCHANTMENTS.register("homing", () -> new HomingEnchantment(() -> enableHoming.get(), () -> homingTreasureOnly.get()));

    /**
     * Adds the glowing effect to the targeting entity when using the bow
     */
    @SubscribeEvent
    public void useItem(LivingEntityUseItemEvent event) {
        if (!event.getEntity().getCommandSenderWorld().isClientSide() && enableHoming.get()) {
            LivingEntity player = event.getEntity();
            int homingLvl = event.getItem().getEnchantmentLevel(EnchantmentInit.HOMING.get());
            if (homingLvl > 0) {
                LivingEntity target = EnchantmentImpl.getHomingTarget(event.getEntity().getCommandSenderWorld(), player, homingLvl);
                if (target != null)
                    target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 4, 1, true, false, false));
            }
        }
    }

    /**
     * Handles the logic of Nimble, Vigor & Hops enchantment
     */
    @SubscribeEvent
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            if (EnchantmentInit.enableNimble.get() && event.getSlot() == EquipmentSlot.FEET) {
                EnchantmentImpl.triggerNimble(event.getEntity(), EnchantmentInit.NIMBLE.get());
            }
            if (EnchantmentInit.enableVigor.get() && event.getSlot() == EquipmentSlot.CHEST) {
                EnchantmentImpl.triggerVigor(event.getEntity(), EnchantmentInit.VIGOR.get());
            }
            if (EnchantmentInit.enableHops.get() && event.getSlot() == EquipmentSlot.FEET) {
                EnchantmentImpl.triggerHops(event.getEntity(), EnchantmentInit.HOPS.get());
            }
        }
    }

    @Override
    public void setupConfig(ModConfigSpec.Builder builder) {
        enableBlazing = builder.translation("config.vanillatweaks:enableBlazing").comment("Want to smelt things when you mine them?").define("enableBlazing", true);
        enableHops = builder.translation("config.vanillatweaks:enableHops").comment("Want to jump more than a block high with an enchantment?").define("enableHops", true);
        enableNimble = builder.translation("config.vanillatweaks:enableNimble").comment("Want more speed with an enchantment?").define("enableNimble", true);
        enableSiphon = builder.translation("config.vanillatweaks:enableSiphon").comment("Don't want the zombies stealing your items when you are mining?").define("enableSiphon", true);
        enableVeteran = builder.translation("config.vanillatweaks:enableVeteran").comment("Want all the experience in the nearby area?").define("enableVeteran", true);
        enableVigor = builder.translation("config.vanillatweaks:enableVigor").comment("Want more health with an enchant?").define("enableVigor", true);
        enableHoming = builder.translation("config.vanillatweaks:enableHoming").comment("Don't want to aim but love shooting arrows?").define("enableHoming", true);
        blazingTreasureOnly = builder.comment("Want blazing enchantment to only appear in loot?").define("blazingTreasureOnly", false);
        hopsTreasureOnly = builder.comment("Want Hops enchantment to only appear in loot?").define("hopsTreasureOnly", false);
        nimbleTreasureOnly = builder.comment("Want Nimble enchantment to only appear in loot?").define("nimbleTreasureOnly", false);
        siphonTreasureOnly = builder.comment("Want Siphon enchantment to only appear in loot?").define("siphonTreasureOnly", false);
        veteranTreasureOnly = builder.comment("Want Veteran enchantment to only appear in loot?").define("veteranTreasureOnly", false);
        vigorTreasureOnly = builder.comment("Want Vigor enchantment to only appear in loot?").define("vigorTreasureOnly", false);
        homingTreasureOnly = builder.comment("Want Homing enchantment to only appear in loot?").define("homingTreasureOnly", false);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }
    /*
     * Retargets the arrow towards the targeted entity
     */

    @SubscribeEvent
    public void entityJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide() && EnchantmentInit.enableHoming.get() && event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity shooter) {
            EnchantmentImpl.triggerHoming(arrow, shooter, EnchantmentInit.HOMING.get());
        }
    }

    /**
     * Handles the logic of Hops enchantment
     */
    @SubscribeEvent
    public void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (EnchantmentInit.enableHops.get() && !event.getEntity().level().isClientSide()) {
            EnchantmentImpl.triggerHops(event.getEntity(), EnchantmentInit.HOPS.get());
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.LevelTickEvent event) {
        if (EnchantmentInit.enableVeteran.get() && event.level != null && !event.level.isClientSide()) {
            ServerLevel world = (ServerLevel) event.level;
            world.getEntities(EntityType.EXPERIENCE_ORB, EntitySelector.ENTITY_STILL_ALIVE).forEach(experienceOrb -> EnchantmentImpl.moveXP(experienceOrb, EnchantmentInit.VETERAN.get()));
        }
    }
}