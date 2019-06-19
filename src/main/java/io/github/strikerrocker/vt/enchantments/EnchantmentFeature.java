package io.github.strikerrocker.vt.enchantments;

import com.google.common.collect.Maps;
import io.github.strikerrocker.vt.base.Feature;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class EnchantmentFeature extends Feature {
    private Map<Enchantment, Tuple<String, String>> enchantments = Maps.newHashMap();
    private Object2BooleanOpenHashMap<Enchantment> enchantmentsToEnable = new Object2BooleanOpenHashMap<>();

    EnchantmentFeature() {
        enchantments.put(new EnchantmentBlazing("blazing"), new Tuple<>("blazing", "Want to smelt things when you mine them?"));
        enchantments.put(new EnchantmentHops("hops"), new Tuple<>("hops", "Want to jump more than a block high with an enchantment?"));
        enchantments.put(new EnchantmentNimble("nimble"), new Tuple<>("nimble", "Want more speed with an enchantment?"));
        enchantments.put(new EnchantmentSiphon("siphon"), new Tuple<>("siphon", "Don't want the zombies stealing your items when you are mining?"));
        enchantments.put(new EnchantmentVeteran("veteran"), new Tuple<>("veteran", "Want all the experience in the nearby area?"));
        enchantments.put(new EnchantmentVigor("vigor"), new Tuple<>("vigor", "Want more health with an enchant?"));
        enchantments.put(new EnchantmentHoming("homing"), new Tuple<>("homing", "Don't want to aim but love shooting arrows?"));
        enchantments.forEach((enchantment, idDesc) -> MinecraftForge.EVENT_BUS.register(enchantment));
    }

    @Override
    public void syncConfig(Configuration config, String category) {
        enchantments.forEach((enchantment, tuple) -> enchantmentsToEnable.put(enchantment, config.get(category, tuple.getFirst(), true, tuple.getSecond()).setRequiresMcRestart(true).getBoolean()));
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void registerEnchantments(RegistryEvent.Register<Enchantment> registryEvent) {
        enchantmentsToEnable.forEach((enchantment, toEnable) -> {
            if (toEnable) registryEvent.getRegistry().register(enchantment);
        });
    }
}
