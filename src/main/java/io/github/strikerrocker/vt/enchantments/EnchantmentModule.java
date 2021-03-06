package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.base.Module;
import net.minecraftforge.common.ForgeConfigSpec;

public class EnchantmentModule extends Module {
    public EnchantmentModule(ForgeConfigSpec.Builder builder) {
        super("enchanting", "Do you want enchantments?", builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("enchantments", new EnchantmentFeature());
    }
}