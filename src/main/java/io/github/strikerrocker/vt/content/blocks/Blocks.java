package io.github.strikerrocker.vt.content.blocks;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.blocks.pedestal.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ObjectHolder;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

/**
 * Handles everything related to blocks
 */
public class Blocks extends Feature {
    public static final Block PEDESTAL_BLOCK = new PedestalBlock();
    @ObjectHolder(MOD_ID + ":pedestal")
    public static final BlockEntityType<PedestalBlockEntity> PEDESTAL_TYPE = null;
    private static final Block CHARCOAL_BLOCK = new Block(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(5.0f, 10.0f)).setRegistryName("charcoalblock");
    private static final Block SUGAR_BLOCK = new Block(Properties.of(Material.SAND, MaterialColor.TERRACOTTA_WHITE).strength(0.5f).sound(SoundType.SAND)).setRegistryName("sugarblock");
    private static final Block FLINT_BLOCK = new Block(Properties.of(Material.SAND, MaterialColor.COLOR_BROWN).strength(1.0f, 10.0f)).setRegistryName("flintblock");
    public static final Block[] BLOCKS = new Block[]{CHARCOAL_BLOCK, SUGAR_BLOCK, FLINT_BLOCK, PEDESTAL_BLOCK};
    static ForgeConfigSpec.BooleanValue enableStorageBlocks;
    static ForgeConfigSpec.BooleanValue enablePedestal;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enablePedestal = builder
                .translation("config.vanillatweaks:enablePedestal")
                .comment("Want to showcase your treasure but item frame doesn't satisfy you?")
                .define("enablePedestal", true);
        enableStorageBlocks = builder
                .translation("config.vanillatweaks:enableStorageBlocks")
                .comment("Want block forms of flint, charcoal and sugar?")
                .define("enableStorageBlocks", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    /**
     * Adds Charcoal block and torch as fuel source
     *
     * @param event FurnaceFuelBurnTimeEvent
     */
    @SubscribeEvent
    public void onFurnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
        Item item = event.getItemStack().getItem();
        if (item == CHARCOAL_BLOCK.asItem())
            event.setBurnTime(16000);
        if (item == net.minecraft.world.level.block.Blocks.TORCH.asItem())
            event.setBurnTime(400);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(PedestalContainer.TYPE, PedestalScreen::new);
            BlockEntityRenderers.register(PEDESTAL_TYPE, PedestalRenderer::new);
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        /**
         * Registers MenuType
         *
         * @param event Registry event for MenuType
         */
        @SubscribeEvent
        public static void onRegisterContainers(RegistryEvent.Register<MenuType<?>> event) {
            event.getRegistry().register(IForgeContainerType.create(((windowId, inv, data) -> new PedestalContainer(windowId, inv, data.readBlockPos()))).setRegistryName(MOD_ID, "pedestal"));
        }

        /**
         * Registers BlockEntityType
         *
         * @param event Registry event for BlockEntityType
         */
        @SubscribeEvent
        public static void onRegisterTEType(RegistryEvent.Register<BlockEntityType<?>> event) {
            event.getRegistry().register(BlockEntityType.Builder.of(PedestalBlockEntity::new, PEDESTAL_BLOCK).build(null).setRegistryName(new ResourceLocation(MOD_ID, "pedestal")));
        }

        /**
         * Registers blocks
         *
         * @param event Registry event for Block
         */
        @SubscribeEvent
        public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(CHARCOAL_BLOCK);
            event.getRegistry().register(SUGAR_BLOCK);
            event.getRegistry().register(FLINT_BLOCK);
        }

        /**
         * Registers RecipeSerializer for item blocks
         *
         * @param event Registry event for RecipeSerializer
         */
        @SubscribeEvent
        public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
            CraftingHelper.register(BlockConditions.Serializer.INSTANCE);
        }
    }
}