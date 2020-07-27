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
    public void render(MatrixStack matrixStack, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        renderBackground(matrixStack);
        this.drawGuiContainerBackgroundLayer(matrixStack, p_230430_4_, p_230430_2_, p_230430_3_);
        super.render(matrixStack, p_230430_2_, p_230430_3_, p_230430_4_);
        this.func_230459_a_(matrixStack, p_230430_2_, p_230430_3_);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bindTexture(BG_TEXTURE);
        int posX = (width - xSize) / 2;
        int posY = (height - ySize) / 2;
        this.blit(matrixStack, posX, posY, 0, 0, xSize, ySize);
    }
}