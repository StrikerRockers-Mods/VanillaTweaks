package io.github.strikerrocker.vt.recipes.better_vanilla;

import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.VTModInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class BetterVanillaConditions implements ICondition {

    private static final ResourceLocation NAME = new ResourceLocation(VTModInfo.MODID, "better_vanilla");
    private final String object;

    private BetterVanillaConditions(String object) {
        this.object = object;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test() {
        if (object.equals("better_stairs")) return BetterVanillaRecipes.betterStairs.get();
        if (object.equals("better_stone_tools"))
            return BetterVanillaRecipes.betterStoneTools.get();//!ModList.get().isLoaded("quark");
        return false;
    }

    public static class Serializer implements IConditionSerializer<BetterVanillaConditions> {
        static final BetterVanillaConditions.Serializer INSTANCE = new BetterVanillaConditions.Serializer();

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
