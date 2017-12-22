package com.strikerrocker.vt.blocks;

import com.strikerrocker.vt.items.ItemModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import static com.strikerrocker.vt.vt.proxy;

/**
 * Created by thari on 22/07/2017.
 */
public class BlockBase extends Block implements ItemModelProvider {

    protected String name;

    public BlockBase(Material material, String name) {
        super(material);
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    @Override
    public void registerItemModel(Item item) {
        proxy.registerItemRenderer(item, 0, name);
    }

}