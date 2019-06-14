package io.github.strikerrocker.vt.recipes;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.recipes.betterrecipes.BetterRecipes;

public class RecipeModule extends Module {
    public RecipeModule() {
        super("Crafting", "Modification of vanilla_recipes recipes and addition of new recipes regarding vanilla_recipes", true);
    }

    @Override
    public void addFeatures() {
        registerFeature(new BetterRecipes());
    }
}
