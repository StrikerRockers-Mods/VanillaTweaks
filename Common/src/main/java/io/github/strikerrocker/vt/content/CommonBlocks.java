package io.github.strikerrocker.vt.content;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CommonBlocks {
    public static final Block CHARCOAL_BLOCK = new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5f, 6.0f));
    public static final Block SUGAR_BLOCK = new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(0.5f).sound(SoundType.SAND));
    public static final Block FLINT_BLOCK = new Block(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(1.0f, 6.0f));
}