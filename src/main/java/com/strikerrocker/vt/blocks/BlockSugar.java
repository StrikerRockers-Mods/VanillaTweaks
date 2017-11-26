package com.strikerrocker.vt.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSugar extends BlockBase {

    String name;

    public BlockSugar(String name) {
        super(Material.SAND, name);
        this.name = name;
        this.setUnlocalizedName(name);
        this.setSoundType(SoundType.SAND);
        this.setHardness(0.5F);
    }

}
