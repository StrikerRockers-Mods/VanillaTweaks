package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.base.FabricModule;
import io.github.strikerrocker.vt.content.ContentModule;
import io.github.strikerrocker.vt.enchantments.EnchantmentModule;
import io.github.strikerrocker.vt.loot.LootModule;
import io.github.strikerrocker.vt.tweaks.TweaksModule;
import io.github.strikerrocker.vt.world.WorldModule;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VanillaTweaksFabric implements ModInitializer {
    public static final List<FabricModule> modules = new ArrayList<>();
    public static ModConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        Collections.addAll(modules, new ContentModule(), new EnchantmentModule(), new LootModule(), new TweaksModule(), new WorldModule(), new RecipeModule());
        modules.forEach(FabricModule::initialize);
        VanillaTweaks.LOGGER.info("Setup Complete");
    }
}