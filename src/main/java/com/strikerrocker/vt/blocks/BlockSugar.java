package com.strikerrocker.vt.blocks;

import com.strikerrocker.vt.main.vt;
import com.strikerrocker.vt.main.vtModInfo;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

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
