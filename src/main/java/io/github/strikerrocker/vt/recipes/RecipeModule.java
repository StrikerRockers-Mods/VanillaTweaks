package io.github.strikerrocker.vt.recipes;

import io.github.strikerrocker.vt.base.Module;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class RecipeModule extends Module {
    public RecipeModule(ForgeConfigSpec.Builder builder) {
        super("crafting", "Modification of vanilla_recipes recipes and addition of new recipes regarding vanilla_recipes", builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("vanilla_recipes", new VanillaRecipes());
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
            CraftingHelper.register(VanillaRecipeConditions.Serializer.INSTANCE);
        }
    }
}
