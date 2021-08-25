package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

public class EnchantmentInit extends Feature {
    // Deferred Registries
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MOD_ID);
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, MOD_ID);
    // Enchantments
    public static final RegistryObject<Enchantment> BLAZING = ENCHANTMENTS.register("blazing", BlazingEnchantment::new);
    public static final RegistryObject<Enchantment> HOPS = ENCHANTMENTS.register("hops", HopsEnchantment::new);
    public static final RegistryObject<Enchantment> NIMBLE = ENCHANTMENTS.register("nimble", NimbleEnchantment::new);
    public static final RegistryObject<Enchantment> SIPHON = ENCHANTMENTS.register("siphon", SiphonEnchantment::new);
    public static final RegistryObject<Enchantment> VETERAN = ENCHANTMENTS.register("veteran", VeteranEnchantment::new);
    public static final RegistryObject<Enchantment> VIGOR = ENCHANTMENTS.register("vigor", VigorEnchantment::new);
    public static final RegistryObject<Enchantment> HOMING = ENCHANTMENTS.register("homing", HomingEnchantment::new);
    // GlobalLootModifierSerializer
    public static final RegistryObject<GlobalLootModifierSerializer<?>> BLAZING_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register("blazing",
            BlazingModifier.Serializer::new);
    public static final RegistryObject<GlobalLootModifierSerializer<?>> SIPHON_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register("siphon",
            BlazingModifier.Serializer::new);
    // Configs
    public static ForgeConfigSpec.BooleanValue enableBlazing;
    public static ForgeConfigSpec.BooleanValue enableHops;
    public static ForgeConfigSpec.BooleanValue enableNimble;
    public static ForgeConfigSpec.BooleanValue enableSiphon;
    public static ForgeConfigSpec.BooleanValue enableVeteran;
    public static ForgeConfigSpec.BooleanValue enableVigor;
    public static ForgeConfigSpec.BooleanValue enableHoming;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enableBlazing = builder
                .translation("config.vanillatweaks:enableBlazing")
                .comment("Want to smelt things when you mine them?")
                .define("enableBlazing", true);
        enableHops = builder
                .translation("config.vanillatweaks:enableHops")
                .comment("Want to jump more than a block high with an enchantment?")
                .define("enableHops", true);
        enableNimble = builder
                .translation("config.vanillatweaks:enableNimble")
                .comment("Want more speed with an enchantment?")
                .define("enableNimble", true);
        enableSiphon = builder
                .translation("config.vanillatweaks:enableSiphon")
                .comment("Don't want the zombies stealing your items when you are mining?")
                .define("enableSiphon", true);
        enableVeteran = builder
                .translation("config.vanillatweaks:enableVeteran")
                .comment("Want all the experience in the nearby area?")
                .define("enableVeteran", true);
        enableVigor = builder
                .translation("config.vanillatweaks:enableVigor")
                .comment("Want more health with an enchant?")
                .define("enableVigor", true);
        enableHoming = builder
                .translation("config.vanillatweaks:enableHoming")
                .comment("Don't want to aim but love shooting arrows?")
                .define("enableHoming", true);
    }
}
