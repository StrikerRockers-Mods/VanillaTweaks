package com.strikerrocker.vt.blocks;

import net.minecraft.block.material.Material;

/**
 * Created by thari on 22/07/2017.
 */
public class BlockCharcoal extends BlockBase {

    String name;

    public BlockCharcoal(String name) {
        super(Material.ROCK, name);
        this.name = name;
        this.setUnlocalizedName(name);
        this.setHardness(1.0F);
    }
}
