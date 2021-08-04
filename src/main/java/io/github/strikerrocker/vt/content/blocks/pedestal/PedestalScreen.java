package io.github.strikerrocker.vt.content.blocks.pedestal;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PedestalScreen extends ContainerScreen<PedestalContainer> {

    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(VanillaTweaks.MOD_ID, "textures/gui/pedestal.png");

    private final PlayerInventory playerInv;

    public PedestalScreen(PedestalContainer container, PlayerInventory playerInv, ITextComponent name) {
        super(container, playerInv, name);
        this.playerInv = playerInv;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        this.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderLabels(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(BG_TEXTURE);
        int posX = (width - getXSize()) / 2;
        int posY = (height - getYSize()) / 2;
        this.blit(matrixStack, posX, posY, 0, 0, getXSize(), getYSize());
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.title, this.titleLabelX, this.titleLabelY, 0x404040);
        this.font.draw(matrixStack, playerInv.getDisplayName(), 8, 40, 0x404040);
    }
}