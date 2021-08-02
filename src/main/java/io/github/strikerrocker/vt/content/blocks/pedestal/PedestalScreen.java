package io.github.strikerrocker.vt.content.blocks.pedestal;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PedestalScreen extends AbstractContainerScreen<PedestalContainer> {

    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(VanillaTweaks.MOD_ID, "textures/gui/pedestal.png");

    private final Inventory playerInv;

    public PedestalScreen(PedestalContainer container, Inventory playerInv, Component name) {
        super(container, playerInv, name);
        this.playerInv = playerInv;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        int posX = (width - getXSize()) / 2;
        int posY = (height - getYSize()) / 2;
        this.blit(matrixStack, posX, posY, 0, 0, getXSize(), getYSize());
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.title, this.titleLabelX, this.titleLabelY, 0x404040);
        this.font.draw(matrixStack, playerInv.getDisplayName(), 8, 40, 0x404040);
    }
}