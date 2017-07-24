package com.strikerrocker.vt.coremod;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

/**
 * Craft++'s coremod hooks
 */
@SuppressWarnings("unused")
public class VTCoremodHooks {
    /**
     * Schedules a block update if the block is a Craft++ falling block
     *
     * @param world    The world
     * @param blockPos The block position
     * @param block    The block
     */

    /**
     * Called on every update tick on every ticking block in the world (edited to make Craft++'s falling blocks fall)
     *
     * @param world    The world
     * @param blockPos The block position
     * @param block    The block
     */

    /**
     * Returns whether the given cactus block can stay at the given block position in the given world
     *
     * @param world    The world
     * @param blockPos The block position
     * @param block    The block
     * @return Whether the given cactus block can stay at the block position in the world
     */
    public static boolean canCactusStay(World world, BlockPos blockPos, Block block) {
        IBlockState state = world.getBlockState(blockPos.down());
        return state.getBlock().canSustainPlant(state, world, blockPos.down(), EnumFacing.UP, (IPlantable) block);
    }

    /**
     * Returns whether or not the given block is a Craft++ falling block
     *
     * @param block The block
     * @return Whether or not the block is a Craft++ falling block
     */
}
