package io.github.strikerrocker.vt;

import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.base.FabricModule;
import io.github.strikerrocker.vt.content.ContentModule;
import io.github.strikerrocker.vt.loot.LootModule;
import io.github.strikerrocker.vt.tweaks.TweaksModule;
import io.github.strikerrocker.vt.world.WorldModule;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VanillaTweaksFabric implements ModInitializer {
    public static final List<FabricModule> modules = new ArrayList<>();
    public static ModConfig config;

    static {
        ResourceConditions.register(new ResourceLocation(VanillaTweaks.MOD_ID, "custom_conditions"), VanillaTweaksFabric::customCondition);
    }

    public static boolean customCondition(JsonObject object) {
        String value = GsonHelper.getAsString(object, "value");
        return switch (value) {
            case "storage_blocks" -> config.content.enableStorageBlocks;
            case "fried_egg" -> config.content.enableFriedEgg;
            case "dynamite" -> config.content.enableDynamite;
            case "crafting_pad" -> config.content.enableCraftingPad;
            case "slime_bucket" -> config.content.enableSlimeBucket;
            case "pedestal" -> config.content.enablePedestal;
            case "better_chest" -> config.recipe.betterChestRecipe;
            case "better_repeater" -> config.recipe.betterRepeater;
            case "name_tag" -> config.recipe.nameTag;
            case "wool_to_string" -> config.recipe.woolToString;
            case "better_trapped_chest" -> config.recipe.betterTrappedChestRecipe;
            default -> false;
        };
    }

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        Collections.addAll(modules, new ContentModule(), new EnchantmentModule(), new LootModule(), new TweaksModule(), new WorldModule());
        modules.forEach(FabricModule::initialize);
        VanillaTweaks.LOGGER.info("Setup Complete");
    }
}