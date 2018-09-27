package io.github.strikerrocker.vt.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCharcoal extends Block {

    public BlockCharcoal(String name) {
        super(Material.ROCK, MapColor.BLACK);
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setHardness(1.0F);
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return true;
    }
}
