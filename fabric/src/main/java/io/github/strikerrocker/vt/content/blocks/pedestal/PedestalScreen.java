package io.github.strikerrocker.vt.content.blocks.pedestal;

import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PedestalScreen extends AbstractContainerScreen<PedestalScreenHandler> {

    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(VanillaTweaks.MOD_ID, "textures/gui/pedestal.png");

    public PedestalScreen(PedestalScreenHandler container, Inventory playerInv, Component title) {
        super(container, playerInv, title);
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = (imageWidth - font.width(title)) / 2;
        inventoryLabelY = 40;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(BG_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, i, j, f);
        renderTooltip(guiGraphics, i, j);
    }
}