package io.github.strikerrocker.vt;

import io.github.strikerrocker.vt.base.ForgeModule;
import io.github.strikerrocker.vt.content.ContentModule;
import io.github.strikerrocker.vt.content.blocks.BlockConditions;
import io.github.strikerrocker.vt.content.blocks.ForgeBlocks;
import io.github.strikerrocker.vt.content.items.ForgeItems;
import io.github.strikerrocker.vt.content.items.ItemConditions;
import io.github.strikerrocker.vt.enchantments.EnchantmentInit;
import io.github.strikerrocker.vt.enchantments.EnchantmentModule;
import io.github.strikerrocker.vt.loot.LootModule;
import io.github.strikerrocker.vt.recipes.RecipeModule;
import io.github.strikerrocker.vt.recipes.VanillaRecipeConditions;
import io.github.strikerrocker.vt.tweaks.TweaksModule;
import io.github.strikerrocker.vt.world.NoMoreLavaPocketGen;
import io.github.strikerrocker.vt.world.WorldModule;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

@Mod(MOD_ID)
public class VanillaTweaksForge {
    private static final List<ForgeModule> modules = new ArrayList<>();

    public VanillaTweaksForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ForgeItems.ITEMS.register(modEventBus);
        ForgeItems.ENTITY_TYPES.register(modEventBus);
        ForgeBlocks.BLOCKS.register(modEventBus);
        ForgeBlocks.MENU_TYPE.register(modEventBus);
        ForgeBlocks.BLOCK_ENTITY_TYPE.register(modEventBus);
        EnchantmentInit.ENCHANTMENTS.register(modEventBus);
        EnchantmentInit.GLM.register(modEventBus);
        NoMoreLavaPocketGen.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
        registerModules();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::registerRecipeSerializers);
        modEventBus.addListener(ForgeItems::itemGroup);
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
        modules.forEach(ForgeModule::setupConfig);
        builder.pop();
        ForgeConfigSpec spec = builder.build();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec);
    }

    private void setup(final FMLCommonSetupEvent event) {
        modules.forEach(module -> module.setup(event));
        VanillaTweaks.LOGGER.info("Setup Complete");
    }

    public void registerRecipeSerializers(RegisterEvent event) {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS)) {
            CraftingHelper.register(ItemConditions.Serializer.INSTANCE);
            CraftingHelper.register(BlockConditions.Serializer.INSTANCE);
            CraftingHelper.register(VanillaRecipeConditions.Serializer.INSTANCE);
        }
    }
}