package io.github.strikerrocker.vt.handlers;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.blocks.VTBlocks;
import io.github.strikerrocker.vt.enchantments.VTEnchantments;
import io.github.strikerrocker.vt.entities.EntityDynamite;
import io.github.strikerrocker.vt.entities.EntitySitting;
import io.github.strikerrocker.vt.items.VTItems;
import io.github.strikerrocker.vt.misc.VTUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static io.github.strikerrocker.vt.VT.proxy;

@Mod.EventBusSubscriber(modid = VTModInfo.MOD_ID)
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
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item : VTItems.items) {
            proxy.registerItemRenderer(item, 0, item.getRegistryName().getPath());
        }
        RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, createRenderFactoryForSnowball(VTItems.dynamite));
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(VTUtils.createEntityEntry("dynamite", EntityDynamite.class, 64, 1, false));
        event.getRegistry().register(VTUtils.createEntityEntry("entity_sit", EntitySitting.class, 256, 20, false));
    }

    @SideOnly(Side.CLIENT)
    private static <T extends Entity> IRenderFactory<T> createRenderFactoryForSnowball(final Item itemToRender) {
        return manager -> new RenderSnowball<>(manager, itemToRender, Minecraft.getMinecraft().getRenderItem());
    }
}
