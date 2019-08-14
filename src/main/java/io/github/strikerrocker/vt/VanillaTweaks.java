package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.content.ContentModule;
import io.github.strikerrocker.vt.content.blocks.pedestal.PacketUpdatePedestal;
import io.github.strikerrocker.vt.enchantments.EnchantmentModule;
import io.github.strikerrocker.vt.loot.LootModule;
import io.github.strikerrocker.vt.recipes.RecipeModule;
import io.github.strikerrocker.vt.tweaks.TweaksModule;
import io.github.strikerrocker.vt.world.WorldModule;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static io.github.strikerrocker.vt.VTModInfo.MODID;

@Mod(MODID)
public class VanillaTweaks {
    public static final Logger LOGGER = LogManager.getLogger();
    private static final String PROTOCOL_VERSION = "1.0"; //for channel
    public static SimpleChannel network = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(MODID, MODID))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();
    private static List<Module> modules = new ArrayList<>();

    public VanillaTweaks() {
        registerModules();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        network.registerMessage(0, PacketUpdatePedestal.class, PacketUpdatePedestal::encode, PacketUpdatePedestal::decode, PacketUpdatePedestal::onMessage);
    }

    private void registerModules() {
        List<Function<ForgeConfigSpec.Builder, Module>> moduleBuilder = new ArrayList<>();
        Collections.addAll(moduleBuilder, ContentModule::new, EnchantmentModule::new, LootModule::new, RecipeModule::new, TweaksModule::new, WorldModule::new);
        for (Function<ForgeConfigSpec.Builder, Module> function : moduleBuilder) {
            Pair<Module, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(function);
            modules.add(specPair.getLeft());

            File cfgFile = FMLPaths.CONFIGDIR.get().resolve(MODID).resolve(String.format(specPair.getLeft().getName() + ".toml", MODID, ModConfig.Type.SERVER)).toFile();
            if (!cfgFile.getParentFile().exists()) {
                cfgFile.getParentFile().mkdirs();
            }
            ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, specPair.getRight(), cfgFile.toString());
            specPair.getLeft().setupConfig();
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        modules.forEach(Module::setup);
        LOGGER.info("Setup Complete");
    }
}
