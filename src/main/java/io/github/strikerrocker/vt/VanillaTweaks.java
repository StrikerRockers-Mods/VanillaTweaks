package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.content.ContentModule;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadContainer;
import io.github.strikerrocker.vt.enchantments.EnchantmentModule;
import io.github.strikerrocker.vt.loot.LootModule;
import io.github.strikerrocker.vt.recipes.RecipeModule;
import io.github.strikerrocker.vt.tweaks.TweaksModule;
import io.github.strikerrocker.vt.world.WorldModule;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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

@Mod("vanillatweaks")
public class VanillaTweaks {
    public static final Logger LOGGER = LogManager.getLogger();
    public static List<Module> modules = new ArrayList<>();

    public VanillaTweaks() {
        registerModules();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addGenericListener(ContainerType.class, this::registerContainers);
        modEventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void logInfo(String message) {
        LOGGER.info("VanillaTweaks : " + message);
    }

    private void registerModules() {
        List<Function<ForgeConfigSpec.Builder, Module>> moduleBuilder = new ArrayList<>();
        Collections.addAll(moduleBuilder, ContentModule::new, EnchantmentModule::new, LootModule::new, RecipeModule::new, TweaksModule::new, WorldModule::new);
        for (Function<ForgeConfigSpec.Builder, Module> function : moduleBuilder) {
            Pair<Module, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(function);
            specPair.getLeft().configSpec = specPair.getRight();
            modules.add(specPair.getLeft());

            File cfgFile = FMLPaths.CONFIGDIR.get().resolve(VTModInfo.MODID).resolve(String.format("VanillaTweaks" + specPair.getLeft().getName() + ".toml", VTModInfo.MODID, ModConfig.Type.SERVER)).toFile();
            if (!cfgFile.getParentFile().exists()) {
                cfgFile.getParentFile().mkdirs();
            }
            ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, specPair.getRight(), cfgFile.toString());
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        modules.forEach(Module::setup);
        logInfo("Setup Complete");
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
        }
    }


}
