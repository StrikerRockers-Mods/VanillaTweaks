package com.strikerrocker.vt.input;

import com.strikerrocker.vt.vtModInfo;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindings {
    private static final String categoryName = vtModInfo.MOD_ID + " (" + vtModInfo.NAME + ')';
    public static KeyBinding bauble = new KeyBinding("key.vt",Keyboard.KEY_Z,categoryName);
    public static void init(){
        ClientRegistry.registerKeyBinding(bauble);
    }
}