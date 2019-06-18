package io.github.strikerrocker.vt.content.blocks;

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
        if (key.equals(VTModInfo.MODID + ":bark_blocks")) return () -> Blocks.enableBarkBlocks;
        if (key.equals(VTModInfo.MODID + ":pedestal")) return () -> Blocks.enablePedestal;
        if (key.equals(VTModInfo.MODID + ":storage_blocks")) return () -> Blocks.enableStorageBlocks;
        return null;
    }
}
