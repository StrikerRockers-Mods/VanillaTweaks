package com.strikerrocker.vt.proxies;

import com.strikerrocker.vt.blocks.pedestal.TESRPedestal;
import com.strikerrocker.vt.blocks.pedestal.TileEntityPedestal;
import com.strikerrocker.vt.input.KeyBindings;
import com.strikerrocker.vt.vtModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The client-side proxy for Vanilla Tweaks
 */
public class VTClientProxy extends VTCommonProxy {
    private static final Minecraft minecraft = Minecraft.getMinecraft(); //the Minecraft instance

    @Override
    @SideOnly(Side.CLIENT)
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(vtModInfo.MOD_ID + ":" + id, "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TESRPedestal());
    }

    @SideOnly(Side.CLIENT)
    public String localize(String unlocalized, Object... args) {
        return I18n.format(unlocalized, args);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        KeyBindings.init();
    }
}

