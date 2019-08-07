package io.github.strikerrocker.vt.content.blocks;

import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.VTModInfo;
import net.minecraftforge.common.crafting.IConditionSerializer;

import java.util.function.BooleanSupplier;

public class Conditions implements IConditionSerializer {
    @Override
    public BooleanSupplier parse(JsonObject json) {
        String key = json.get("type").getAsString();
        if (key.equals(VTModInfo.MODID + ":pedestal")) return () -> Blocks.enablePedestal.get();
        if (key.equals(VTModInfo.MODID + ":storage_blocks")) return () -> Blocks.enableStorageBlocks.get();
        return null;
    }
}
