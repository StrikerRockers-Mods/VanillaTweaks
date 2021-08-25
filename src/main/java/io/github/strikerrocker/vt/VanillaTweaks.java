package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.content.ContentModule;
import io.github.strikerrocker.vt.content.blocks.BlockConditions;
import io.github.strikerrocker.vt.content.blocks.BlockInit;
import io.github.strikerrocker.vt.content.items.ItemConditions;
import io.github.strikerrocker.vt.content.items.ItemInit;
import io.github.strikerrocker.vt.enchantments.EnchantmentModule;
import io.github.strikerrocker.vt.loot.LootModule;
import io.github.strikerrocker.vt.recipes.RecipeModule;
import io.github.strikerrocker.vt.recipes.VanillaRecipeConditions;
import io.github.strikerrocker.vt.tweaks.TweaksModule;
import io.github.strikerrocker.vt.world.WorldModule;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

@Mod(MOD_ID)
public class VanillaTweaks {
    public static final Logger LOGGER = LogManager.getLogger();
    /**
     * Vanilla Tweaks mod ID
     */
    public static final String MOD_ID = "vanillatweaks";
    private static final List<Module> modules = new ArrayList<>();

    public VanillaTweaks() {
        registerModules();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        ItemInit.ITEMS.register(modEventBus);
        ItemInit.ENTITY_TYPES.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        BlockInit.MENU_TYPE.register(modEventBus);
        BlockInit.BLOCK_ENTITY_TYPE.register(modEventBus);
        modEventBus.addListener(this::registerRecipeSerializers);
    }

    private static void registerModules() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("VanillaTweaks");
        Collections.addAll(modules,
                new ContentModule(builder),
                new EnchantmentModule(builder),
                new LootModule(builder),
                new RecipeModule(builder),
                new TweaksModule(builder),
                new WorldModule(builder));
        modules.forEach(Module::setupConfig);
        builder.pop();
        ForgeConfigSpec spec = builder.build();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec);
    }

    private void setup(final FMLCommonSetupEvent event) {
        modules.forEach(module -> module.setup(event));
        LOGGER.info("Setup Complete");
    }

    public void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        CraftingHelper.register(ItemConditions.Serializer.INSTANCE);
        CraftingHelper.register(BlockConditions.Serializer.INSTANCE);
        CraftingHelper.register(VanillaRecipeConditions.Serializer.INSTANCE);
    }
}