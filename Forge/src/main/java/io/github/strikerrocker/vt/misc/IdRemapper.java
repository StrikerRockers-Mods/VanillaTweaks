package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.content.blocks.ForgeBlocks;
import io.github.strikerrocker.vt.content.items.ForgeItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = VanillaTweaks.MOD_ID)
public class IdRemapper {
    @SubscribeEvent
    public static void missingMappingsItems(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getMappings(VanillaTweaks.MOD_ID)) {
            switch (mapping.key.getPath()) {
                case "friedegg" -> mapping.remap(ForgeItems.FRIED_EGG.get());
                case "pad" -> mapping.remap(ForgeItems.CRAFTING_PAD.get());
                case "slime" -> mapping.remap(ForgeItems.SLIME_BUCKET.get());
                case "charcoalblock" -> mapping.remap(ForgeItems.CHARCOAL_BLOCK_ITEM.get());
                case "flintblock" -> mapping.remap(ForgeItems.FLINT_BLOCK_ITEM.get());
                case "sugarblock" -> mapping.remap(ForgeItems.SUGAR_BLOCK_ITEM.get());
            }
        }
    }

    @SubscribeEvent
    public static void missingMappingsBlocks(RegistryEvent.MissingMappings<Block> event) {
        VanillaTweaks.LOGGER.error("Block registry remapping");
        for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getMappings(VanillaTweaks.MOD_ID)) {
            switch (mapping.key.getPath()) {
                case "charcoalblock" -> mapping.remap(ForgeBlocks.CHARCOAL_BLOCK.get());
                case "flintblock" -> mapping.remap(ForgeBlocks.FLINT_BLOCK.get());
                case "sugarblock" -> mapping.remap(ForgeBlocks.SUGAR_BLOCK.get());
            }
        }
    }
}