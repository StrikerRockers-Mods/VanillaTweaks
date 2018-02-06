package io.github.strikerrocker.vt.handlers;

import io.github.strikerrocker.vt.blocks.VTBlocks;
import io.github.strikerrocker.vt.compat.baubles.BaubleTools;
import io.github.strikerrocker.vt.enchantments.VTEnchantments;
import io.github.strikerrocker.vt.items.VTItems;
import io.github.strikerrocker.vt.vt;
import io.github.strikerrocker.vt.vtModInfo;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = vtModInfo.MOD_ID)
public class VTRegistrationHandler {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        VTItems.register(event.getRegistry());
        VTBlocks.registerItemBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        VTBlocks.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerEnchantment(RegistryEvent.Register<Enchantment> event) {
        VTEnchantments.init(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        VTItems.registerModels();
        VTBlocks.registerModels();
        if (vt.baubles)
            BaubleTools.initModel(VTItems.bb);
    }


}
