package io.github.strikerrocker.vt.content.items;

import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.VTModInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class ItemConditions implements ICondition {

    private static final ResourceLocation NAME = new ResourceLocation(VTModInfo.MODID, "items");
    private final String object;

    private ItemConditions(String object) {
        this.object = object;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test() {
        if (object.equals("crafting_pad")) return Items.enablePad.get();
        if (object.equals("slime_bucket")) return Items.enableSlimeBucket.get();
        if (object.equals("dynamite")) return Items.enableDynamite.get();
        if (object.equals("fried_egg")) return Items.enableFriedEgg.get();
        if (object.equals("binoculars")) return Items.binocularZoomAmount.get() != 0;
        return false;
    }

    public static class Serializer implements IConditionSerializer<ItemConditions> {
        static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, ItemConditions value) {

        }

        @Override
        public ItemConditions read(JsonObject json) {
            return new ItemConditions(json.get("object").getAsString());
        }

        @Override
        public ResourceLocation getID() {
            return NAME;
        }
    }
}
