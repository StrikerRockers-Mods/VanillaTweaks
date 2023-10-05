package io.github.strikerrocker.vt.content.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.strikerrocker.vt.VanillaTweaks;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;

/**
 * Adds conditions for item recipes
 */
public final class ItemConditions implements ICondition {
    public static final Codec<ItemConditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("object").forGetter(condition -> condition.object)
    ).apply(instance, ItemConditions::new));
    public static final ResourceLocation NAME = new ResourceLocation(VanillaTweaks.MOD_ID, "items");
    private final String object;

    public ItemConditions(String object) {
        this.object = object;
    }

    @Override
    public boolean test(IContext context) {
        if (object.equals("crafting_pad")) return ForgeItems.enablePad.get();
        if (object.equals("slime_bucket")) return ForgeItems.enableSlimeBucket.get();
        if (object.equals("dynamite")) return ForgeItems.enableDynamite.get();
        if (object.equals("fried_egg")) return ForgeItems.enableFriedEgg.get();
        return false;
    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }
}