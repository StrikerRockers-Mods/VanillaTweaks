package io.github.strikerrocker.vt.recipes.vanilla;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class VanillaRecipes extends Feature {
    static ForgeConfigSpec.BooleanValue betterChest;
    static ForgeConfigSpec.BooleanValue nametag;
    static ForgeConfigSpec.BooleanValue string;
    static ForgeConfigSpec.BooleanValue betterRepeater;
    static ForgeConfigSpec.BooleanValue betterTrappedChestRecipe;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        betterChest = builder
                .translation("config.vanillatweaks:betterChest")
                .comment("Wanna craft multiple chests at one go?")
                .define("betterChest", true);
        nametag = builder
                .translation("config.vanillatweaks:nametag")
                .comment("Tired of not having a nametag?")
                .define("nametag", true);
        string = builder
                .translation("config.vanillatweaks:string")
                .comment("Have wool but need string?")
                .define("string", true);
        betterRepeater = builder
                .translation("config.vanillatweaks:betterRepeater")
                .comment("Want an easier way to craft repeater?")
                .define("betterRepeater", true);
        betterTrappedChestRecipe = builder
                .translation("config.vanillatweaks:betterTrappedChestRecipe")
                .comment("Wanna easier recipe for trapped chest?")
                .define("betterTrappedChestRecipe", true);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
            CraftingHelper.register(VanillaRecipeConditions.Serializer.INSTANCE);
        }
    }
}
