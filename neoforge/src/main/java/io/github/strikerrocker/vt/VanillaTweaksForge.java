package io.github.strikerrocker.vt;

import com.mojang.serialization.Codec;
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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

@Mod(MOD_ID)
public class VanillaTweaksForge {
    private static final List<ForgeModule> modules = new ArrayList<>();
    private static final DeferredRegister<Codec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, MOD_ID);
    private static final Supplier itemCondition = CONDITION_CODECS.register(ItemConditions.NAME, () -> ItemConditions.CODEC);
    private static final Supplier blockCondition = CONDITION_CODECS.register(BlockConditions.NAME, () -> BlockConditions.CODEC);
    private static final Supplier vanillaRecipeCondition = CONDITION_CODECS.register(VanillaRecipeConditions.NAME, () -> VanillaRecipeConditions.CODEC);

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
        CONDITION_CODECS.register(modEventBus);
        registerModules();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(ForgeItems::itemGroup);
    }

    private static void registerModules() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
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
        ModConfigSpec spec = builder.build();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec);
    }

    private void setup(final FMLCommonSetupEvent event) {
        modules.forEach(module -> module.setup(event));
        VanillaTweaks.LOGGER.info("Setup Complete");
    }
}