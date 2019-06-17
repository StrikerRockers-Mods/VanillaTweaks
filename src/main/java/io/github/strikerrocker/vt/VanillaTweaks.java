package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.content.ContentModule;
import io.github.strikerrocker.vt.enchantments.EnchantmentModule;
import io.github.strikerrocker.vt.loot.LootModule;
import io.github.strikerrocker.vt.misc.GuiHandler;
import io.github.strikerrocker.vt.recipes.RecipeModule;
import io.github.strikerrocker.vt.world.WorldModule;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mod(modid = VTModInfo.MODID, useMetadata = true, guiFactory = VTModInfo.PACKAGE_LOCATION + ".misc.GuiConfigFactory")
public class VanillaTweaks {
    @Mod.Instance(VTModInfo.MODID)
    public static VanillaTweaks instance;
    public static Logger logger = LogManager.getLogger(VTModInfo.MODID);
    private static List<Module> modules = new ArrayList<>();
    private Configuration config;

    public static void logInfo(String message) {
        logger.info("VanillaTweaks : " + message);
    }

    public Configuration getConfig() {
        return config;
    }

    private void registerModules() {
        modules.add(new LootModule());
        modules.add(new RecipeModule());
        modules.add(new EnchantmentModule());
        modules.add(new WorldModule());
        modules.add(new ContentModule());
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        //TODO change the config path when ready for curseforge release.
        registerModules();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        config = new Configuration(new File(event.getModConfigurationDirectory().toString() + "/VanillaTweaks2.cfg"));
        config.load();
        syncConfig();
        modules.forEach(Module::preInit);
        logInfo("Pre-Initialization Complete");
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        modules.forEach(Module::init);
        logInfo("Initialization Complete");
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        modules.forEach(Module::postInit);
        logInfo("Post-Initialization Complete");
    }

    private void syncConfig() {
        modules.forEach(module -> module.syncConfig(config));
        if (config.hasChanged())
            config.save();
        logInfo("Syncing config");
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(VTModInfo.MODID))
            syncConfig();
    }
}
