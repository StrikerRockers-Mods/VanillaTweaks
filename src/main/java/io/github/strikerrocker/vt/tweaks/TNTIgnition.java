package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.BlockMagma;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraft.block.BlockTNT.EXPLODE;

public class TNTIgnition extends Feature {
    private boolean tntIgnition;

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.PlaceEvent event) {
        IBlockState blockState = event.getPlacedBlock();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        if (!world.isRemote && blockState.getBlock() instanceof BlockTNT && tntIgnition) {
            for (EnumFacing f : EnumFacing.values())
                if (world.getBlockState(pos.offset(f, 1)).getBlock() instanceof BlockMagma || world.getBlockState(pos.offset(f, 1)).getMaterial() == Material.LAVA) {
                    BlockTNT blockTNT = (BlockTNT) blockState.getBlock();
                    blockTNT.explode(world, pos, blockState.withProperty(EXPLODE, true), event.getPlayer());
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
                }
        }
    }

    @Override
    public void syncConfig(Configuration config, String category) {
        tntIgnition = config.get(category, "tntIgnition", true, "Want TNT to ignite when next to lava or magma block").getBoolean();
    }


    @Override
    public boolean usesEvents() {
        return true;
    }
}
