package io.github.strikerrocker.vt.recipes.betterrecipes;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.base.Module;
import net.minecraftforge.common.config.Configuration;

public class BetterRecipes extends Feature {
    static boolean betterChest;
    static boolean nametag;
    static boolean packedIce;
    static boolean string;

    public BetterRecipes(Module module) {
        super(module);
    }

    @Override
    public void syncConfig(Configuration config, String module) {
        betterChest = config.get(module, "betterChestRecipe", true, "Wanna craft multiple chests at one go?").getBoolean();
        nametag = config.get(module, "nameTagRecipe", true, "Tired of not getting a nametag?").getBoolean();
        packedIce = config.get(module, "packedIceRecipe", true, "Don't want to travel to find packed ice?").getBoolean();
        string = config.get(module, "woolToStringRecipe", true, "Have wool but need string?").getBoolean();
    }
}
