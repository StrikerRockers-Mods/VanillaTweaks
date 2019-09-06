package io.github.strikerrocker.vt.enchantments;

import com.google.common.collect.Maps;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Map;

public class EnchantmentFeature extends Feature {
    static boolean blazing = true;
    static boolean hops = true;
    static boolean nimble = true;
    static boolean siphon = true;
    static boolean veteran = true;
    static boolean vigor = true;
    static boolean homing = true;
    private static ForgeConfigSpec.BooleanValue enableBlazing;
    private static ForgeConfigSpec.BooleanValue enableHops;
    private static ForgeConfigSpec.BooleanValue enableNimble;
    private static ForgeConfigSpec.BooleanValue enableSiphon;
    private static ForgeConfigSpec.BooleanValue enableVeteran;
    private static ForgeConfigSpec.BooleanValue enableVigor;
    private static ForgeConfigSpec.BooleanValue enableHoming;
    private static Map<String, Tuple<Enchantment, String>> enchantments = Maps.newHashMap();

    static {
        enchantments.put("blazing", new Tuple<>(new BlazingEnchantment("blazing"), "Want to smelt things when you mine them?"));
        enchantments.put("hops", new Tuple<>(new HopsEnchantment("hops"), "Want to jump more than a block high with an enchantment?"));
        enchantments.put("nimble", new Tuple<>(new NimbleEnchantment("nimble"), "Want more speed with an enchantment?"));
        enchantments.put("siphon", new Tuple<>(new SiphonEnchantment("siphon"), "Don't want the zombies stealing your items when you are mining?"));
        enchantments.put("veteran", new Tuple<>(new VeteranEnchantment("veteran"), "Want all the experience in the nearby area?"));
        enchantments.put("vigor", new Tuple<>(new VigorEnchantment("vigor"), "Want more health with an enchant?"));
        enchantments.put("homing", new Tuple<>(new HomingEnchantment("homing"), "Don't want to aim but love shooting arrows?"));
        enchantments.forEach((name, tuple) -> MinecraftForge.EVENT_BUS.register(tuple.getA()));
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void modConfig(ModConfig.ModConfigEvent event) {
        ModConfig config = event.getConfig();
        if (config.getSpec() == module.getConfigSpec()) {
            blazing = enableBlazing.get();
            hops = enableHops.get();
            nimble = enableNimble.get();
            siphon = enableSiphon.get();
            veteran = enableVeteran.get();
            vigor = enableVigor.get();
            homing = enableHoming.get();
        }
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enableBlazing = builder
                .translation("config.vanillatweaks:enableBlazing")
                .comment(getComment("blazing"))
                .define("enableBlazing", true);
        enableHops = builder
                .translation("config.vanillatweaks:enableHops")
                .comment(getComment("hops"))
                .define("enableHops", true);
        enableNimble = builder
                .translation("config.vanillatweaks:enableNimble")
                .comment(getComment("nimble"))
                .define("enableNimble", true);
        enableSiphon = builder
                .translation("config.vanillatweaks:enableSiphon")
                .comment(getComment("siphon"))
                .define("enableSiphon", true);
        enableVeteran = builder
                .translation("config.vanillatweaks:enableVeteran")
                .comment(getComment("veteran"))
                .define("enableVeteran", true);
        enableVigor = builder
                .translation("config.vanillatweaks:enableVigor")
                .comment(getComment("vigor"))
                .define("enableVigor", true);
        enableHoming = builder
                .translation("config.vanillatweaks:enableHoming")
                .comment(getComment("homing"))
                .define("enableHoming", true);
    }

    private String getComment(String name) {
        return enchantments.get(name).getB();
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerEnchantments(RegistryEvent.Register<Enchantment> registryEvent) {
            enchantments.values().forEach(triple -> registryEvent.getRegistry().register(triple.getA()));
        }
    }
}
