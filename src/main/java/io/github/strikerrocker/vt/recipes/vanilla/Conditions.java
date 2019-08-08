package io.github.strikerrocker.vt.recipes.vanilla;

import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.VTModInfo;
import net.minecraftforge.common.crafting.IConditionSerializer;

import java.util.function.BooleanSupplier;

public class Conditions implements IConditionSerializer {

    @Override
    public BooleanSupplier parse(JsonObject json) {
        String key = json.get("type").getAsString();
        if (key.equals(VTModInfo.MODID + ":chest")) return () -> VanillaRecipes.betterChest.get();
        if (key.equals(VTModInfo.MODID + ":nametag")) return () -> VanillaRecipes.nametag.get();
        if (key.equals(VTModInfo.MODID + ":string")) return () -> VanillaRecipes.string.get();
        return null;
    }
}
