package io.github.strikerrocker.vt.recipes;

import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.VT;
import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.handlers.ConfigHandler;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;


public class VTConditionFactory implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        boolean value = JsonUtils.getBoolean(json, "value", true);
        String key = JsonUtils.getString(json, "type");
        if (key.equals(VTModInfo.MODID + ":storage_blocks")) {
            return () -> ConfigHandler.vanilla_tweaks.storageBlocks == value;
        }
        if (key.equals(VTModInfo.MODID + ":pedestal")) {
            return () -> ConfigHandler.vanilla_tweaks.pedestal == value;
        }
        if (key.equals(VTModInfo.MODID + ":pad")) {
            return () -> ConfigHandler.vanilla_tweaks.craftingPad == value;
        }
        if (key.equals(VTModInfo.MODID + ":slime")) {
            return () -> ConfigHandler.vanilla_tweaks.slimeChunkFinder == value;
        }
        if (key.equals(VTModInfo.MODID + ":barks")) {
            return () -> ConfigHandler.miscellanious_restart.barks == value;
        }
        if (key.equals(VTModInfo.MODID + ":chest")) {
            return () -> ConfigHandler.recipes.useBetterChestRecipe == value;
        }
        if (key.equals(VTModInfo.MODID + ":leather")) {
            return () -> ConfigHandler.recipes.leather == value;
        }
        if (key.equals(VTModInfo.MODID + ":bauble")) {
            return () -> VT.baubles == value;
        }
        return null;
    }
}
