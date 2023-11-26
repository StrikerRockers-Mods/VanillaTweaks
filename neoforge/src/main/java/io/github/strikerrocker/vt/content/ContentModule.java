package io.github.strikerrocker.vt.content;

import io.github.strikerrocker.vt.base.ForgeModule;
import io.github.strikerrocker.vt.content.blocks.ForgeBlocks;
import io.github.strikerrocker.vt.content.items.ForgeItems;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ContentModule extends ForgeModule {
    public ContentModule(ModConfigSpec.Builder builder) {
        super("content", "New items, blocks VanillaTweaks adds.", builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("items", new ForgeItems());
        registerFeature("blocks", new ForgeBlocks());
    }
}