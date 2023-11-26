package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class SelfPlanting extends ForgeFeature {
    public static ModConfigSpec.BooleanValue enableSelfPlanting;
    public static ModConfigSpec.ConfigValue<List<? extends String>> selfPlantingBlacklist;
    public static ModConfigSpec.IntValue selfPlantingInterval;

    @Override
    public void setupConfig(ModConfigSpec.Builder builder) {
        enableSelfPlanting = builder
                .translation("config.vanillatweaks:selfPlanting")
                .comment("Want seeds to auto-plant themselves when broken?")
                .define("selfPlanting", true);
        selfPlantingBlacklist = builder
                .translation("config.vanillatweaks:selfPlantingBlacklist")
                .comment("List of item ID's to blacklist for self planting")
                .defineList("selfPlantingBlacklist", List.of(), id -> id instanceof String && BuiltInRegistries.ITEM.containsKey(new ResourceLocation((String) id)));
        selfPlantingInterval = builder
                .translation("config.vanillatweaks:selfPlantingInterval")
                .comment("The number of ticks to wait to check whether a plant can be planted")
                .defineInRange("selfPlantingInterval", 100, 20, 6000);
    }
}