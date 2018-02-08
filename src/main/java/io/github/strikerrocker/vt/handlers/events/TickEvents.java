package io.github.strikerrocker.vt.handlers.events;

import io.github.strikerrocker.vt.capabilities.CapabilitySelfPlanting;
import io.github.strikerrocker.vt.enchantments.EntityTickingEnchantment;
import io.github.strikerrocker.vt.enchantments.VTEnchantmentBase;
import io.github.strikerrocker.vt.enchantments.VTEnchantments;
import io.github.strikerrocker.vt.handlers.VTConfigHandler;
import io.github.strikerrocker.vt.misc.VTUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.RecipeToast;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EntitySelectors;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.invoke.MethodHandle;
import java.util.Deque;
import java.util.List;

public class TickEvents {

    private final MethodHandle TOASTS_QUEUE = VTUtils.findFieldGetter(GuiToast.class, "toastsQueue", "field_191792_h");
    private final MethodHandle RECIPES_OUTPUTS = VTUtils.findFieldGetter(RecipeToast.class, "recipesOutputs", "field_193666_c");
    /**
     * Allows thrown seeds to plant themselves in farmland, and gives the Homing enchantment functionality
     *
     * @param event The WorldTickEvent
     */
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (VTConfigHandler.autoSeedPlanting && !event.world.isRemote) {
            World world = event.world;
            List<EntityItem> entityItems = world.getEntities(EntityItem.class, EntitySelectors.IS_ALIVE);
            entityItems.stream().filter(entityItem -> entityItem.hasCapability(CapabilitySelfPlanting.CAPABILITY_SELF_PLANTING, null)).forEach(entityItem -> entityItem.getCapability(CapabilitySelfPlanting.CAPABILITY_SELF_PLANTING, null).handlePlantingLogic(entityItem));

            for (Object entityObject : world.getEntities(Entity.class, EntitySelectors.IS_ALIVE))
                VTEnchantmentBase.cppEnchantments.stream().filter(cppEnchantment -> cppEnchantment.getClass().isAnnotationPresent(EntityTickingEnchantment.class)).forEach(cppEnchantment -> cppEnchantment.performAction((Entity) entityObject, null));
        }

    }

    /**
     * Syncs up motion-affecting enchantments to the client
     *
     * @param event The ClientTickEvent
     */

    @SubscribeEvent
    public void onClientTick(TickEvent.WorldTickEvent event) {
        World world = event.world;
        if (world != null) {
            List<Entity> arrows = world.getEntities(EntityArrow.class, EntitySelectors.IS_ALIVE);
            for (Entity arrow : arrows)
                VTEnchantments.performAction("homing", arrow, null);
            List<Entity> xpOrbs = world.getEntities(EntityXPOrb.class, EntitySelectors.IS_ALIVE);
            for (Entity xpOrb : xpOrbs)
                VTEnchantments.performAction("veteran", xpOrb, null);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderTickPre(TickEvent.RenderTickEvent event) {
        try {
            Deque deque = (Deque) TOASTS_QUEUE.invoke(Minecraft.getMinecraft().getToastGui());
            for (Object o : deque) {
                if (o instanceof RecipeToast) {
                    ((List) RECIPES_OUTPUTS.invoke(o)).clear();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
