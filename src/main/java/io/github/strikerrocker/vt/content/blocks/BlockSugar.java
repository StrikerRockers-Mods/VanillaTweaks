package io.github.strikerrocker.vt.content.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

class BlockSugar extends Block {
    BlockSugar(String name) {
        super(Material.SAND, MapColor.WHITE_STAINED_HARDENED_CLAY);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setSoundType(SoundType.SAND);
        setHardness(0.5F);
    }
}
