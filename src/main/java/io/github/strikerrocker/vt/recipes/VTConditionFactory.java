package io.github.strikerrocker.vt.recipes;

import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.handlers.VTConfigHandler;
import io.github.strikerrocker.vt.vt;
import io.github.strikerrocker.vt.vtModInfo;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public class VTConditionFactory implements IConditionFactory
{

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        boolean value = JsonUtils.getBoolean(json, "value", true);
        String key = JsonUtils.getString(json, "type");
        if (key.equals(vtModInfo.MOD_ID + ":storage_blocks")) {
            return () -> VTConfigHandler.storageBlocks == value;
        }
        if (key.equals(vtModInfo.MOD_ID + ":pedestal")) {
            return () -> VTConfigHandler.pedestal == value;
        }
        if (key.equals(vtModInfo.MOD_ID + ":pad")) {
            return () -> VTConfigHandler.craftingPad == value;
        }
        if (key.equals(vtModInfo.MOD_ID + ":slime")) {
            return () -> VTConfigHandler.slimeChunkFinder == value;
        }
        if (key.equals(vtModInfo.MOD_ID + ":barks")) {
            return () -> VTConfigHandler.barks == value;
        }
        if (key.equals(vtModInfo.MOD_ID + ":chest")) {
            return () -> VTConfigHandler.useBetterChestRecipe == value;
        }
        if (key.equals(vtModInfo.MOD_ID + ":leather")) {
            return () -> VTConfigHandler.leather == value;
        }
        if (key.equals(vtModInfo.MOD_ID + ":bauble")) {
            return () -> vt.baubles == value;
        }
        return null;
    }
}
