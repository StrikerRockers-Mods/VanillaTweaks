package io.github.strikerrocker.vt.recipes.vanilla;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraftforge.common.config.Configuration;

public class VanillaRecipes extends Feature {
    static boolean betterChest;
    static boolean nametag;
    static boolean packedIce;
    static boolean string;

    @Override
    public void syncConfig(Configuration config, String category) {
        betterChest = config.get(category, "betterChestRecipe", true, "Wanna craft multiple chests at one go?").getBoolean();
        nametag = config.get(category, "nameTagRecipe", true, "Tired of not getting a nametag?").getBoolean();
        packedIce = config.get(category, "packedIceRecipe", true, "Don't want to travel to find packed ice?").getBoolean();
        string = config.get(category, "woolToStringRecipe", true, "Have wool but need string?").getBoolean();
    }
}
