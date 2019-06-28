package io.github.strikerrocker.vt.content.items.craftingpad;

import io.github.strikerrocker.vt.content.items.Items;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.io.IOException;

public class GuiCraftingPad extends GuiContainer implements IRecipeShownListener {
    private static final ResourceLocation craftingTableGuiTexture = new ResourceLocation("textures/gui/container/crafting_table.png");
    private final GuiRecipeBook recipeBookGui;
    private GuiButtonImage recipeButton;
    private boolean widthTooNarrow;

    public GuiCraftingPad(InventoryPlayer inventoryPlayer, World world) {
        super(new ContainerCraftingPad(inventoryPlayer, world));
        recipeBookGui = new GuiRecipeBook();
    }

    @Override
    public void initGui() {
        super.initGui();
        widthTooNarrow = width < 379;
        recipeBookGui.func_194303_a(width, height, mc, widthTooNarrow, ((ContainerWorkbench) inventorySlots).craftMatrix);
        guiLeft = recipeBookGui.updateScreenPosition(widthTooNarrow, width, xSize);
        recipeButton = new GuiButtonImage(10, guiLeft + 5, height / 2 - 49, 20, 18, 0, 168, 19, craftingTableGuiTexture);
        buttonList.add(recipeButton);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        recipeBookGui.tick();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        if (recipeBookGui.isVisible() && widthTooNarrow) {
            drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
            recipeBookGui.render(mouseX, mouseY, partialTicks);
        } else {
            recipeBookGui.render(mouseX, mouseY, partialTicks);
            super.drawScreen(mouseX, mouseY, partialTicks);
            recipeBookGui.renderGhostRecipe(guiLeft, guiTop, true, partialTicks);
        }

        renderHoveredToolTip(mouseX, mouseY);
        recipeBookGui.renderTooltip(guiLeft, guiTop, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format(Items.craftingPad.getTranslationKey() + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(craftingTableGuiTexture);
        int i = guiLeft;
        int j = (height - ySize) / 2;
        drawTexturedModalRect(i, j, 0, 0, xSize, ySize);
    }

    @Override
    protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
        return (!widthTooNarrow || !recipeBookGui.isVisible()) && super.isPointInRegion(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (!recipeBookGui.mouseClicked(mouseX, mouseY, mouseButton)) {
            if (!widthTooNarrow || !recipeBookGui.isVisible()) {
                super.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    protected boolean hasClickedOutside(int p_193983_1_, int p_193983_2_, int p_193983_3_, int p_193983_4_) {
        boolean flag = p_193983_1_ < p_193983_3_ || p_193983_2_ < p_193983_4_ || p_193983_1_ >= p_193983_3_ + xSize || p_193983_2_ >= p_193983_4_ + ySize;
        return recipeBookGui.hasClickedOutside(p_193983_1_, p_193983_2_, guiLeft, guiTop, xSize, ySize) && flag;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 10) {
            recipeBookGui.initVisuals(widthTooNarrow, ((ContainerWorkbench) inventorySlots).craftMatrix);
            recipeBookGui.toggleVisibility();
            guiLeft = recipeBookGui.updateScreenPosition(widthTooNarrow, width, xSize);
            recipeButton.setPosition(guiLeft + 5, height / 2 - 49);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (!recipeBookGui.keyPressed(typedChar, keyCode)) {
            super.keyTyped(typedChar, keyCode);
        }
    }


    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
        recipeBookGui.slotClicked(slotIn);
    }

    @Override
    public void onGuiClosed() {
        recipeBookGui.removed();
        super.onGuiClosed();
    }

    @Override
    public void recipesUpdated() {
        recipeBookGui.recipesUpdated();
    }

    @Override
    public GuiRecipeBook func_194310_f() {
        return recipeBookGui;
    }
}
