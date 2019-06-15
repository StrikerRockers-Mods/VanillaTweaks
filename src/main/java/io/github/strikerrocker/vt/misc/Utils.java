package io.github.strikerrocker.vt.misc;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
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

    public static FakePlayer getFakePlayer(World world) {
        if (world instanceof WorldServer)
            return FakePlayerFactory.getMinecraft((WorldServer) world);
        return null;
    }
}
