package io.github.strikerrocker.vt.recipes;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraftforge.common.ForgeConfigSpec;

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
}
