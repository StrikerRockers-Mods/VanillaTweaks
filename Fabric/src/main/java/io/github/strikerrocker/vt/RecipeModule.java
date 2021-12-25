package io.github.strikerrocker.vt;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.base.FabricModule;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeModule extends FabricModule {
    public static final Map<ResourceLocation, JsonObject> recipes = new HashMap<>();

    /**
     * Creates and registers shaped recipe via code
     */
    public static void createShapedRecipeJson(List<Character> keys, List<ResourceLocation> items, List<String> type, List<String> pattern, ResourceLocation output, int count) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shaped");
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(pattern.get(0));
        jsonArray.add(pattern.get(1));
        jsonArray.add(pattern.get(2));

        json.add("pattern", jsonArray);

        JsonObject individualKey;
        JsonObject keyList = new JsonObject();

        for (int i = 0; i < keys.size(); ++i) {
            individualKey = new JsonObject();
            individualKey.addProperty(type.get(i), items.get(i).toString());
            keyList.add(keys.get(i) + "", individualKey);
        }
        json.add("key", keyList);

        JsonObject result = new JsonObject();
        result.addProperty("item", output.toString());
        result.addProperty("count", count);
        json.add("result", result);
        recipes.put(output, json);
    }

    /**
     * Creates and registers shapeless recipe via code
     */
    public static void createShapelessRecipeJson(List<ResourceLocation> ingredients, List<String> types
            , ResourceLocation output, int count) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shapeless");
        JsonArray ingredientArray = new JsonArray();
        for (int i = 0; i < ingredients.size(); i++) {
            String type = types.get(i);
            JsonObject item = new JsonObject();
            item.addProperty(type, ingredients.get(i).toString());
            ingredientArray.add(item);
        }
        json.add("ingredients", ingredientArray);
        JsonObject result = new JsonObject();
        result.addProperty("item", output.toString());
        result.addProperty("count", count);
        json.add("result", result);
        recipes.put(output, json);
    }

    @Override
    public void addFeatures() {
        if (VanillaTweaksFabric.config.recipe.betterChestRecipe)
            createShapedRecipeJson(Lists.newArrayList('O'), Lists.newArrayList(new ResourceLocation("logs")), Lists.newArrayList("tag"),
                    Lists.newArrayList(
                            "OOO",
                            "O O",
                            "OOO"
                    ), new ResourceLocation("minecraft:chest"), 4);
        if (VanillaTweaksFabric.config.recipe.nameTag) {
            createShapedRecipeJson(Lists.newArrayList('I', 'P'), Lists.newArrayList(new ResourceLocation("iron_ingot"), new ResourceLocation("paper")),
                    Lists.newArrayList("item", "item"), Lists.newArrayList(
                            "  I",
                            " P ",
                            "P  "
                    ), new ResourceLocation("name_tag"), 1);
        }
        if (VanillaTweaksFabric.config.recipe.woolToString)
            createShapelessRecipeJson(Lists.newArrayList(new ResourceLocation("wool")), Lists.newArrayList("tag"), new ResourceLocation("string"), 4);
        if (VanillaTweaksFabric.config.recipe.betterRepeater)
            createShapedRecipeJson(Lists.newArrayList('S', 'R', 'O'), Lists.newArrayList(new ResourceLocation("stone"), new ResourceLocation("redstone"), new ResourceLocation("stick")),
                    Lists.newArrayList("item", "item", "item"), Lists.newArrayList(
                            "R R",
                            "ORO",
                            "SSS"
                    ), new ResourceLocation("repeater"), 1);
        if (VanillaTweaksFabric.config.recipe.betterTrappedChestRecipe)
            createShapedRecipeJson(Lists.newArrayList('P', 'H'), Lists.newArrayList(new ResourceLocation("minecraft:planks"), new ResourceLocation("tripwire_hook")),
                    Lists.newArrayList("tag", "item"), Lists.newArrayList(
                            "PPP",
                            "PHP",
                            "PPP"
                    ), new ResourceLocation("trapped_chest"), 1);
    }
}