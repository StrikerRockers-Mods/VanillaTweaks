package io.github.strikerrocker.vt.content.blocks;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.blocks.pedestal.BlockPedestal;
import io.github.strikerrocker.vt.content.blocks.pedestal.TESRPedestal;
import io.github.strikerrocker.vt.content.blocks.pedestal.TileEntityPedestal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

public class Blocks extends Feature {
    public static final Block pedestal = new BlockPedestal();
    private static final BlockCharcoal charcoal = new BlockCharcoal("charcoalblock");
    private static final Block sugar = new BlockSugar("sugarblock");
    private static final Block flint = new Block(Block.Properties.create(Material.SAND, MaterialColor.BROWN).hardnessAndResistance(1.0f, 10.0f)).setRegistryName("flintblock");
    static boolean enableBarkBlocks;
    static boolean enableStorageBlocks;
    static boolean enablePedestal;
    private static Block[] blocks = new Block[]{charcoal, sugar, flint, pedestal};

    /*@Override
    public void syncConfig(Configuration config, String category) {
        enableBarkBlocks = config.get(category, "enableBarkBlocks", true, "Want a block which has bark texture of logs in all sides?").getBoolean();
        enableStorageBlocks = config.get(category, "enableStorageBlocks", true, "Want block forms of flint, charcoal and sugar?").getBoolean();
        enablePedestal = config.get(category, "enablePedestal", true, "Want to showcase your treasure but item frame doesn't satisfy you?").getBoolean();
    }*/

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(blocks);
//TODO GameRegistry.registerTileEntity(TileEntityPedestal.class, pedestal.getRegistryName());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onModelRegister(ModelRegistryEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TESRPedestal());
    }

    @SubscribeEvent
    public void onFurnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
        Item item = event.getItemStack().getItem();
        if (item == Item.getItemFromBlock(charcoal))
            event.setBurnTime(16000);
        if (item == Item.getItemFromBlock(net.minecraft.block.Blocks.TORCH))
            event.setBurnTime(400);
    }

    /*@Override
    public void registerPacket(SimpleNetworkWrapper network) {
        network.registerMessage(new PacketUpdatePedestal.Handler(), PacketUpdatePedestal.class, 0, Side.CLIENT);
    }*/

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public void onRegisterItemBlocks(RegistryEvent.Register<Item> event) {
            Arrays.stream(blocks).map(block -> new BlockItem(block, new Item.Properties()).setRegistryName(block.getRegistryName())).forEach(item -> event.getRegistry().register(item));
        }
    }
}