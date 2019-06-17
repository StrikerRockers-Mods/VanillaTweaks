package io.github.strikerrocker.vt.content.items;

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
        if (key.equals(VTModInfo.MODID + ":crafting_pad")) return () -> Items.enablePad;
        if (key.equals(VTModInfo.MODID + ":slime_bucket")) return () -> Items.enableSlimeBucket;
        if (key.equals(VTModInfo.MODID + ":dynamite")) return () -> Items.enableDynamite;
        return null;
    }
}
