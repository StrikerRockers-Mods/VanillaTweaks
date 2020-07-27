package io.github.strikerrocker.vt.recipes;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.recipes.better_vanilla.BetterVanillaRecipes;
import io.github.strikerrocker.vt.recipes.vanilla.VanillaRecipes;
import net.minecraftforge.common.ForgeConfigSpec;

public class RecipeModule extends Module {
    public RecipeModule(ForgeConfigSpec.Builder builder) {
        super("crafting", "Modification of vanilla_recipes recipes and addition of new recipes regarding vanilla_recipes", true, builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("vanilla_recipes", new VanillaRecipes());
        registerFeature("better_recipes", new BetterVanillaRecipes());
    }
}
