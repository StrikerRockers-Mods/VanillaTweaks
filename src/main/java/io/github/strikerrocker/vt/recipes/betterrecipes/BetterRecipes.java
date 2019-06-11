package io.github.strikerrocker.vt.recipes.betterrecipes;

import io.github.strikerrocker.vt.base.Feature;
import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.misc.Utils;
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
        betterChest = Utils.get(config, module, "betterChestRecipe", true, "Wanna craft multiple chests at one go?");
        nametag = Utils.get(config, module, "nameTagRecipe", true, "Tired of not getting a nametag?");
        packedIce = Utils.get(config, module, "packedIceRecipe", true, "Don't want to travel to find packed ice?");
        string = Utils.get(config, module, "woolToStringRecipe", true, "Have wool but need string?");
    }
}
