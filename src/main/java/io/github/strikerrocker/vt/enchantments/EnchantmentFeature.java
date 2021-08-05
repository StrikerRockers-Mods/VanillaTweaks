package io.github.strikerrocker.vt.enchantments;

import com.google.common.collect.Maps;
import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.enchantments.homing.HomingEnchantment;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

public class EnchantmentFeature extends Feature {
    protected static final Map<String, Tuple<Enchantment, String>> enchantments = Maps.newHashMap();
    public static ForgeConfigSpec.BooleanValue enableBlazing;
    public static ForgeConfigSpec.BooleanValue enableHops;
    public static ForgeConfigSpec.BooleanValue enableNimble;
    public static ForgeConfigSpec.BooleanValue enableSiphon;
    public static ForgeConfigSpec.BooleanValue enableVeteran;
    public static ForgeConfigSpec.BooleanValue enableVigor;
    public static ForgeConfigSpec.BooleanValue enableHoming;

    static {
        enchantments.put("blazing", new Tuple<>(new BlazingEnchantment(), "Want to smelt things when you mine them?"));
        enchantments.put("hops", new Tuple<>(new HopsEnchantment(), "Want to jump more than a block high with an enchantment?"));
        enchantments.put("nimble", new Tuple<>(new NimbleEnchantment(), "Want more speed with an enchantment?"));
        enchantments.put("siphon", new Tuple<>(new SiphonEnchantment(), "Don't want the zombies stealing your items when you are mining?"));
        enchantments.put("veteran", new Tuple<>(new VeteranEnchantment(), "Want all the experience in the nearby area?"));
        enchantments.put("vigor", new Tuple<>(new VigorEnchantment(), "Want more health with an enchant?"));
        enchantments.put("homing", new Tuple<>(new HomingEnchantment(), "Don't want to aim but love shooting arrows?"));
        enchantments.forEach((name, tuple) -> MinecraftForge.EVENT_BUS.register(tuple.getA()));
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

        @SubscribeEvent
        public static void registerModifierSerializers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
            event.getRegistry().register(new BlazingModifier.Serializer().setRegistryName(VanillaTweaks.MOD_ID, "blazing"));
            event.getRegistry().register(new SiphonModifier.Serializer().setRegistryName(VanillaTweaks.MOD_ID, "siphon"));
        }
    }
}
