package io.github.strikerrocker.vt.blocks;

import io.github.strikerrocker.vt.VT;
import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.blocks.pedestal.BlockPedestal;
import io.github.strikerrocker.vt.items.VTItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains, initializes, and registers all of Craft++'s blocks
 */
@Mod.EventBusSubscriber(modid = VTModInfo.MODID)
public class VTBlocks {

    public static final BlockPedestal pedestal = new BlockPedestal();
    public static final BlockBark acaciabark = new BlockBark("acaciabark", MapColor.STONE);
    public static final BlockBark darkoakbark = new BlockBark("darkoakbark", BlockPlanks.EnumType.DARK_OAK.getMapColor());
    public static final BlockBark oakbark = new BlockBark("oakbark", BlockPlanks.EnumType.OAK.getMapColor());
    public static final BlockBark sprucebark = new BlockBark("sprucebark", BlockPlanks.EnumType.SPRUCE.getMapColor());
    public static final BlockBark junglebark = new BlockBark("junglebark", BlockPlanks.EnumType.JUNGLE.getMapColor());
    public static final BlockBark birchbark = new BlockBark("birchbark", BlockPlanks.EnumType.BIRCH.getMapColor());
    public static final BlockCharcoal charcoal = new BlockCharcoal("charcoalblock");
    private static final BlockSugar sugar = new BlockSugar("sugarblock");
    private static final Block flint = new Block(Material.SAND, MapColor.BROWN).setHardness(1.0f).setTranslationKey("flintblock").setRegistryName("flintblock");

    private static Item createItemBlock(Block block) {
        return new ItemBlock(block).setRegistryName(block.getRegistryName());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        VT.logInfo("Registering Blocks");
        event.getRegistry().registerAll(sugar, charcoal, pedestal, flint, acaciabark, darkoakbark, oakbark, sprucebark, junglebark, birchbark);
        GameRegistry.registerTileEntity(pedestal.getTileEntityClass(), pedestal.getRegistryName());
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        List<Item> itemBlocks = new ArrayList<>(Arrays.asList(createItemBlock(sugar), createItemBlock(pedestal), createItemBlock(flint), createItemBlock(charcoal), createItemBlock(acaciabark),
                createItemBlock(darkoakbark), createItemBlock(oakbark), createItemBlock(sprucebark), createItemBlock(junglebark), createItemBlock(birchbark)));
        itemBlocks.forEach(item -> {
            VTItems.items.add(item);
            event.getRegistry().register(item);
        });
        VT.logInfo("Registering ItemBlocks");
    }
}