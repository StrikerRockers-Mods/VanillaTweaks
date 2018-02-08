package io.github.strikerrocker.vt.proxies;

import io.github.strikerrocker.vt.blocks.pedestal.TESRPedestal;
import io.github.strikerrocker.vt.blocks.pedestal.TileEntityPedestal;
import io.github.strikerrocker.vt.vtModInfo;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The client-side proxy for Vanilla Tweaks
 */

@SideOnly(Side.CLIENT)
public class VTClientProxy extends VTCommonProxy {

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(vtModInfo.MOD_ID + ":" + id, "inventory"));
    }

    @Override
    public void regsiterRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TESRPedestal());
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        super.onInit(event);
    }
}

