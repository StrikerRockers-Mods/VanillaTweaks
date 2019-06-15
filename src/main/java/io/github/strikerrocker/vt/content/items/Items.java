package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.items.craftingpad.ItemCraftingPad;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Items extends Feature {
    public static Item craftingPad = new ItemCraftingPad("pad");
    static boolean enablePad;
    static boolean enableSlimeBucket;
    private static Item slimeBucket = new ItemSlimeBucket("slime");
    @Override
    public void syncConfig(Configuration config, String module) {
        enablePad = config.get(module, "craftingPad", true, "Do you want a portable crafting table?").getBoolean();
        enableSlimeBucket = config.get(module, "slimeBucket", true, "Want to identity slime chunks in-game?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(craftingPad, slimeBucket);
    }

    @SubscribeEvent
    public void onModelRegister(ModelRegistryEvent event) {
        registerItemRenderer(craftingPad, 0);
        registerItemRenderer(slimeBucket, 0);
    }

    @SideOnly(Side.CLIENT)
    private void registerItemRenderer(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(VTModInfo.MODID + ":" + item.getRegistryName().getPath(), "inventory"));
    }
}
