package io.github.strikerrocker.vt.content.blocks.pedestal;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class PedestalTileEntityRenderer extends TileEntityRenderer<PedestalTileEntity> {
    public PedestalTileEntityRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(PedestalTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(0);
        matrixStack.pushPose();
        double offset = Math.sin((tileEntityIn.getLevel().getGameTime() - tileEntityIn.lastChangeTime + partialTicks) / 8) / 4.0;
        matrixStack.translate(0.5, 1.25 + offset, 0.5);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees((tileEntityIn.getLevel().getGameTime() + partialTicks) * 4));
        float scale = 1.5f;
        matrixStack.scale(scale, scale, scale);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemCameraTransforms.TransformType.GROUND, i, i1, matrixStack, iRenderTypeBuffer);
        matrixStack.popPose();
    }
}