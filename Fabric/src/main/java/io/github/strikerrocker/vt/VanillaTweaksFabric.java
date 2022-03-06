package io.github.strikerrocker.vt;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VanillaTweaksFabric implements ModInitializer {
    public static final List<FabricModule> modules = new ArrayList<>();
    public static ModConfig config;
    private static final ResourceLocation ITEM_REGISTERED = new ResourceLocation(VanillaTweaks.MOD_ID, "item_registered");

    static {
        ResourceConditions.register(ITEM_REGISTERED, VanillaTweaksFabric::itemRegistered);
    }

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        Collections.addAll(modules, new ContentModule(), new EnchantmentModule(), new LootModule(), new TweaksModule(), new WorldModule(), new RecipeModule());
        modules.forEach(FabricModule::initialize);
        VanillaTweaks.LOGGER.info("Setup Complete");
    }

    public static boolean itemRegistered(JsonObject object) {
        JsonArray array = GsonHelper.getAsJsonArray(object, "values");
        for (JsonElement element : array) {
            if (element.isJsonPrimitive() && !Registry.ITEM.containsKey(new ResourceLocation(element.getAsString()))) {
                return false;
            }
        }
        return true;
    }
}