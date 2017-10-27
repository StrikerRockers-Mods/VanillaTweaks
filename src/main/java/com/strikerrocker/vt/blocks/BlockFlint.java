package com.strikerrocker.vt.blocks;

import net.minecraft.block.material.Material;

public class BlockFlint extends BlockBase {

    String name;

    public BlockFlint(String name) {
        super(Material.SAND, name);
        this.name = name;
        this.setUnlocalizedName(name);
    }
}
