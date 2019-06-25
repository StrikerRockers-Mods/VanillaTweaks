package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.content.ContentModule;
import io.github.strikerrocker.vt.enchantments.EnchantmentModule;
import io.github.strikerrocker.vt.loot.LootModule;
import io.github.strikerrocker.vt.misc.GuiHandler;
import io.github.strikerrocker.vt.recipes.RecipeModule;
import io.github.strikerrocker.vt.tweaks.TweaksModule;
import io.github.strikerrocker.vt.world.WorldModule;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = VTModInfo.MODID, useMetadata = true, guiFactory = VTModInfo.PACKAGE_LOCATION + ".misc.GuiConfigFactory")
public class VanillaTweaks {
    @Mod.Instance(VTModInfo.MODID)
    public static VanillaTweaks instance;
    public static Logger logger = LogManager.getLogger(VTModInfo.MODID);
    public static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(VTModInfo.MODID);
    public static List<Module> modules = new ArrayList<>();

    public static void logInfo(String message) {
        logger.info("VanillaTweaks : " + message);
    }

    private void registerModules() {
        modules.add(new LootModule());
        modules.add(new RecipeModule());
        modules.add(new EnchantmentModule());
        modules.add(new WorldModule());
        modules.add(new ContentModule());
        modules.add(new TweaksModule());
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        registerModules();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        modules.forEach(module -> module.setupConfig(event));
        syncConfig();
        modules.forEach(module -> module.registerPacket(network));
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
        modules.forEach(Module::syncConfig);
        logInfo("Syncing config");
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(VTModInfo.MODID))
            syncConfig();
    }
}
