package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.handlers.ConfigHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Optional;

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
        if (ConfigHandler.enchantments.vigor)
            registry.register(Vigor);
        if (ConfigHandler.enchantments.nimble)
            registry.register(Nimble);
        if (ConfigHandler.enchantments.hops)
            registry.register(Hops);
        if (ConfigHandler.enchantments.veteran)
            registry.register(Veteran);
        if (ConfigHandler.enchantments.siphon)
            registry.register(Siphon);
        if (ConfigHandler.enchantments.homing)
            registry.register(Homing);
        if (ConfigHandler.enchantments.blazing)
            registry.register(Blazing);
    }

    /**
     * Gets an enchantment by name
     *
     * @param name The name of the enchantment
     * @return The enchantment with the specified name
     */
    private static Optional<VTEnchantmentBase> getByName(String name) {
        return VTEnchantmentBase.vtEnchantments.stream().filter(enchantment -> enchantment.getName().replaceFirst("enchantment\\.", "").equals(name)).findFirst();
    }

    /**
     * Performs the action of the enchantment with the specified name
     *
     * @param name   The name of the enchantment
     * @param entity The entity to go along with the enchantment
     * @param event  The event to go along with the enchantment
     */
    public static void performAction(String name, Entity entity, Event event) {
        Optional<VTEnchantmentBase> vtEnchantment = getByName(name);
        vtEnchantment.ifPresent(vtEnchantmentBase -> vtEnchantmentBase.performAction(entity, event));
    }
}
