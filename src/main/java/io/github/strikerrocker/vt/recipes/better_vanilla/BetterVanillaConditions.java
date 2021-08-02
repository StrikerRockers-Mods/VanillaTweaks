package io.github.strikerrocker.vt.recipes.better_vanilla;

import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

/**
 * Adds conditions for changing vanilla recipes
 */
public record BetterVanillaConditions(String object) implements ICondition {

    private static final ResourceLocation NAME = new ResourceLocation(VanillaTweaks.MOD_ID, "better_vanilla");

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test() {
        if (object.equals("better_stairs")) return BetterVanillaRecipes.betterStairs.get();
        if (object.equals("better_stone_tools")) {
            //TODO not working
            return BetterVanillaRecipes.betterStoneTools.get();
        }
        return false;
    }

    public static class Serializer implements IConditionSerializer<BetterVanillaConditions> {
        public static final BetterVanillaConditions.Serializer INSTANCE = new BetterVanillaConditions.Serializer();

        @Override
        public void write(JsonObject json, BetterVanillaConditions value) {

        }

        @Override
        public BetterVanillaConditions read(JsonObject json) {
            return new BetterVanillaConditions(json.get("object").getAsString());
        }

        @Override
        public ResourceLocation getID() {
            return NAME;
        }
    }
}
