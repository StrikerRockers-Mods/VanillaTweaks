package io.github.strikerrocker.vt.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCharcoal extends BlockBase {

    public BlockCharcoal(String name) {
        super(Material.ROCK, name, MapColor.BLACK);
        this.setUnlocalizedName(name);
        this.setHardness(1.0F);
        this.name = name;
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return true;
    }


}
