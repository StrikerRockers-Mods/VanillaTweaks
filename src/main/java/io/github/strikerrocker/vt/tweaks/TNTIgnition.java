package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TNTIgnition extends Feature {
    private ForgeConfigSpec.BooleanValue enableTNTIgnition;

    /**
     * Explode Tnt when it is beside lava or magma block
     */
    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        BlockState blockState = event.getPlacedBlock();
        LevelAccessor level = event.getWorld();
        BlockPos pos = event.getPos();
        if (!level.isClientSide() && enableTNTIgnition.get() && event.getEntity() != null) {
            if (blockState.getBlock() instanceof TntBlock) {
                for (Direction f : Direction.values()) {
                    BlockState state = level.getBlockState(pos.relative(f));
                    if (state.getBlock() instanceof MagmaBlock || state.getMaterial() == Material.LAVA) {
                        blockState.getBlock().catchFire(blockState, event.getEntity().level, pos, f,
                                event.getEntity() instanceof LivingEntity livingEntity ? livingEntity :
                                        null);
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
                    }
                }
            } else if (blockState.getBlock() instanceof MagmaBlock) {
                for (Direction f : Direction.values()) {
                    BlockState state = level.getBlockState(pos.relative(f));
                    if (state.getBlock() instanceof TntBlock) {
                        BlockPos relativePos = pos.relative(f);
                        state.getBlock().catchFire(blockState, event.getEntity().level, relativePos, f.getOpposite(),
                                event.getEntity() instanceof LivingEntity livingEntity ? livingEntity :
                                        null);
                        level.setBlock(relativePos, Blocks.AIR.defaultBlockState(), 11);
                    }
                }
            }
        }
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enableTNTIgnition = builder
                .translation("config.vanillatweaks:tntIgnition")
                .comment("Want TNT to ignite when next to lava or magma block?")
                .define("tntIgnition", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }
}
