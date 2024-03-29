package io.github.strikerrocker.vt.world;

import io.github.strikerrocker.vt.base.ForgeFeature;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class SelfPlanting extends ForgeFeature {
    public static ForgeConfigSpec.BooleanValue enableSelfPlanting;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> selfPlantingBlacklist;
    public static ForgeConfigSpec.IntValue selfPlantingInterval;

    @Override
    public boolean usesEvents() {
        return true;
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enableSelfPlanting = builder
                .translation("config.vanillatweaks:selfPlanting")
                .comment("Want seeds to auto-plant themselves when broken?")
                .define("selfPlanting", true);
        selfPlantingBlacklist = builder
                .translation("config.vanillatweaks:selfPlantingBlacklist")
                .comment("List of item ID's to blacklist for self planting")
                .defineList("selfPlantingBlacklist", List.of(), id -> id instanceof String && ForgeRegistries.ITEMS.containsKey(new ResourceLocation((String) id)));
        selfPlantingInterval = builder
                .translation("config.vanillatweaks:selfPlantingInterval")
                .comment("The number of ticks to wait to check whether a plant can be planted")
                .defineInRange("selfPlantingInterval", 100, 20, 6000);
    }
}