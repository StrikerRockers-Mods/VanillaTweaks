package com.strikerrocker.vt.proxies;

import com.strikerrocker.vt.blocks.Pedestal.TESRPedestal;
import com.strikerrocker.vt.blocks.Pedestal.TileEntityPedestal;
import com.strikerrocker.vt.main.vtModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * The client-side proxy for Vanilla Tweaks
 */
@SuppressWarnings("unused")
public class VTClientProxy extends VTCommonProxy {
    private static final Minecraft minecraft = Minecraft.getMinecraft(); //the Minecraft instance

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(vtModInfo.MOD_ID + ":" + id, "inventory"));
    }

    @Override
    public String localize(String unlocalized, Object... args) {
        return I18n.format(unlocalized, args);
    }

    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TESRPedestal());
    }
}

