package io.github.strikerrocker.vt.recipes.vanilla;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraftforge.common.config.Configuration;

public class VanillaRecipes extends Feature {
    static boolean betterChest;
    static boolean nametag;
    static boolean packedIce;
    static boolean string;

    @Override
    public void syncConfig(Configuration config, String module) {
        betterChest = config.get(module, "betterChestRecipe", true, "Wanna craft multiple chests at one go?").getBoolean();
        nametag = config.get(module, "nameTagRecipe", true, "Tired of not getting a nametag?").getBoolean();
        packedIce = config.get(module, "packedIceRecipe", true, "Don't want to travel to find packed ice?").getBoolean();
        string = config.get(module, "woolToStringRecipe", true, "Have wool but need string?").getBoolean();
    }
}
