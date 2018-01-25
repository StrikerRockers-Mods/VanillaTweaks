package com.strikerrocker.vt;

import com.strikerrocker.vt.entities.VTEntities;
import com.strikerrocker.vt.handlers.VTConfigHandler;
import com.strikerrocker.vt.handlers.VTEventHandler;
import com.strikerrocker.vt.handlers.VTGuiHandler;
import com.strikerrocker.vt.handlers.VTSoundHandler;
import com.strikerrocker.vt.items.VTItems;
import com.strikerrocker.vt.misc.NetherPortalFix;
import com.strikerrocker.vt.misc.OreDictionaryRegistry;
import com.strikerrocker.vt.misc.VTVanillaPropertiesChanger;
import com.strikerrocker.vt.network.PacketRequestUpdatePedestal;
import com.strikerrocker.vt.network.PacketUpdatePedestal;
import com.strikerrocker.vt.proxies.VTCommonProxy;
import com.strikerrocker.vt.worldgen.NetherPocketer;
import net.minecraftforge.common.MinecraftForge;
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


@Mod(modid = vtModInfo.MOD_ID, name = vtModInfo.NAME, version = vtModInfo.VERSION, dependencies = "after:*", guiFactory = vtModInfo.PACKAGE_LOCATION + ".gui.config.VTGuiFactory")
public class vt {

    @SidedProxy(modId = vtModInfo.MOD_ID, clientSide = vtModInfo.PACKAGE_LOCATION + ".proxies.VTClientProxy", serverSide = vtModInfo.PACKAGE_LOCATION + ".proxies.VTCommonProxy")
    public static VTCommonProxy proxy;

    @Mod.Instance(vtModInfo.MOD_ID)
    public static vt instance;
    private static Logger logger;
    public static boolean baubles = false;
    public static SimpleNetworkWrapper network;
    public static void logInfo(String message) {
        logger.info("VanillaTweaks: " + message);
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        if (baubles){
            logInfo("Baubles Support Enabled");
        }
        OreDictionaryRegistry.init();
        logger = event.getModLog();
        VTConfigHandler.init(event.getSuggestedConfigurationFile());
        VTItems.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new VTGuiHandler());
        proxy.registerRenderers();
        network = NetworkRegistry.INSTANCE.newSimpleChannel(vtModInfo.MOD_ID);
        network.registerMessage(new PacketUpdatePedestal.Handler(), PacketUpdatePedestal.class, 0, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdatePedestal.Handler(), PacketRequestUpdatePedestal.class, 1, Side.SERVER);
        VTEntities.init();
        baubles = Loader.isModLoaded("Baubles") || Loader.isModLoaded("baubles");
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new VTGuiHandler());
        VTVanillaPropertiesChanger.init();
        MinecraftForge.EVENT_BUS.register(new VTEventHandler());
        MinecraftForge.EVENT_BUS.register(new VTSoundHandler());
        MinecraftForge.EVENT_BUS.register(new NetherPortalFix());
        MinecraftForge.TERRAIN_GEN_BUS.register(new NetherPocketer());
    }


    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
    }


}