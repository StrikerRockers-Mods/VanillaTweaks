package io.github.strikerrocker.vt.content.blocks.pedestal;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.content.blocks.Blocks;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class GuiPedestal extends ContainerScreen {

    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(VTModInfo.MODID, "textures/gui/pedestal.png");

    private final PlayerEntity playerInv;

    public GuiPedestal(PlayerEntity playerInv, TileEntityPedestal pedestal) {
        super(new ContainerPedestal(playerInv, pedestal));
        this.playerInv = playerInv;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format(Blocks.pedestal.getTranslationKey() + ".name");
        font.drawString(name, xSize / 2 - font.getStringWidth(name) / 2, 6, 0x404040);
        font.drawString(playerInv.getDisplayName().font(), 8, ySize - 94, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        blit(x, y, 0, 0, xSize, ySize);
    }
}