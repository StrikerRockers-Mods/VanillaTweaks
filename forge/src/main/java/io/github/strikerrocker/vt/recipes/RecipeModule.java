package io.github.strikerrocker.vt.recipes;

import io.github.strikerrocker.vt.base.ForgeModule;
import net.minecraftforge.common.ForgeConfigSpec;

public class RecipeModule extends ForgeModule {
    public RecipeModule(ForgeConfigSpec.Builder builder) {
        super("crafting", "Modification of vanilla_recipes recipes and addition of new recipes regarding vanilla_recipes", builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("vanilla_recipes", new VanillaRecipes());
    }
}