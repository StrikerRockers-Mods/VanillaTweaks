package io.github.strikerrocker.vt.content.blocks.pedestal;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.content.blocks.Blocks;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PedestalScreen extends ContainerScreen<PedestalContainer> {

    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(VTModInfo.MODID, "textures/gui/pedestal.png");

    private final PlayerInventory playerInv;

    public PedestalScreen(PedestalContainer container, PlayerInventory playerInv, ITextComponent name) {
        super(container, playerInv, name);
        this.playerInv = playerInv;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format(Blocks.PEDESTAL_BLOCK.getTranslationKey());
        font.drawString(name, xSize / 2 - font.getStringWidth(name) / 2, 6, 0x404040);
        font.drawString(playerInv.getDisplayName().getFormattedText(), 8, 40, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        blit(x, y, 0, 0, xSize, ySize);
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }
}