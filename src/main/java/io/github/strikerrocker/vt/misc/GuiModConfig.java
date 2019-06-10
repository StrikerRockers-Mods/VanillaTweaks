package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class GuiModConfig extends net.minecraftforge.fml.client.config.GuiConfig {
    @SuppressWarnings("unused")
    public GuiModConfig() {
        this(null);
    }

    GuiModConfig(GuiScreen parent) {
        super(parent, new ConfigElement(VanillaTweaks.instance.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), VTModInfo.MODID, false, false, GuiModConfig.getAbridgedConfigPath(VanillaTweaks.instance.getConfig().toString()));
    }
}
