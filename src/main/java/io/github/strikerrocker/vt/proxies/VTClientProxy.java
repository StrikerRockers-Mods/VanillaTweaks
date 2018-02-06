package io.github.strikerrocker.vt.proxies;

import io.github.strikerrocker.vt.blocks.pedestal.TESRPedestal;
import io.github.strikerrocker.vt.blocks.pedestal.TileEntityPedestal;
import io.github.strikerrocker.vt.handlers.VTInputHandler;
import io.github.strikerrocker.vt.vtModInfo;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

/**
 * The client-side proxy for Vanilla Tweaks
 */

@SideOnly(Side.CLIENT)
public class VTClientProxy extends VTCommonProxy {

    public static KeyBinding bauble;

    @Override
    public void registerKey() {
        MinecraftForge.EVENT_BUS.register(new VTInputHandler());
        bauble = new KeyBinding("key.zoom",KeyConflictContext.IN_GAME,Keyboard.KEY_Z,"key.category.vt");
        ClientRegistry.registerKeyBinding(bauble);
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(vtModInfo.MOD_ID + ":" + id, "inventory"));
    }

    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TESRPedestal());
    }
}

