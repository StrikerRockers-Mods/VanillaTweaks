package io.github.strikerrocker.vt.content.blocks;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.blocks.pedestal.BlockPedestal;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalContainer;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalTileEntity;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalTileEntityRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

public class Blocks extends Feature {
    public static final Block pedestal = new BlockPedestal();
    private static final CharcoalBlock charcoal = new CharcoalBlock("charcoalblock");
    private static final Block sugar = new Block(Block.Properties.create(Material.SAND, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(0.5f).sound(SoundType.SAND)).setRegistryName("sugarblock");
    private static final Block flint = new Block(Block.Properties.create(Material.SAND, MaterialColor.BROWN).hardnessAndResistance(1.0f, 10.0f)).setRegistryName("flintblock");
    static ForgeConfigSpec.BooleanValue enableStorageBlocks;
    static ForgeConfigSpec.BooleanValue enablePedestal;
    private static Block[] blocks = new Block[]{charcoal, sugar, flint, pedestal};

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enablePedestal = builder
                .translation("config.vanillatweaks:enablePedestal")
                .comment("Want to showcase your treasure but item frame doesn't satisfy you?")
                .define("enablePedestal", true);
        enableStorageBlocks = builder
                .translation("config.vanillatweaks:enableStorageBlocks")
                .comment("Want block forms of flint, charcoal and sugar?")
                .define("enableStorageBlocks", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(blocks);
        //TODO GameRegistry.registerTileEntity(PedestalTileEntity.class, pedestal.getRegistryName());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onModelRegister(ModelRegistryEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(PedestalTileEntity.class, new PedestalTileEntityRenderer());
    }

    @SubscribeEvent
    public void onFurnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
        Item item = event.getItemStack().getItem();
        if (item == charcoal.asItem())
            event.setBurnTime(16000);
        if (item == net.minecraft.block.Blocks.TORCH.asItem())
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
            Arrays.stream(blocks).map(block -> new BlockItem(block, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(block.getRegistryName())).forEach(item -> event.getRegistry().register(item));
        }

        @SubscribeEvent
        public void onRegisterContainers(RegistryEvent.Register<ContainerType<?>> event) {
            event.getRegistry().register(IForgeContainerType.create(((windowId, inv, data) -> new PedestalContainer(windowId, inv, data.readBlockPos()))).setRegistryName("crafting_pad"));
        }
    }
}