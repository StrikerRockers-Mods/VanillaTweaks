package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.base.ForgeModule;
import net.neoforged.neoforge.common.ModConfigSpec;

public class EnchantmentModule extends ForgeModule {
    public EnchantmentModule(ModConfigSpec.Builder builder) {
        super("enchanting", "Do you want enchantments?", builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("enchantments", new EnchantmentInit());
    }
}