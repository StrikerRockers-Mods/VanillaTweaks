package io.github.strikerrocker.vt.content.blocks;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.blocks.pedestal.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

/**
 * Handles everything related to blocks
 */
public class BlockInit extends Feature {
    // Deferred Registries
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    // Blocks
    public static final RegistryObject<Block> PEDESTAL_BLOCK = BLOCKS.register("pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> CHARCOAL_BLOCK = BLOCKS.register("charcoalblock", () ->
            new Block(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(5.0f, 10.0f)));
    public static final RegistryObject<Block> SUGAR_BLOCK = BLOCKS.register("sugarblock",
            () -> new Block(Properties.of(Material.SAND, MaterialColor.TERRACOTTA_WHITE).strength(0.5f).sound(SoundType.SAND)));
    public static final RegistryObject<Block> FLINT_BLOCK = BLOCKS.register("flintblock", () ->
            new Block(Properties.of(Material.SAND, MaterialColor.COLOR_BROWN).strength(1.0f, 10.0f)));
    public static final RegistryObject<MenuType<PedestalContainer>> PEDESTAL_MENU_TYPE = MENU_TYPE.register("pedestal",
            () -> IForgeMenuType.create(((windowId, inv, data) -> new PedestalContainer(windowId, inv, data.readBlockPos()))));    // BlockEntityType and MenuType
    // Configs
    static ForgeConfigSpec.BooleanValue enableStorageBlocks;
    static ForgeConfigSpec.BooleanValue enablePedestal;
    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL_TYPE = BLOCK_ENTITY_TYPE.register("pedestal",
            () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, PEDESTAL_BLOCK.get()).build(null));

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
        if (item == CHARCOAL_BLOCK.get().asItem())
            event.setBurnTime(16000);
        if (item == net.minecraft.world.level.block.Blocks.TORCH.asItem())
            event.setBurnTime(400);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            MenuScreens.register(PEDESTAL_MENU_TYPE.get(), PedestalScreen::new);
            event.registerBlockEntityRenderer(PEDESTAL_TYPE.get(), PedestalRenderer::new);
        }
    }


}