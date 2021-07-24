package io.github.strikerrocker.vt.content.blocks.pedestal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;

public class PedestalRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    public PedestalRenderer(BlockEntityRendererProvider.Context provider) {
    }

    @Override
    public void render(PedestalBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int i, int i1) {
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(0);
        matrixStack.pushPose();
        double offset = Math.sin((tileEntityIn.getLevel().getGameTime() - tileEntityIn.lastChangeTime + partialTicks) / 8) / 4.0;
        matrixStack.translate(0.5, 1.25 + offset, 0.5);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees((tileEntityIn.getLevel().getGameTime() + partialTicks) * 4));
        float scale = 1.5f;
        matrixStack.scale(scale, scale, scale);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.GROUND, i, i1, matrixStack, iRenderTypeBuffer, 0);
        matrixStack.popPose();
    }
}