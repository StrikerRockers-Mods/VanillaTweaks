package io.github.strikerrocker.vt.misc;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Utils {
    private Utils() {
    }

    public static Property get(Configuration config, String category, String key, double defaultValue, String comment, boolean slider) {
        Property property = config.get(category, key, defaultValue, comment);
        if (slider && FMLCommonHandler.instance().getEffectiveSide().isClient())
            return property.setConfigEntryClass(GuiConfigEntries.NumberSliderEntry.class);
        return property;
    }
}
