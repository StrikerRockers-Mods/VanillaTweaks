package io.github.strikerrocker.vt.content.blocks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import io.github.strikerrocker.vt.content.CommonObjects;
import io.github.strikerrocker.vt.content.blocks.pedestal.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
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

public class ForgeBlocks extends ForgeFeature {
    // Deferred Registries
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    // Blocks
    public static final RegistryObject<Block> PEDESTAL_BLOCK = BLOCKS.register("pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> CHARCOAL_BLOCK = BLOCKS.register("charcoal_block", () -> CommonObjects.CHARCOAL_BLOCK);
    public static final RegistryObject<Block> SUGAR_BLOCK = BLOCKS.register("sugar_block", () -> CommonObjects.SUGAR_BLOCK);
    public static final RegistryObject<Block> FLINT_BLOCK = BLOCKS.register("flint_block", () -> CommonObjects.FLINT_BLOCK);
    public static final RegistryObject<MenuType<PedestalContainer>> PEDESTAL_MENU_TYPE = MENU_TYPE.register("pedestal",
            () -> IForgeMenuType.create(((windowId, inv, data) -> new PedestalContainer(windowId, inv, data.readBlockPos()))));    // BlockEntityType and MenuType
    // Configs
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
        if (item == CHARCOAL_BLOCK.get().asItem())
            event.setBurnTime(16000);
        if (item == Blocks.TORCH.asItem())
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

    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL_TYPE = BLOCK_ENTITY_TYPE.register("pedestal",
            () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, PEDESTAL_BLOCK.get()).build(null));


}