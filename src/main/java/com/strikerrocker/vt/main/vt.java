package com.strikerrocker.vt.main;

import com.strikerrocker.vt.WorldGen.NetherPocketer;
import com.strikerrocker.vt.blocks.VTBlocks;
import com.strikerrocker.vt.capabilities.CapabilitySelfPlanting;
import com.strikerrocker.vt.dispenser.VTDispenserBehaviors;
import com.strikerrocker.vt.enchantments.VTEnchantments;
import com.strikerrocker.vt.entities.VTEntities;
import com.strikerrocker.vt.handlers.VTConfigHandler;
import com.strikerrocker.vt.handlers.VTEventHandler;
import com.strikerrocker.vt.handlers.VTFuelHandler;
import com.strikerrocker.vt.handlers.VTGuiHandler;
import com.strikerrocker.vt.items.VTItems;
import com.strikerrocker.vt.misc.VTVanillaPropertiesChanger;
import com.strikerrocker.vt.network.PacketRequestUpdatePedestal;
import com.strikerrocker.vt.network.PacketUpdatePedestal;
import com.strikerrocker.vt.proxies.VTCommonProxy;
import com.strikerrocker.vt.recipes.VTRecipeReplacer;
import com.strikerrocker.vt.recipes.VTRecipes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;


@Mod(modid = vtModInfo.MOD_ID,
        name = vtModInfo.NAME,
        version = vtModInfo.VERSION,
        dependencies = "after:*",
        guiFactory = vtModInfo.PACKAGE_LOCATION + ".gui.config.VTGuiFactory")
@SuppressWarnings("unused")
public final class vt {

    public static final ItemArmor.ArmorMaterial binoculars = EnumHelper.addArmorMaterial("binoculars", vtModInfo.MOD_ID + ":copper", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
    /**
     * The proxy of vanilla Tweaks
     */
    @SidedProxy(modId = vtModInfo.MOD_ID, clientSide = vtModInfo.PACKAGE_LOCATION + ".proxies.VTClientProxy", serverSide = vtModInfo.PACKAGE_LOCATION + ".proxies.VTCommonProxy")
    public static VTCommonProxy proxy;

    public static SimpleNetworkWrapper network;

    /**
     * The mod instance of vanilla Tweaks
     */

    @Instance(vtModInfo.MOD_ID)
    public static vt instance;

    /**
     * The logger for vanilla Tweaks
     */
    private static Logger logger;

    /**
     * Logs a message with vanilla Tweaks's logger with the INFO level
     *
     * @param message The string to be logged
     */
    public static void logInfo(String message) {
        logger.info("VanillaTweaks: " + message);
    }

    /**
     * Performs the following actions: <br>
     * - Initializes the logger <br>
     * - Hard-codes the mcmod.info <br>
     * - Initializes the config handler <br>
     * - Enables the Version Checker support <br>
     * - Registers the blocks and items
     *
     * @param event The FMLPreInitializationEvent
     */
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logInfo("Initialized the logger");
        logInfo("Initializing the config handler");
        VTConfigHandler.init(event.getSuggestedConfigurationFile());
        logInfo("Hard-coding the mcmod.info");
        ModMetadata modMetadata = event.getModMetadata();
        modMetadata.modId = vtModInfo.MOD_ID;
        modMetadata.name = vtModInfo.NAME;
        modMetadata.version = vtModInfo.VERSION;
        modMetadata.description = "A simple vanilla-enhancing mod";
        logInfo("Registering the blocks");
        VTBlocks.init();
        logInfo("Registering the items");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new VTGuiHandler());
        proxy.registerRenderers();
        network = NetworkRegistry.INSTANCE.newSimpleChannel(vtModInfo.MOD_ID);
        network.registerMessage(new PacketUpdatePedestal.Handler(), PacketUpdatePedestal.class, 0, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdatePedestal.Handler(), PacketRequestUpdatePedestal.class, 1, Side.SERVER);
        //Nether Pocketer
        NetherPocketer handler = new NetherPocketer();
        MinecraftForge.TERRAIN_GEN_BUS.register(handler);
        VTItems.init();
        logInfo("Pre-initialization completed successfully");
    }

    /**
     * Performs the following actions: <br>
     * - Registers the entities <br>
     * - Registers the renderers <br>
     * - Registers the GUI handler <br>
     * - Registers the enchantments <br>
     * - Registers the event handler <br>
     * - Registers the key bindings <br>
     * - Registers the fuel handler <br>
     * - Registers the crafting recipes <br>
     * - Registers the dispenser behaviors <br>
     * - Registers the self-planting <br>
     * - Initializes the vanilla properties changer <br>
     * - Initializes the recipe remover
     *
     * @param event The FMLInitializationEvent
     */
    @EventHandler
    public void onInit(FMLInitializationEvent event) {
        logInfo("Registering the entities");
        VTEntities.init();
        logInfo("Registering the GUI handler");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new VTGuiHandler());
        logInfo("Registering the enchantments");
        VTEnchantments.registerEnchantments();
        logInfo("Registering the event handler");
        MinecraftForge.EVENT_BUS.register(VTEventHandler.instance);
        logInfo("Registering the fuel handler");
        GameRegistry.registerFuelHandler(new VTFuelHandler());
        logInfo("Registering the crafting/furnace recipes");
        VTRecipes.registerRecipes();
        logInfo("Registering the dispenser behaviors");
        VTDispenserBehaviors.registerDispenserBehaviors();
        logInfo("Registering the self-planting");
        CapabilitySelfPlanting.register();
        logInfo("Initializing the vanilla properties changer");
        VTVanillaPropertiesChanger.init();
        logInfo("Initializing the crafting recipe remover");
        VTRecipeReplacer.replaceRecipes();
        logInfo("Initialization completed successfully");
    }
}
