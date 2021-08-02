package io.github.strikerrocker.vt.recipes.better_vanilla;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraftforge.common.ForgeConfigSpec;

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
    }
}
