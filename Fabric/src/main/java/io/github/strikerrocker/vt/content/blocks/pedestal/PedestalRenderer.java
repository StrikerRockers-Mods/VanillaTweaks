package io.github.strikerrocker.vt.content.blocks.pedestal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;

public class PedestalRenderer implements BlockEntityRenderer<PedestalBlockEntity> {

    public PedestalRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(PedestalBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        matrices.pushPose();
        ItemStack stack = blockEntity.getItem(0);
        double offset = Math.sin((blockEntity.getLevel().getGameTime() + tickDelta) / 8.0) / 4.0;
        matrices.translate(0.5, 1.25 + offset, 0.5);
        matrices.mulPose(Vector3f.YP.rotationDegrees((blockEntity.getLevel().getGameTime() + tickDelta) * 4));
        float scale = 1.25f;
        matrices.scale(scale, scale, scale);
        int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.GROUND, lightAbove, OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, 0);
        matrices.popPose();
    }
}