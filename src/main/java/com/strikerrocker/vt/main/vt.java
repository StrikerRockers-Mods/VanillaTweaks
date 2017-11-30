package com.strikerrocker.vt.main;

import com.strikerrocker.vt.blocks.VTBlocks;
import com.strikerrocker.vt.capabilities.CapabilitySelfPlanting;
import com.strikerrocker.vt.dispenser.VTDispenserBehaviors;
import com.strikerrocker.vt.enchantments.VTEnchantments;
import com.strikerrocker.vt.entities.VTEntities;
import com.strikerrocker.vt.handlers.VTConfigHandler;
import com.strikerrocker.vt.handlers.VTEventHandler;
import com.strikerrocker.vt.handlers.VTFuelHandler;
import com.strikerrocker.vt.handlers.VTGuiHandler;
import com.strikerrocker.vt.items.ItemArmor;
import com.strikerrocker.vt.items.VTItems;
import com.strikerrocker.vt.misc.VTVanillaPropertiesChanger;
import com.strikerrocker.vt.network.PacketRequestUpdatePedestal;
import com.strikerrocker.vt.network.PacketUpdatePedestal;
import com.strikerrocker.vt.proxies.VTCommonProxy;
import com.strikerrocker.vt.recipes.VTRecipes;
import com.strikerrocker.vt.worldgen.NetherPocketer;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;


@Mod(modid = VTModInfo.MOD_ID,
        name = VTModInfo.NAME,
        version = VTModInfo.VERSION,
        dependencies = "after:*",
        guiFactory = VTModInfo.PACKAGE_LOCATION + ".gui.config.VTGuiFactory")
@SuppressWarnings("unused")
public final class VT {

    public static final ItemArmor.ArmorMaterial binoculars = EnumHelper.addArmorMaterial("binoculars", VTModInfo.MOD_ID + ":binoculars", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);


    @SidedProxy(modId = VTModInfo.MOD_ID, clientSide = VTModInfo.PACKAGE_LOCATION + ".proxies.VTClientProxy", serverSide = VTModInfo.PACKAGE_LOCATION + ".proxies.VTCommonProxy")
    public static VTCommonProxy proxy;

    public static SimpleNetworkWrapper network;


    @Mod.Instance(VTModInfo.MOD_ID)
    public static VT instance;


    private static Logger logger;


    public static void logInfo(String message) {
        logger.info("VanillaTweaks: " + message);
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        VTConfigHandler.init(event.getSuggestedConfigurationFile());
        VTBlocks.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new VTGuiHandler());
        proxy.registerRenderers();
        network = NetworkRegistry.INSTANCE.newSimpleChannel(VTModInfo.MOD_ID);
        network.registerMessage(new PacketUpdatePedestal.Handler(), PacketUpdatePedestal.class, 0, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdatePedestal.Handler(), PacketRequestUpdatePedestal.class, 1, Side.SERVER);
        NetherPocketer handler = new NetherPocketer();
        MinecraftForge.TERRAIN_GEN_BUS.register(handler);
        VTRecipes.registerRecipes();
        VTEntities.init();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new VTGuiHandler());
        GameRegistry.registerFuelHandler(new VTFuelHandler());
        MinecraftForge.EVENT_BUS.register(VTEventHandler.instance);
        VTDispenserBehaviors.registerDispenserBehaviors();
        CapabilitySelfPlanting.register();
        VTVanillaPropertiesChanger.init();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
    }

    @Mod.EventBusSubscriber
    public static class RegsitrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            VTItems.register(event.getRegistry());
            VTBlocks.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            VTBlocks.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerEnchantment(RegistryEvent.Register<Enchantment> event) {
            VTEnchantments.registerEnchantments(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            VTItems.registerModels();
            VTBlocks.registerModels();
        }

    }

}
