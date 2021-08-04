package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MagmaBlock;
import net.minecraft.block.TNTBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TNTIgnition extends Feature {
    private ForgeConfigSpec.BooleanValue tntIgnition;

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        BlockState blockState = event.getPlacedBlock();
        IWorld world = event.getWorld();
        BlockPos pos = event.getPos();
        if (!world.isClientSide() && tntIgnition.get() && event.getEntity() != null) {
            if (blockState.getBlock() instanceof TNTBlock) {
                for (Direction f : Direction.values()) {
                    BlockState state = world.getBlockState(pos.relative(f));
                    if (state.getBlock() instanceof MagmaBlock || state.getMaterial() == Material.LAVA) {
                        blockState.getBlock().catchFire(blockState, event.getEntity().level, pos, f,
                                event.getEntity() instanceof LivingEntity ? (LivingEntity) event.getEntity() :
                                        null);
                        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
                    }
                }
            } else if (blockState.getBlock() instanceof MagmaBlock || blockState.getFluidState().getType() == Fluids.LAVA || blockState.getFluidState().getType() == Fluids.FLOWING_LAVA) {
                for (Direction f : Direction.values()) {
                    BlockState state = world.getBlockState(pos.relative(f));
                    if (state.getBlock() instanceof TNTBlock) {
                        BlockPos relativePos = pos.relative(f);
                        state.getBlock().catchFire(blockState, event.getEntity().level, relativePos, f.getOpposite(),
                                event.getEntity() instanceof LivingEntity ? (LivingEntity) event.getEntity() :
                                        null);
                        world.setBlock(relativePos, Blocks.AIR.defaultBlockState(), 11);
                    }
                }
            }
        }
    }

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        tntIgnition = builder
                .translation("config.vanillatweaks:tntIgnition")
                .comment("Want TNT to ignite when next to lava or magma block?")
                .define("tntIgnition", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }
}
