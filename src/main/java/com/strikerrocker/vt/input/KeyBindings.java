package com.strikerrocker.vt.input;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindings {
    public static KeyBinding bauble;
    public static void init(){
        bauble = new KeyBinding("key.vt", Keyboard.KEY_Z,"key.category.vt");
        ClientRegistry.registerKeyBinding(bauble);
    }


}
