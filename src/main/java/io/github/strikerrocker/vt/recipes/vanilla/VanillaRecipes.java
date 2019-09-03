package io.github.strikerrocker.vt.recipes.vanilla;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.content.items.ItemConditions;
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
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerRecipeSerialziers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
            CraftingHelper.register(VanillaRecipeConditions.Serializer.INSTANCE);
        }
    }
}
