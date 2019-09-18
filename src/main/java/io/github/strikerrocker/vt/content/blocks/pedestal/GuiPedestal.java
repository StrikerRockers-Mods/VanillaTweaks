package io.github.strikerrocker.vt.content.blocks.pedestal;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.content.blocks.Blocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiPedestal extends GuiContainer {

    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(VTModInfo.MODID, "textures/gui/pedestal.png");

    private final InventoryPlayer playerInv;

    public GuiPedestal(InventoryPlayer playerInv, TileEntityPedestal pedestal) {
        super(new ContainerPedestal(playerInv, pedestal));
        this.playerInv = playerInv;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format(Blocks.pedestal.getTranslationKey() + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, 40, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}