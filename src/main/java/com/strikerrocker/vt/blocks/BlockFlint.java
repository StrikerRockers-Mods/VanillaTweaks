package com.strikerrocker.vt.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockFlint extends BlockBase {

    String name;

    public BlockFlint(String name) {
        super(Material.SAND, name, MapColor.BROWN);
        this.name = name;
        this.setUnlocalizedName(name);
        this.setHardness(1.0F);
    }
}
