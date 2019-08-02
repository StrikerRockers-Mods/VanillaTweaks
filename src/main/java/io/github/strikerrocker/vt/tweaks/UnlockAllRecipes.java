package io.github.strikerrocker.vt.tweaks;

import com.google.common.collect.Lists;
import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.RecipeToast;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Deque;
import java.util.List;

public class UnlockAllRecipes extends Feature {
    private boolean unlockAllRecipes;

    private static MethodHandle findFieldGetter(Class c, String... names) {
        try {
            return MethodHandles.lookup().unreflectGetter(ReflectionHelper.findField(c, names));
        } catch (IllegalAccessException e) {
            VanillaTweaks.logger.catching(e);
            return null;
        }
    }

    @Override
    public void syncConfig(Configuration config, String category) {
        unlockAllRecipes = config.get(category, "unlockAllRecipes", true, "Want to instantly unlock all recipes?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void onRenderTickPre(TickEvent.RenderTickEvent event) {
        if (unlockAllRecipes) {
            try {
                MethodHandle toastsQueue = findFieldGetter(GuiToast.class, "toastsQueue", "field_191792_h");
                MethodHandle recipesOutputs = findFieldGetter(RecipeToast.class, "recipesOutputs", "field_193666_c");

                Deque deque = (Deque) toastsQueue.invoke(Minecraft.getMinecraft().getToastGui());
                for (Object o : deque) {
                    if (o instanceof RecipeToast) {
                        ((List) recipesOutputs.invoke(o)).clear();
                    }
                }
            } catch (Throwable t) {
                VanillaTweaks.logger.catching(t);
            }
        }
    }

    @SubscribeEvent
    public void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (unlockAllRecipes)
            event.player.unlockRecipes(Lists.newArrayList(CraftingManager.REGISTRY));
    }
}