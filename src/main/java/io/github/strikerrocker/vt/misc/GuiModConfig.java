package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiModConfig extends net.minecraftforge.fml.client.config.GuiConfig {
    @SuppressWarnings("unused")
    public GuiModConfig() {
        this(null);
    }

    GuiModConfig(GuiScreen parent) {
        super(parent, getConfigElements(), VTModInfo.MODID, false, false, "VanillaTweaks");
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> configElements = new ArrayList<>();

        VanillaTweaks.modules.forEach(module -> configElements.addAll(new ConfigElement(module.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements()));
        return configElements;
    }
}
