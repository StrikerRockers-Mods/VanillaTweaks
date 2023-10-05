package io.github.strikerrocker.vt.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;

/**
 * Adds conditions for adding vanilla-like recipes
 */
public final class VanillaRecipeConditions implements ICondition {
    public static final ResourceLocation NAME = new ResourceLocation(VanillaTweaks.MOD_ID, "vanilla");
    public static final Codec<VanillaRecipeConditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("object").forGetter(condition -> condition.object)
    ).apply(instance, VanillaRecipeConditions::new));
    private final String object;


    public VanillaRecipeConditions(String object) {
        this.object = object;
    }

    @Override
    public boolean test(IContext context) {
        System.out.println(object);
        if (object.equals("chest")) return VanillaRecipes.betterChest.get();
        if (object.equals("nametag")) return VanillaRecipes.nametag.get();
        if (object.equals("string")) return VanillaRecipes.string.get();
        if (object.equals("trapped_chest")) return VanillaRecipes.betterTrappedChestRecipe.get();
        if (object.equals("repeater")) return VanillaRecipes.betterRepeater.get();
        return false;
    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }
}