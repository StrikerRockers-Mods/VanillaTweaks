package io.github.strikerrocker.vt.recipes.vanilla;

import com.google.gson.JsonObject;
import io.github.strikerrocker.vt.VTModInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class VanillaRecipeConditions implements ICondition {

    private static final ResourceLocation NAME = new ResourceLocation(VTModInfo.MODID, "vanilla");
    private final String object;

    private VanillaRecipeConditions(String object) {
        this.object = object;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test() {
        if (object.equals("chest")) return VanillaRecipes.betterChest.get();
        if (object.equals("nametag")) return VanillaRecipes.nametag.get();
        if (object.equals("string")) return VanillaRecipes.string.get();
        return false;
    }

    public static class Serializer implements IConditionSerializer<VanillaRecipeConditions> {
        static final VanillaRecipeConditions.Serializer INSTANCE = new VanillaRecipeConditions.Serializer();

        @Override
        public void write(JsonObject json, VanillaRecipeConditions value) {

        }

        @Override
        public VanillaRecipeConditions read(JsonObject json) {
            return new VanillaRecipeConditions(json.get("object").getAsString());
        }

        @Override
        public ResourceLocation getID() {
            return NAME;
        }
    }
}
