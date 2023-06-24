package io.github.strikerrocker.vt.content.blocks.pedestal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PedestalRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    public PedestalRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(PedestalBlockEntity blockEntity, float tickDelta, PoseStack matrixStack, MultiBufferSource vertexConsumers, int light, int overlay) {
        matrixStack.pushPose();
        ItemStack stack = blockEntity.inventory.getStackInSlot(0);
        double offset = Math.sin((blockEntity.getLevel().getGameTime() + tickDelta) / 8) / 4.0;
        matrixStack.translate(0.5, 1.25 + offset, 0.5);
        matrixStack.mulPose(Axis.YP.rotationDegrees((blockEntity.getLevel().getGameTime() + tickDelta) * 4));
        float scale = 1.5f;
        matrixStack.scale(scale, scale, scale);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, light, overlay, matrixStack, vertexConsumers, Minecraft.getInstance().level, 0);
        matrixStack.popPose();
    }
}