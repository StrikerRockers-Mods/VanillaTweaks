package io.github.strikerrocker.vt.content.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class CharcoalBlock extends Block {
    CharcoalBlock(String name) {
        super(Block.Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(5.0f, 10.0f));
        setRegistryName(name);
    }

    @Override
    public boolean isFireSource(BlockState state, IWorldReader world, BlockPos pos, Direction side) {
        return true;
    }
}
