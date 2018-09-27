package io.github.strikerrocker.vt.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

class BlockSugar extends Block {
    public BlockSugar(String name) {
        super(Material.SAND, MapColor.WHITE_STAINED_HARDENED_CLAY);
        this.setTranslationKey(name);
        this.setSoundType(SoundType.SAND);
        this.setHardness(0.5F);
    }
}
