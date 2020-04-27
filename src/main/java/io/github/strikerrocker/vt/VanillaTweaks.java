package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.content.ContentModule;
import io.github.strikerrocker.vt.enchantments.EnchantmentModule;
import io.github.strikerrocker.vt.loot.LootModule;
import io.github.strikerrocker.vt.recipes.RecipeModule;
import io.github.strikerrocker.vt.tweaks.TweaksModule;
import io.github.strikerrocker.vt.world.WorldModule;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static io.github.strikerrocker.vt.VTModInfo.MODID;

@Mod(MODID)
public class VanillaTweaks {
    public static final Logger LOGGER = LogManager.getLogger();
    private static List<Module> modules = new ArrayList<>();

    public VanillaTweaks() {
        registerModules();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::configChanged);
    }

    private static void registerModules() {
        List<Function<ForgeConfigSpec.Builder, Module>> moduleBuilder = new ArrayList<>();
        Collections.addAll(moduleBuilder, ContentModule::new, EnchantmentModule::new, LootModule::new, RecipeModule::new, TweaksModule::new, WorldModule::new);
        for (Function<ForgeConfigSpec.Builder, Module> function : moduleBuilder) {
            Pair<Module, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(function);
            modules.add(specPair.getLeft());

            File cfgFile = FMLPaths.CONFIGDIR.get().resolve(MODID).resolve(specPair.getLeft().getName() + ".toml").toFile();
            if (!cfgFile.getParentFile().exists()) {
                cfgFile.getParentFile().mkdirs();
            }
            specPair.getLeft().setConfigSpec(specPair.getRight());
            ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, specPair.getRight(), cfgFile.toString());
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        modules.forEach(Module::setup);
        LOGGER.info("Setup Complete");
    }

    private void configChanged(final ModConfig.ModConfigEvent event) {
        modules.forEach(module -> module.configChanged(event));
    }
}