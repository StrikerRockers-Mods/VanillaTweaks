package io.github.strikerrocker.vt.recipes;

import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.VT;
import io.github.strikerrocker.vt.VTModInfo;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

import static io.github.strikerrocker.vt.handlers.ConfigHandler.Miscellanious_Restart.barks;
import static io.github.strikerrocker.vt.handlers.ConfigHandler.Recipes.leather;
import static io.github.strikerrocker.vt.handlers.ConfigHandler.Recipes.useBetterChestRecipe;
import static io.github.strikerrocker.vt.handlers.ConfigHandler.VanillaTweaks.*;

public class VTConditionFactory implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        boolean value = JsonUtils.getBoolean(json, "value", true);
        String key = JsonUtils.getString(json, "type");
        if (key.equals(VTModInfo.MOD_ID + ":storage_blocks")) {
            return () -> storageBlocks == value;
        }
        if (key.equals(VTModInfo.MOD_ID + ":pedestal")) {
            return () -> pedestal == value;
        }
        if (key.equals(VTModInfo.MOD_ID + ":pad")) {
            return () -> craftingPad == value;
        }
        if (key.equals(VTModInfo.MOD_ID + ":slime")) {
            return () -> slimeChunkFinder == value;
        }
        if (key.equals(VTModInfo.MOD_ID + ":barks")) {
            return () -> barks == value;
        }
        if (key.equals(VTModInfo.MOD_ID + ":chest")) {
            return () -> useBetterChestRecipe == value;
        }
        if (key.equals(VTModInfo.MOD_ID + ":leather")) {
            return () -> leather == value;
        }
        if (key.equals(VTModInfo.MOD_ID + ":bauble")) {
            return () -> VT.baubles == value;
        }
        return null;
    }
}
