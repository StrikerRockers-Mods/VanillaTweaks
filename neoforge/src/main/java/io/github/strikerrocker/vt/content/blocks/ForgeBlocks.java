package io.github.strikerrocker.vt.content.blocks;

import io.github.strikerrocker.vt.base.ForgeFeature;
import io.github.strikerrocker.vt.content.CommonObjects;
import io.github.strikerrocker.vt.content.blocks.pedestal.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static io.github.strikerrocker.vt.VanillaTweaks.MOD_ID;

public class ForgeBlocks extends ForgeFeature {
    // Deferred Registries
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(Registries.MENU, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MOD_ID);
    // Blocks
    public static final Supplier<PedestalBlock> PEDESTAL_BLOCK = BLOCKS.register("pedestal", PedestalBlock::new);
    public static final Supplier<Block> CHARCOAL_BLOCK = BLOCKS.register("charcoal_block", () -> CommonObjects.CHARCOAL_BLOCK);
    public static final Supplier<Block> SUGAR_BLOCK = BLOCKS.register("sugar_block", () -> CommonObjects.SUGAR_BLOCK);
    public static final Supplier<Block> FLINT_BLOCK = BLOCKS.register("flint_block", () -> CommonObjects.FLINT_BLOCK);
    public static final Supplier<MenuType<PedestalContainer>> PEDESTAL_MENU_TYPE = MENU_TYPE.register("pedestal",
            () -> IMenuTypeExtension.create(((windowId, inv, data) -> new PedestalContainer(windowId, inv, data.readBlockPos()))));    // BlockEntityType and MenuType

    // Configs
    static ModConfigSpec.BooleanValue enableStorageBlocks;
    static ModConfigSpec.BooleanValue enablePedestal;

    @Override
    public void setupConfig(ModConfigSpec.Builder builder) {
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

    public static final Supplier<BlockEntityType<PedestalBlockEntity>> PEDESTAL_TYPE = BLOCK_ENTITY_TYPE.register("pedestal",
            () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, PEDESTAL_BLOCK.get()).build(null));


}