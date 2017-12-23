package com.strikerrocker.vt;

import com.strikerrocker.vt.blocks.VTBlocks;
import com.strikerrocker.vt.enchantments.VTEnchantments;
import com.strikerrocker.vt.entities.VTEntities;
import com.strikerrocker.vt.handlers.*;
import com.strikerrocker.vt.items.ItemArmor;
import com.strikerrocker.vt.items.VTItems;
import com.strikerrocker.vt.misc.VTVanillaPropertiesChanger;
import com.strikerrocker.vt.network.PacketRequestUpdatePedestal;
import com.strikerrocker.vt.network.PacketUpdatePedestal;
import com.strikerrocker.vt.proxies.VTCommonProxy;
import com.strikerrocker.vt.recipes.VTRecipeReplacer;
import com.strikerrocker.vt.recipes.VTRecipes;
import com.strikerrocker.vt.worldgen.NetherPocketer;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod(modid = vtModInfo.MOD_ID, name = vtModInfo.NAME, version = vtModInfo.VERSION, dependencies = "after:*", guiFactory = vtModInfo.PACKAGE_LOCATION + ".gui.config.VTGuiFactory")
public class vt {

    public static final ItemArmor.ArmorMaterial binoculars = EnumHelper.addArmorMaterial("binoculars", vtModInfo.MOD_ID + ":binoculars", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);


    @SidedProxy(modId = vtModInfo.MOD_ID, clientSide = vtModInfo.PACKAGE_LOCATION + ".proxies.VTClientProxy", serverSide = vtModInfo.PACKAGE_LOCATION + ".proxies.VTCommonProxy")
    public static VTCommonProxy proxy;

    public static SimpleNetworkWrapper network;


    @Mod.Instance(vtModInfo.MOD_ID)
    public static vt instance;


    private static Logger logger;


    public static void logInfo(String message) {
        logger.info("VanillaTweaks: " + message);
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        VTConfigHandler.init(event.getSuggestedConfigurationFile());
        VTBlocks.init();
        VTEntities.registerEntities(this);
        VTItems.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new VTGuiHandler());
        network = NetworkRegistry.INSTANCE.newSimpleChannel(vtModInfo.MOD_ID);
        network.registerMessage(new PacketUpdatePedestal.Handler(), PacketUpdatePedestal.class, 0, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdatePedestal.Handler(), PacketRequestUpdatePedestal.class, 1, Side.SERVER);
        proxy.registerRenderers();
        NetherPocketer handler = new NetherPocketer();
        MinecraftForge.TERRAIN_GEN_BUS.register(handler);
        VTRecipes.registerRecipes();
        VTRecipeReplacer.replaceRecipes();
        logInfo("Pre-Init Complete");
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new VTGuiHandler());
        GameRegistry.registerFuelHandler(new VTFuelHandler());
        VTEnchantments.registerEnchantments();
        MinecraftForge.EVENT_BUS.register(VTEventHandler.instance);
        MinecraftForge.EVENT_BUS.register(new VTSoundHandler());
        VTVanillaPropertiesChanger.init();
        logInfo("Init Complete");
    }


    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        logInfo("Post-Init Complete");
    }


}


