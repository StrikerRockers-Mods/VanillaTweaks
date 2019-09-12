package io.github.strikerrocker.vt.recipes.better_vanilla;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class BetterVanillaRecipes extends Feature {
    static ForgeConfigSpec.BooleanValue betterStairs;
    static ForgeConfigSpec.BooleanValue betterStoneTools;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        betterStairs = builder
                .translation("config.vanillatweaks:betterStairs")
                .comment("Want 8 stairs rather than 4 in stairs recipe?")
                .define("betterStairs", true);
        betterStoneTools = builder
                .translation("config.vanillatweaks:betterStoneTools")
                .comment("Cobblestone used in stone tools doesn't make sense?")
                .define("betterStoneTools", true);
        //TODO cobblestone stairs and better doesn't work due to forge.
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerRecipeSerialziers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
            CraftingHelper.register(BetterVanillaConditions.Serializer.INSTANCE);
        }
    }
}
