package com.strikerrocker.vt.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockFlint extends BlockBase {

    public BlockFlint(String name) {
        super(Material.SAND, name, MapColor.BROWN);
        this.setUnlocalizedName(name);
        this.setHardness(1.0F);
    }
}
