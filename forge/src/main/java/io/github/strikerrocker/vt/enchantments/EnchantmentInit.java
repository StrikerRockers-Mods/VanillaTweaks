package io.github.strikerrocker.vt.enchantments;

import com.mojang.serialization.Codec;
import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

public class EnchantmentInit extends ForgeFeature {

    // Deferred Registries
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MOD_ID);
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);
    // GlobalLootModifierSerializer
    public static final RegistryObject<Codec<BlazingModifier>> BLAZING_MODIFIER = GLM.register("blazing", BlazingModifier.CODEC);
    public static final RegistryObject<Codec<SiphonModifier>> SIPHON_MODIFIER = GLM.register("siphon", SiphonModifier.CODEC);
    public static ForgeConfigSpec.BooleanValue enableHops;
    public static ForgeConfigSpec.BooleanValue enableNimble;
    public static ForgeConfigSpec.BooleanValue enableSiphon;
    public static ForgeConfigSpec.BooleanValue enableVeteran;
    public static ForgeConfigSpec.BooleanValue enableVigor;
    public static ForgeConfigSpec.BooleanValue enableHoming;
    public static ForgeConfigSpec.BooleanValue blazingTreasureOnly;
    public static ForgeConfigSpec.BooleanValue hopsTreasureOnly;
    public static final RegistryObject<Enchantment> HOPS = ENCHANTMENTS.register("hops", () -> new HopsEnchantment(() -> enableHops.get(), () -> hopsTreasureOnly.get()));
    public static ForgeConfigSpec.BooleanValue nimbleTreasureOnly;
    public static final RegistryObject<Enchantment> NIMBLE = ENCHANTMENTS.register("nimble", () -> new NimbleEnchantment(() -> enableNimble.get(), () -> nimbleTreasureOnly.get()));
    public static ForgeConfigSpec.BooleanValue siphonTreasureOnly;
    public static final RegistryObject<Enchantment> SIPHON = ENCHANTMENTS.register("siphon", () -> new SiphonEnchantment(() -> enableSiphon.get(), () -> siphonTreasureOnly.get()));
    public static ForgeConfigSpec.BooleanValue veteranTreasureOnly;
    public static final RegistryObject<Enchantment> VETERAN = ENCHANTMENTS.register("veteran", () -> new VeteranEnchantment(() -> enableVeteran.get(), () -> veteranTreasureOnly.get()));
    public static ForgeConfigSpec.BooleanValue vigorTreasureOnly;
    public static final RegistryObject<Enchantment> VIGOR = ENCHANTMENTS.register("vigor", () -> new VigorEnchantment(() -> enableVigor.get(), () -> vigorTreasureOnly.get()));
    // Configs & Enchantments
    public static ForgeConfigSpec.BooleanValue enableBlazing;
    public static final RegistryObject<Enchantment> BLAZING = ENCHANTMENTS.register("blazing", () -> new BlazingEnchantment(() -> enableBlazing.get(), () -> blazingTreasureOnly.get()));
    public static ForgeConfigSpec.BooleanValue homingTreasureOnly;
    public static final RegistryObject<Enchantment> HOMING = ENCHANTMENTS.register("homing", () -> new HomingEnchantment(() -> enableHoming.get(), () -> homingTreasureOnly.get()));

    /**
     * Adds the glowing effect to the targeting entity when using the bow
     */
    @SubscribeEvent
    public void useItem(LivingEntityUseItemEvent event) {
        if (!event.getEntity().getCommandSenderWorld().isClientSide()) {
            LivingEntity player = event.getEntity();
            int homingLvl = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.HOMING.get(), event.getItem());
            if (homingLvl > 0) {
                LivingEntity target = EnchantmentImpl.getHomingTarget(event.getEntity().getCommandSenderWorld(), player, homingLvl);
                if (target != null)
                    target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 4, 1, true, false, false));
            }
        }
    }

    /**
     * Handles the logic of Nimble & Vigor enchantment
     */
    @SubscribeEvent
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (EnchantmentInit.enableNimble.get()) {
            EnchantmentImpl.triggerNimble(event.getEntity(), EnchantmentInit.NIMBLE.get());
        }
        if (EnchantmentInit.enableVigor.get()) {
            EnchantmentImpl.triggerVigor(event.getEntity(), EnchantmentInit.VIGOR.get());
        }
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
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