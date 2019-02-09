package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.capabilities.CapabilitySelfPlanting;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(modid = VTModInfo.MODID, useMetadata = true)
public class VT {

    @SidedProxy(modId = VTModInfo.MODID, clientSide = VTModInfo.PACKAGE_LOCATION + ".proxies.VTClientProxy", serverSide = VTModInfo.PACKAGE_LOCATION + ".proxies.VTCommonProxy")
    public static VTCommonProxy proxy;

    @Mod.Instance(VTModInfo.MODID)
    public static VT instance;
    public static boolean baubles = false;
    public static SimpleNetworkWrapper network;
    public static Logger logger = LogManager.getLogger(VTModInfo.MODID);

    public static void logInfo(String message) {
        logger.info("VanillaTweaks : " + message);
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        baubles = Loader.isModLoaded("Baubles") || Loader.isModLoaded("baubles");
        if (baubles) logInfo("Baubles Support Enabled");
        VTItems.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new VTGuiHandler());
        network = NetworkRegistry.INSTANCE.newSimpleChannel(VTModInfo.MODID);
        network.registerMessage(new PacketUpdatePedestal.Handler(), PacketUpdatePedestal.class, 0, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdatePedestal.Handler(), PacketRequestUpdatePedestal.class, 1, Side.SERVER);
        logInfo("Pre-Initialization Complete");
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new VTGuiHandler());
        VTVanillaPropertiesChanger.init();
        CapabilitySelfPlanting.register();
        proxy.init();
        logInfo("Initialization Complete");
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        logInfo("Post-Initialization Complete");
    }
}