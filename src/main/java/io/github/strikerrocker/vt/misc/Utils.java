package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.VTModInfo;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    @SideOnly(Side.CLIENT)
    public static void registerItemRenderer(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(VTModInfo.MODID + ":" + item.getRegistryName().getPath(), "inventory"));
    }
}
