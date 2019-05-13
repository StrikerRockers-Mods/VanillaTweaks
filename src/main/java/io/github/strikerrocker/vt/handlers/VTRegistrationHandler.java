package io.github.strikerrocker.vt.handlers;

import io.github.strikerrocker.vt.VT;
import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.blocks.pedestal.TESRPedestal;
import io.github.strikerrocker.vt.blocks.pedestal.TileEntityPedestal;
import io.github.strikerrocker.vt.entities.EntityDynamite;
import io.github.strikerrocker.vt.entities.EntitySitting;
import io.github.strikerrocker.vt.items.VTItems;
import io.github.strikerrocker.vt.misc.VTUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static io.github.strikerrocker.vt.VT.proxy;

@Mod.EventBusSubscriber(modid = VTModInfo.MODID)
public class VTRegistrationHandler {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        VT.logInfo("Registering Models");
        for (Item item : VTItems.items) {
            proxy.registerItemRenderer(item, 0, item.getRegistryName().getPath());
        }
        RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, manager -> new RenderSnowball<>(manager, VTItems.dynamite, Minecraft.getMinecraft().getRenderItem()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TESRPedestal());
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        VT.logInfo("Registering Entities");
        event.getRegistry().register(VTUtils.createEntityEntry("dynamite", EntityDynamite.class, 64, 1, false));
        event.getRegistry().register(VTUtils.createEntityEntry("entity_sit", EntitySitting.class, 256, 1, false));
    }
}
