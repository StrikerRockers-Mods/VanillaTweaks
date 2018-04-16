package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.capabilities.CapabilitySelfPlanting;
import io.github.strikerrocker.vt.entities.VTEntities;
import io.github.strikerrocker.vt.handlers.OreDictionaryHandler;
import io.github.strikerrocker.vt.handlers.VTConfigHandler;
import io.github.strikerrocker.vt.handlers.VTGuiHandler;
import io.github.strikerrocker.vt.items.VTItems;
import io.github.strikerrocker.vt.misc.VTVanillaPropertiesChanger;
import io.github.strikerrocker.vt.network.PacketRequestUpdatePedestal;
import io.github.strikerrocker.vt.network.PacketUpdatePedestal;
import io.github.strikerrocker.vt.proxies.VTCommonProxy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;


@Mod(modid = vtModInfo.MOD_ID, name = vtModInfo.NAME, version = vtModInfo.VERSION, guiFactory = vtModInfo.PACKAGE_LOCATION + ".gui.config.VTGuiFactory")
public class vt
{

    @SidedProxy(modId = vtModInfo.MOD_ID, clientSide = vtModInfo.PACKAGE_LOCATION + ".proxies.VTClientProxy", serverSide = vtModInfo.PACKAGE_LOCATION + ".proxies.VTCommonProxy")
    public static VTCommonProxy proxy;

    @Mod.Instance(vtModInfo.MOD_ID)
    public static vt instance;
    public static boolean baubles = false;
    public static SimpleNetworkWrapper network;
    private static Logger logger;

    public static void logInfo(String message) {
        logger.info("VanillaTweaks : " + message);
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        baubles = Loader.isModLoaded("Baubles") || Loader.isModLoaded("baubles");
        if (baubles) logInfo("Baubles Support Enabled");
        VTConfigHandler.init(event.getSuggestedConfigurationFile());
        VTItems.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new VTGuiHandler());
        proxy.registerRenderer();
        network = NetworkRegistry.INSTANCE.newSimpleChannel(vtModInfo.MOD_ID);
        network.registerMessage(new PacketUpdatePedestal.Handler(), PacketUpdatePedestal.class, 0, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdatePedestal.Handler(), PacketRequestUpdatePedestal.class, 1, Side.SERVER);
        VTEntities.init();
        logInfo("Pre-Initialization Complete");
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new VTGuiHandler());
        OreDictionaryHandler.init();
        VTVanillaPropertiesChanger.init();
        CapabilitySelfPlanting.register();
        proxy.init(event);
        logInfo("Initialization Complete");
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        logInfo("Post-Initialization Complete");
    }
}