package io.github.strikerrocker.vt.recipes.better;

import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.VTModInfo;
import net.minecraftforge.common.crafting.IConditionSerializer;

import java.util.function.BooleanSupplier;

public class Conditions implements IConditionSerializer {

    @Override
    public BooleanSupplier parse(JsonObject json) {
        String key = json.get("type").getAsString();
        if (key.equals(VTModInfo.MODID + ":betterStairs")) return () -> BetterRecipes.betterStairs.get();
        if (key.equals(VTModInfo.MODID + ":betterStoneTools")) return () -> BetterRecipes.betterStoneTools.get();
        return null;
    }
}
