package io.github.strikerrocker.vt.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import static io.github.strikerrocker.vt.vt.proxy;

class BlockBase extends Block
{

    String name;

    BlockBase(Material material, String name, MapColor mapColor) {
        super(material, mapColor);

        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    public void registerItemModel(Item item) {
        proxy.registerItemRenderer(item, 0, name);
    }

    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }
}