package com.strikerrocker.vt.gui;

import com.strikerrocker.vt.main.vt;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SideOnly(Side.CLIENT)
public class GuiCraftingPad extends GuiContainer {
    private static final ResourceLocation craftingTableGuiTexture = new ResourceLocation("textures/gui/container/crafting_table.png");

    static {
        try {
            Class<?> neiApi = Class.forName("codechicken.nei.api.API");
            Class<?> overlayHandler = Class.forName("codechicken.nei.api.IOverlayHandler");
            Class<?> defaultOverlayHandler = Class.forName("codechicken.nei.recipe.DefaultOverlayHandler");
            Method registerGuiOverlayMethod = neiApi.getMethod("registerGuiOverlay", Class.class, String.class);
            Method registerGuiOverlayHandlerMethod = neiApi.getMethod("registerGuiOverlayHandler", Class.class, overlayHandler, String.class);
            registerGuiOverlayMethod.invoke(null, GuiCraftingPad.class, "crafting");
            registerGuiOverlayHandlerMethod.invoke(null, GuiCraftingPad.class, defaultOverlayHandler.newInstance(), "crafting");
            vt.logInfo("Registered NEI integration for crafting pad");
        } catch (NoSuchMethodException | ClassNotFoundException ignored) {
            vt.logInfo("Could not find NEI; skipping NEI integration for crafting pad");
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public GuiCraftingPad(InventoryPlayer inventoryPlayer, World world) {
        super(new ContainerCraftingPad(inventoryPlayer, world));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(craftingTableGuiTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}