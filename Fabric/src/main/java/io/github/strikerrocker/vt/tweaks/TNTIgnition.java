package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.events.BlockPlaceCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.material.Material;

public class TNTIgnition extends Feature {
    /**
     * Explode TNT when it is beside lava or magma block
     */
    @Override
    public void initialize() {
        BlockPlaceCallback.EVENT.register((world, pos, blockState, entity, stack) -> {
            if (!world.isClientSide() && VanillaTweaksFabric.config.tweaks.tntIgnition) {
                if (blockState.getBlock() instanceof TntBlock) {
                    for (Direction f : Direction.values()) {
                        if (world.getBlockState(pos.relative(f)).getBlock() instanceof MagmaBlock || world.getBlockState(pos.relative(f)).getMaterial() == Material.LAVA) {
                            TntBlock.explode(world, pos);
                            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
                        }
                    }
                } else if (blockState.getBlock() instanceof MagmaBlock) {
                    for (Direction f : Direction.values()) {
                        BlockPos offsetPos = pos.relative(f);
                        if (world.getBlockState(offsetPos).getBlock() instanceof TntBlock) {
                            TntBlock.explode(world, offsetPos);
                            world.setBlock(offsetPos, Blocks.AIR.defaultBlockState(), 11);
                        }
                    }
                }
            }
        });
    }
}
