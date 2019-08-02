package io.github.strikerrocker.vt.recipes.vanilla;

import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.VTModInfo;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public class Conditions implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        String key = JsonUtils.getString(json, "type");
        if (key.equals(VTModInfo.MODID + ":chest")) return () -> VanillaRecipes.betterChest;
        if (key.equals(VTModInfo.MODID + ":nametag")) return () -> VanillaRecipes.nametag;
        if (key.equals(VTModInfo.MODID + ":packed_ice")) return () -> VanillaRecipes.packedIce;
        if (key.equals(VTModInfo.MODID + ":string")) return () -> VanillaRecipes.string;
        return null;
    }
}
