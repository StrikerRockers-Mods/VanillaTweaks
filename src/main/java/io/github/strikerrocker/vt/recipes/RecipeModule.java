package io.github.strikerrocker.vt.recipes;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.recipes.better_vanilla.BetterVanillaConditions;
import io.github.strikerrocker.vt.recipes.better_vanilla.BetterVanillaRecipes;
import io.github.strikerrocker.vt.recipes.vanilla.VanillaRecipeConditions;
import io.github.strikerrocker.vt.recipes.vanilla.VanillaRecipes;
import net.minecraft.item.crafting.IRecipeSerializer;
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
        registerFeature("better_recipes", new BetterVanillaRecipes());
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
            CraftingHelper.register(VanillaRecipeConditions.Serializer.INSTANCE);
            CraftingHelper.register(BetterVanillaConditions.Serializer.INSTANCE);
        }
    }
}
