package io.github.strikerrocker.vt.recipes;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.recipes.betterrecipes.BetterRecipes;

public class Recipe extends Module {
    public Recipe() {
        super("Recipes", "Modification of vanilla_recipes recipes and addition of new recipes regarding vanilla_recipes", true);
    }

    @Override
    public void addFeatures() {
        registerFeature(new BetterRecipes(this));
    }
}
