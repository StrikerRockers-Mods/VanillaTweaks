package io.github.strikerrocker.vt.gui.config;

import io.github.strikerrocker.vt.gui.GuiVTConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class VTGuiFactory implements IModGuiFactory
{
    @Override
    public void initialize(Minecraft minecraft)
    {
    }

    @Override
    public boolean hasConfigGui()
    {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen)
    {
        return new GuiVTConfig(parentScreen);
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return null;
    }


    public Class<? extends GuiScreen> mainConfigGuiClass()
    {
        return GuiVTConfig.class;
    }

}