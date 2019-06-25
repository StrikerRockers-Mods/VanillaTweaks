package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.base.Module;

public class EnchantmentModule extends Module {
    public EnchantmentModule() {
        super("Enchanting", "Do you want enchantments?", true);
    }

    @Override
    public void addFeatures() {
        registerFeature(new EnchantmentFeature());
    }
}
