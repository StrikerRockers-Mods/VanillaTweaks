package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MagmaBlock;
import net.minecraft.block.TNTBlock;
import net.minecraft.block.material.Material;
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
        if (!world.isRemote() && blockState.getBlock() instanceof TNTBlock && tntIgnition.get()) {
            for (Direction f : Direction.values())
                if (world.getBlockState(pos.offset(f, 1)).getBlock() instanceof MagmaBlock || world.getBlockState(pos.offset(f, 1)).getMaterial() == Material.LAVA) {
                    TNTBlock blockTNT = (TNTBlock) blockState.getBlock();
                    TNTBlock.explode(event.getEntity().getEntityWorld(), pos);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
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
