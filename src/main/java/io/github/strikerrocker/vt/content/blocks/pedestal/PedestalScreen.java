package io.github.strikerrocker.vt.content.blocks.pedestal;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.strikerrocker.vt.VTModInfo;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
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
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        this.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, mouseX, mouseY);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bindTexture(BG_TEXTURE);
        int posX = (width - xSize) / 2;
        int posY = (height - ySize) / 2;
        this.blit(matrixStack, posX, posY, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.func_243248_b(matrixStack, this.title, this.titleX, this.titleY, 0x404040);
        this.font.func_243248_b(matrixStack, playerInv.getDisplayName(), 8, 40, 0x404040);
    }
}