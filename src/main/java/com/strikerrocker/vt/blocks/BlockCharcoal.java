package com.strikerrocker.vt.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by thari on 22/07/2017.
 */
public class
BlockCharcoal extends BlockBase {

    String name;

    public BlockCharcoal(String name) {
        super(Material.ROCK, name, MapColor.BLACK);
        this.name = name;
        this.setUnlocalizedName(name);
        this.setHardness(1.0F);

    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return true;
    }


}
