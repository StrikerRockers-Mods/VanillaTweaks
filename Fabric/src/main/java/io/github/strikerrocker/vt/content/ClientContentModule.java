package io.github.strikerrocker.vt.content;

import io.github.strikerrocker.vt.base.FabricModule;
import io.github.strikerrocker.vt.content.blocks.FabricBlocks;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalRenderer;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalScreen;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import static io.github.strikerrocker.vt.content.blocks.FabricBlocks.PEDESTAL_TYPE;
import static io.github.strikerrocker.vt.content.items.FabricItems.DYNAMITE_TYPE;

public class ClientContentModule extends FabricModule {

    @Override
    public void addFeatures() {
    }

    @Override
    public void initialize() {
        MenuScreens.register(FabricBlocks.PEDESTAL_SCREEN_HANDLER, PedestalScreen::new);
        BlockEntityRendererRegistry.register(PEDESTAL_TYPE, PedestalRenderer::new);
        EntityRendererRegistry.register(DYNAMITE_TYPE, ThrownItemRenderer::new);
    }
}