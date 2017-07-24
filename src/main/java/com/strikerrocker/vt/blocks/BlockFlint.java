package com.strikerrocker.vt.blocks;

import net.minecraft.block.material.Material;

/**
 * Created by thari on 22/07/2017.
 */
public class BlockFlint extends BlockBase {

    String name;

    public BlockFlint(String name) {
        super(Material.SAND, name);
        this.name = name;
        this.setUnlocalizedName(name);
    }
}
