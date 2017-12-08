package com.strikerrocker.vt.enchantments;

import com.strikerrocker.vt.handlers.VTConfigHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Initializes Vanilla Tweaks's enchantments
 */
public final class VTEnchantments extends Enchantments {
    public static Enchantment Vigor = new EnchantmentVigor();
    public static Enchantment Nimble = new EnchantmentNimble();
    public static Enchantment Hops = new EnchantmentHops();
    public static Enchantment Veteran = new EnchantmentVeteran();
    public static Enchantment Siphon = new EnchantmentSiphon();
    public static Enchantment Homing = new EnchantmentHoming();


    public static void VTEnchantments(IForgeRegistry<Enchantment> registry) {
        if (VTConfigHandler.vigor) {
            registry.register(Vigor);
        }
        if (VTConfigHandler.Nimble) {
            registry.register(Nimble);
        }

        if (VTConfigHandler.Homing) {
            registry.register(Homing);
        }

        if (VTConfigHandler.Hops) {
            registry.register(Hops);
        }

        if (VTConfigHandler.siphon) {
            registry.register(Siphon);
        }
        if (VTConfigHandler.Veteran) {
            registry.register(Veteran);
        }

    }

}
