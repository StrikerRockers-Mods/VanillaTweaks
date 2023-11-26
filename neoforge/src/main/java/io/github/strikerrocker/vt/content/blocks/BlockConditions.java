package io.github.strikerrocker.vt.content.blocks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;


/**
 * Adds conditions for item blocks recipes
 */
public final class BlockConditions implements ICondition {
    public static final Codec<BlockConditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("object").forGetter(condition -> condition.object)
    ).apply(instance, BlockConditions::new));
    public static final String NAME = "blocks";
    private final String object;

    public BlockConditions(String object) {
        this.object = object;
    }

    @Override
    public boolean test(IContext context) {
        if (object.equals("pedestal")) return ForgeBlocks.enablePedestal.get();
        if (object.equals("storage_blocks")) return ForgeBlocks.enableStorageBlocks.get();
        return false;
    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }
}