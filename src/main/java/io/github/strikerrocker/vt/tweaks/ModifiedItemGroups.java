package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;

public class ModifiedItemGroups extends Feature {
    private ForgeConfigSpec.BooleanValue commandBlockInRedstone;
    private ForgeConfigSpec.BooleanValue dragonEggInDecorations;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        commandBlockInRedstone = builder
                .translation("config.vanillatweaks:commandBlockInRedstone")
                .comment("Want command block to appear in redstone tab?")
                .define("commandBlockInRedstone", true);
        dragonEggInDecorations = builder
                .translation("config.vanillatweaks:dragonEggInDecorations")
                .comment("Want dragon egg to appear in decorations tab?")
                .define("dragonEggInDecorations", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @Override
    public void configChanged(ModConfig.ModConfigEvent event) {
        if (event.getConfig().getSpec() == module.getConfigSpec()) {
            for (Item item : Registry.ITEM) {
                String name = item.getRegistryName().getPath();
                if (commandBlockInRedstone.get() && name.contains("command_block")) {
                    ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.COMMAND_BLOCK, ItemGroup.REDSTONE, "field_77701_a");
                    ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.CHAIN_COMMAND_BLOCK, ItemGroup.REDSTONE, "field_77701_a");
                    ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.REPEATING_COMMAND_BLOCK, ItemGroup.REDSTONE, "field_77701_a");
                    ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.COMMAND_BLOCK_MINECART, ItemGroup.REDSTONE, "field_77701_a");
                    ObfuscationReflectionHelper.setPrivateValue(Item.class, item, ItemGroup.REDSTONE, "field_77701_a");
                }
                if (dragonEggInDecorations.get() && name.contains("dragon_egg")) {
                    ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.DRAGON_EGG, ItemGroup.DECORATIONS, "field_77701_a");
                    ObfuscationReflectionHelper.setPrivateValue(Item.class, item, ItemGroup.DECORATIONS, "field_77701_a");
                }
            }
        }
    }
}