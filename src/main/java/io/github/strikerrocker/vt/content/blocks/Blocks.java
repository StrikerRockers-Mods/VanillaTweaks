package io.github.strikerrocker.vt.content.blocks;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.blocks.pedestal.BlockPedestal;
import io.github.strikerrocker.vt.content.blocks.pedestal.PacketUpdatePedestal;
import io.github.strikerrocker.vt.content.blocks.pedestal.TESRPedestal;
import io.github.strikerrocker.vt.content.blocks.pedestal.TileEntityPedestal;
import io.github.strikerrocker.vt.misc.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

public class Blocks extends Feature {
    public static final Block pedestal = new BlockPedestal();
    private static final BlockCharcoal charcoal = new BlockCharcoal("charcoalblock");
    //Remove bark blocks in 1.13 and above.
    private static final BlockBark acaciaBark = new BlockBark("acaciabark", BlockPlanks.EnumType.ACACIA.getMapColor());
    private static final BlockBark darkOakBark = new BlockBark("darkoakbark", BlockPlanks.EnumType.DARK_OAK.getMapColor());
    private static final BlockBark oakBark = new BlockBark("oakbark", BlockPlanks.EnumType.OAK.getMapColor());
    private static final BlockBark spruceBark = new BlockBark("sprucebark", BlockPlanks.EnumType.SPRUCE.getMapColor());
    private static final BlockBark jungleBark = new BlockBark("junglebark", BlockPlanks.EnumType.JUNGLE.getMapColor());
    private static final BlockBark birchBark = new BlockBark("birchbark", BlockPlanks.EnumType.BIRCH.getMapColor());
    private static final Block sugar = new BlockSugar("sugarblock");
    private static final Block flint = new Block(Material.SAND, MapColor.BROWN).setHardness(1.0f).setResistance(10.0f).setTranslationKey("flintblock").setRegistryName("flintblock").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    static boolean enableBarkBlocks;
    static boolean enableStorageBlocks;
    static boolean enablePedestal;
    private static Block[] blocks = new Block[]{acaciaBark, darkOakBark, oakBark, spruceBark, jungleBark, birchBark, charcoal, sugar, flint, pedestal};

    @Override
    public void syncConfig(Configuration config, String category) {
        enableBarkBlocks = config.get(category, "enableBarkBlocks", true, "Want a block which has bark texture of logs in all sides?").getBoolean();
        enableStorageBlocks = config.get(category, "enableStorageBlocks", true, "Want block forms of flint, charcoal and sugar?").getBoolean();
        enablePedestal = config.get(category, "enablePedestal", true, "Want to showcase your treasure but item frame doesn't satisfy you?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(blocks);
        GameRegistry.registerTileEntity(TileEntityPedestal.class, pedestal.getRegistryName());
    }

    @SubscribeEvent
    public void onRegisterItemBlocks(RegistryEvent.Register<Item> event) {
        Arrays.stream(blocks).map(block -> new ItemBlock(block).setRegistryName(block.getRegistryName())).forEach(item -> event.getRegistry().register(item));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onModelRegister(ModelRegistryEvent event) {
        Arrays.stream(blocks).map(Item::getItemFromBlock).forEach(item -> Utils.registerItemRenderer(item, 0));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TESRPedestal());
    }

    @Override
    public void registerPacket(SimpleNetworkWrapper network) {
        network.registerMessage(new PacketUpdatePedestal.Handler(), PacketUpdatePedestal.class, 0, Side.CLIENT);
    }

    @SubscribeEvent
    public void onFurnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
        Item item = event.getItemStack().getItem();
        if (item == Item.getItemFromBlock(charcoal))
            event.setBurnTime(16000);
        if (item == Item.getItemFromBlock(net.minecraft.init.Blocks.TORCH))
            event.setBurnTime(400);
        if (item == Item.getItemFromBlock(acaciaBark) || item == Item.getItemFromBlock(birchBark) || item == Item.getItemFromBlock(darkOakBark)
                || item == Item.getItemFromBlock(jungleBark) || item == Item.getItemFromBlock(oakBark) || item == Item.getItemFromBlock(spruceBark))
            event.setBurnTime(300);
    }
}