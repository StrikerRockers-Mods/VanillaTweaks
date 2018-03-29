package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Optional;

import static io.github.strikerrocker.vt.handlers.VTConfigHandler.*;

/**
 * Initializes VanillaTweaks's enchantments
 */
public final class VTEnchantments {

    public static final Enchantment Vigor = new EnchantmentVigor();
    public static final Enchantment Nimble = new EnchantmentNimble();
    public static final Enchantment Hops = new EnchantmentHops();
    private static final Enchantment Veteran = new EnchantmentVeteran();
    private static final Enchantment Siphon = new EnchantmentSiphon();
    private static final Enchantment Homing = new EnchantmentHoming();
    private static final Enchantment Blazing = new EnchantmentBlazing();

    /**
     * Registers the enchantments for VanillaTweaks
     */

    public static void init(IForgeRegistry<Enchantment> registry) {
        if (vigor)
            registry.register(Vigor);
        if (nimble)
            registry.register(Nimble);
        if (hops)
            registry.register(Hops);
        if (veteran)
            registry.register(Veteran);
        if (siphon)
            registry.register(Siphon);
        if (homing)
            registry.register(Homing);
        if (blazing)
            registry.register(Blazing);
    }

    /**
     * Gets an enchantment by name
     *
     * @param enchantmentName The name of the enchantment
     * @return The enchantment with the specified name
     */
    private static Optional<VTEnchantmentBase> getByName(String enchantmentName) {
        return VTEnchantmentBase.vtEnchantments.stream().filter(enchantment -> enchantment.getName().replaceFirst("enchantment\\.", "").equals(enchantmentName)).findFirst();
    }

    /**
     * Performs the action of the enchantment with the specified name
     *
     * @param enchantmentName The name of the enchantment
     * @param entity          The entity to go along with the enchantment
     * @param baseEvent       The event to go along with the enchantment
     */
    public static void performAction(String enchantmentName, Entity entity, Event baseEvent) {
        Optional<VTEnchantmentBase> vtEnchantment = getByName(enchantmentName);
        vtEnchantment.ifPresent(vtEnchantmentBase -> vtEnchantmentBase.performAction(entity, baseEvent));
    }
}
