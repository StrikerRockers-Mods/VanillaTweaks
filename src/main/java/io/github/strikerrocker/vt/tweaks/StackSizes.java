package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;

public class StackSizes extends Feature {

    private ForgeConfigSpec.IntValue boatStackSize;
    private ForgeConfigSpec.IntValue enderPearlStackSize;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        boatStackSize = builder
                .translation("config.vanillatweaks:boatStackSize")
                .comment("Stack size for boat")
                .defineInRange("boatStackSize", 4, 1, 64);
        enderPearlStackSize = builder
                .translation("config.vanillatweaks:enderPearlStackSize")
                .comment("Stack size for ender pearls")
                .defineInRange("enderPearlStackSize", 64, 1, 64);

    }

    @Override
    public void configChanged(ModConfig.ModConfigEvent event) {
        if (event.getConfig().getSpec() == module.getConfigSpec()) {
            ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.ACACIA_BOAT, boatStackSize.get(), "maxStackSize");
            ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.BIRCH_BOAT, boatStackSize.get(), "maxStackSize");
            ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.OAK_BOAT, boatStackSize.get(), "maxStackSize");
            ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.DARK_OAK_BOAT, boatStackSize.get(), "maxStackSize");
            ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.JUNGLE_BOAT, boatStackSize.get(), "maxStackSize");
            ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.SPRUCE_BOAT, boatStackSize.get(), "maxStackSize");
            ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.ENDER_PEARL, enderPearlStackSize.get(), "maxStackSize");
        }
    }
}