package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ModifiedItemGroups extends Feature {
    protected ForgeConfigSpec.BooleanValue commandBlockInRedstone;
    protected ForgeConfigSpec.BooleanValue dragonEggInDecorations;

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
    public void setup() {
        if (commandBlockInRedstone.get())
            ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.COMMAND_BLOCK, ItemGroup.REDSTONE, "group");
        if (dragonEggInDecorations.get())
            ObfuscationReflectionHelper.setPrivateValue(Item.class, Items.DRAGON_EGG, ItemGroup.DECORATIONS, "group");
    }
}
