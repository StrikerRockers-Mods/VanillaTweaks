package io.github.strikerrocker.vt.content.blocks.pedestal;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

public class PedestalTileEntityRenderer extends TileEntityRenderer<PedestalTileEntity> {

    @Override
    public void func_225616_a_(PedestalTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(0);
        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.pushMatrix();
        double offset = Math.sin((tileEntityIn.getWorld().getGameTime() - tileEntityIn.lastChangeTime + partialTicks) / 8) / 4.0;
        GlStateManager.translated(x + 0.5, y + 1.25 + offset, z + 0.5);
        GlStateManager.rotated((tileEntityIn.getWorld().getGameTime() + partialTicks) * 4, 0, 1, 0);

        IBakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(stack, tileEntityIn.getWorld(), null);
        model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

        Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        Minecraft.getInstance().getItemRenderer().renderItem(stack, model);

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }
}