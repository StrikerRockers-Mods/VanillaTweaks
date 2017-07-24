package com.strikerrocker.vt.gui;

import com.strikerrocker.vt.handlers.VTConfigHandler;
import com.strikerrocker.vt.main.vtModInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

/**
 * The config GUI for Vanilla Tweaks
 */
public class GuiVTConfig extends GuiConfig {
    @SuppressWarnings("unused")
    public GuiVTConfig() {
        this(null);
    }

    public GuiVTConfig(GuiScreen parent) {
        super(parent, new ConfigElement(VTConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), vtModInfo.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(VTConfigHandler.config.toString()));
    }
}
