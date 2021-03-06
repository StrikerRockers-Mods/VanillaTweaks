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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.strikerrocker.vt.VTModInfo.MODID;

@Mod(MODID)
public class VanillaTweaks {
    public static final Logger LOGGER = LogManager.getLogger();
    private static final List<Module> modules = new ArrayList<>();

    public VanillaTweaks() {
        registerModules();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::configChanged);
    }

    private static void registerModules() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("VanillaTweaks");
        Collections.addAll(modules,
                new ContentModule(builder),
                new EnchantmentModule(builder),
                new LootModule(builder),
                new RecipeModule(builder),
                new TweaksModule(builder),
                new WorldModule(builder));
        modules.forEach(Module::setupConfig);
        builder.pop();
        ForgeConfigSpec spec = builder.build();
        modules.forEach(module -> module.setConfigSpec(spec));
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec);
        /*for (Function<ForgeConfigSpec.Builder, Module> function : moduleBuilder) {
            Pair<Module, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(function);
            modules.add(specPair.getLeft());
            String cfgStr = "vanillatweaks_" + specPair.getLeft().getName() + ".toml";
            specPair.getLeft().setConfigSpec(specPair.getRight());
            ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, specPair.getRight(), cfgStr);
        }*/
    }

    private void setup(final FMLCommonSetupEvent event) {
        modules.forEach(Module::setup);
        LOGGER.info("Setup Complete");
    }

    private void configChanged(final ModConfig.ModConfigEvent event) {
        modules.forEach(module -> module.configChanged(event));
    }
}