package com.strikerrocker.vt.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import static com.strikerrocker.vt.vt.proxy;

/**
 * Created by thari on 22/07/2017.
 */
public class BlockBase extends Block {

    protected String name;

    public BlockBase(Material material, String name) {
        super(material);

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