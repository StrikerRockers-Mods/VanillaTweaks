package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.handlers.VTConfigHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Optional;

import static io.github.strikerrocker.vt.handlers.VTConfigHandler.siphon;
import static io.github.strikerrocker.vt.handlers.VTConfigHandler.vigor;

/**
 * Initializes Craft++'s enchantments
 */
public final class VTEnchantments {

    public static Enchantment Vigor = new EnchantmentVigor();
    public static Enchantment Nimble = new EnchantmentNimble();
    public static Enchantment Hops = new EnchantmentHops();
    public static Enchantment Veteran = new EnchantmentVeteran();
    public static Enchantment Siphon = new EnchantmentSiphon();
    public static Enchantment Homing = new EnchantmentHoming();
    public static Enchantment Blazing = new EnchantmentBlazing();

    /**
     * Registers the enchantments for Craft++
     */

    public static void init(IForgeRegistry<Enchantment> registry) {
        if (vigor)
            registry.register(Vigor);
        if (VTConfigHandler.Nimble)
            registry.register(Nimble);
        if (VTConfigHandler.Hops)
            registry.register(Hops);
        if (VTConfigHandler.Veteran)
            registry.register(Veteran);
        if (siphon)
            registry.register(Siphon);
        if (VTConfigHandler.Homing)
            registry.register(Homing);
        if (VTConfigHandler.Blazing)
            registry.register(Blazing);
    }

    /**
     * Gets an enchantment by name
     *
     * @param enchantmentName The name of the enchantment
     * @return The enchantment with the specified name
     */
    public static Optional<VTEnchantmentBase> getByName(String enchantmentName) {
        return VTEnchantmentBase.cppEnchantments.stream().filter(enchantment -> enchantment.getName().replaceFirst("enchantment\\.", "").equals(enchantmentName)).findFirst();
    }

    /**
     * Performs the action of the enchantment with the specified name
     *
     * @param enchantmentName The name of the enchantment
     * @param entity          The entity to go along with the enchantment
     * @param baseEvent       The event to go along with the enchantment
     */
    public static void performAction(String enchantmentName, Entity entity, Event baseEvent) {
        Optional<VTEnchantmentBase> cppEnchantment = getByName(enchantmentName);
        cppEnchantment.ifPresent(vtEnchantmentBase -> vtEnchantmentBase.performAction(entity, baseEvent));
    }
}
