package io.github.strikerrocker.vt.content.items;

import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.VTModInfo;
import net.minecraftforge.common.crafting.IConditionSerializer;

import java.util.function.BooleanSupplier;

public class Conditions implements IConditionSerializer {

    @Override
    public BooleanSupplier parse(JsonObject json) {
        String key = json.get("type").getAsString();
        if (key.equals(VTModInfo.MODID + ":crafting_pad")) return () -> Items.enablePad.get();
        if (key.equals(VTModInfo.MODID + ":slime_bucket")) return () -> Items.enableSlimeBucket.get();
        if (key.equals(VTModInfo.MODID + ":dynamite")) return () -> Items.enableDynamite.get();
        if (key.equals(VTModInfo.MODID + ":binoculars")) return () -> Items.binocularZoomAmount.get() != 0;
        return null;
    }
}
